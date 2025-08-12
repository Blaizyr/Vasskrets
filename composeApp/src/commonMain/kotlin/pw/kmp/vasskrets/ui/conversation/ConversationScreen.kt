package pw.kmp.vasskrets.ui.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.components.conversation.ConversationComponent
import pw.kmp.vasskrets.components.conversation.ConversationState
import pw.kmp.vasskrets.platform.getPlatformAdaptivePaddings

@Composable
fun ConversationScreen(
    component: ConversationComponent,
) {
    val uiState by component.uiState.collectAsState()

    ConversationScreenContent(
        uiState = uiState,
        onTextInputChanged = component.onTextInputChanged,
        onSend = { component.sendMessage() }
    )
}

@Composable
private fun ConversationScreenContent(
    uiState: ConversationState,
    onTextInputChanged: (String) -> Unit,
    onSend: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(getPlatformAdaptivePaddings())
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true,
            verticalArrangement = Arrangement.Bottom
        ) {
            items(uiState.messages.reversed()) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = uiState.currentTextInput,
                onValueChange = onTextInputChanged,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onSend() }) {
                Text("Send")
            }
        }
    }
}
