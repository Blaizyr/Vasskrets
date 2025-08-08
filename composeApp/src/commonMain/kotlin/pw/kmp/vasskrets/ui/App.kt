package pw.kmp.vasskrets.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pw.kmp.vasskrets.components.home.HomeComponent
import pw.kmp.vasskrets.components.login.LoginComponent
import pw.kmp.vasskrets.components.notes.NotesComponent
import pw.kmp.vasskrets.components.root.RootComponent
import pw.kmp.vasskrets.data.note.NoteRepository
import pw.kmp.vasskrets.domain.note.Note
import pw.kmp.vasskrets.ui.adaptive.ProvideInteractionEnvironment
import pw.kmp.vasskrets.ui.theme.AppTheme
import pw.kmp.vasskrets.ui.theme.LocalThemeIsDark
import vasskrets.composeapp.generated.resources.IndieFlower_Regular
import vasskrets.composeapp.generated.resources.Res
import vasskrets.composeapp.generated.resources.ic_cyclone
import vasskrets.composeapp.generated.resources.ic_dark_mode
import vasskrets.composeapp.generated.resources.ic_light_mode
import vasskrets.composeapp.generated.resources.ic_rotate_right
import vasskrets.composeapp.generated.resources.open_github
import vasskrets.composeapp.generated.resources.run
import vasskrets.composeapp.generated.resources.stop
import vasskrets.composeapp.generated.resources.theme
import vasskrets.composeapp.generated.resources.vasskrets

@Preview
@Composable
internal fun App(root: RootComponent) {
    val session by root.currentSession.collectAsState()
    ProvideInteractionEnvironment(root.interactionEnvironment)

    when (/*session*/"logged in ;)") {
        null -> LoginScreen(root.loginComponent)
        else -> NavigationLayout(root.mainNavigator)
    }
}


@Composable
fun LoginScreen(component: LoginComponent) {
}

@Composable
fun HomeScreen(component: HomeComponent) {
}

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
                    Text(note.id)
                    Text(note.title /*style = MaterialTheme.typography.subtitle1*/)
                    Text(note.content/*, style = MaterialTheme.typography.body2*/)
                    Text("Utworzono: ${note.meta.created}"/*, style = MaterialTheme.typography.caption*/)
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
internal fun LegacyApp() = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.vasskrets),
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.displayLarge
        )

        var isRotating by remember { mutableStateOf(false) }

        val rotate = remember { Animatable(0f) }
        val target = 360f
        if (isRotating) {
            LaunchedEffect(Unit) {
                while (isActive) {
                    val remaining = (target - rotate.value) / target
                    rotate.animateTo(
                        target,
                        animationSpec = tween(
                            (1_000 * remaining).toInt(),
                            easing = LinearEasing
                        )
                    )
                    rotate.snapTo(0f)
                }
            }
        }

        Image(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
                .run { rotate(rotate.value) },
            imageVector = vectorResource(Res.drawable.ic_cyclone),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = null
        )

        ElevatedButton(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(min = 200.dp),
            onClick = { isRotating = !isRotating },
            content = {
                Icon(vectorResource(Res.drawable.ic_rotate_right), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(if (isRotating) Res.string.stop else Res.string.run)
                )
            }
        )

        var isDark by LocalThemeIsDark.current
        val icon = remember(isDark) {
            if (isDark) Res.drawable.ic_light_mode
            else Res.drawable.ic_dark_mode
        }

        ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(min = 200.dp),
            onClick = { isDark = !isDark },
            content = {
                Icon(vectorResource(icon), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(Res.string.theme))
            }
        )

        val uriHandler = LocalUriHandler.current
        TextButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(min = 200.dp),
            onClick = { uriHandler.openUri("https://github.com/blaizyr") },
        ) {
            Text(stringResource(Res.string.open_github))
        }
    }
}
