package pw.kmp.vasskrets.ui.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.components.notes.NotesComponent
import pw.kmp.vasskrets.data.note.NoteRepository
import pw.kmp.vasskrets.domain.note.Note
import pw.kmp.vasskrets.ui.FilePathDisplay
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun NotesScreen(component: NotesComponent) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(NoteRepository.loadAll()) }
    var activePath by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = "Vasskrets: Lyden av JSON-regndråper "
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Treść") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Button(onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    val note = Note(title = title, content = content)
                    activePath = NoteRepository.saveAndPoint(note)?.name ?: ""
                    notes = NoteRepository.loadAll()
                    title = ""
                    content = ""
                }
            }) {
                Text("Zapisz notatkę")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FilePathDisplay(filePath = activePath) { /*TODO("copy to clipboard") #2*/ }

        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(note.id.toString())
                    Text(note.title /*style = MaterialTheme.typography.subtitle1*/)
                    Text(note.content/*, style = MaterialTheme.typography.body2*/)
                }
                HorizontalDivider()
            }
        }
    }
}