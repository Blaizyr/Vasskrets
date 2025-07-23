package pw.kmp.vasskrets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.components.root.ConversationComponent
import pw.kmp.vasskrets.platform.getPlatformAdaptivePaddings

@Composable
fun ConversationScreen(
    component: ConversationComponent
) {
    // val uiState by conversationComponent.uiState.collectAsState() //

    val uiState = remember {
        ConversationUiState(
            messages = listOf(
                ChatMessage("Hello", isUser = true),
                ChatMessage("Hi, how can I help?", isUser = false),
                ChatMessage("Tell me a joke", isUser = true),
                ChatMessage(
                    "Why did the programmer quit his job? Because he didn’t get arrays. 😄",
                    isUser = false
                )
            )
        )
    }

    ConversationScreenContent(
        uiState = uiState,
        onSend = { /* conversationComponent.onSend(it) */ }
    )
}

@Composable
private fun ConversationScreenContent(
    uiState: ConversationUiState,
    onSend: (String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    var inputText by remember { mutableStateOf("") }

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
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                onSend(inputText)
                inputText = ""
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bgColor = if (message.isUser) Color(0xFFD0F0C0) else Color(0xFFE0E0E0)
    val textAlign = if (message.isUser) TextAlign.End else TextAlign.Start

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(bgColor, shape = RoundedCornerShape(12.dp))
                .padding(10.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = message.text,
                textAlign = textAlign,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

data class ConversationUiState(
    val messages: List<ChatMessage>,
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
)
