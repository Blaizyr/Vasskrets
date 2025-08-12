package pw.kmp.vasskrets.ui.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.domain.conversation.model.ConversationMessage
import pw.kmp.vasskrets.domain.conversation.model.Participant

@Composable
fun ChatBubble(message: ConversationMessage) {
    val isUser = message.participant == Participant.USER
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bgColor = if (isUser) Color(0xFFD0F0C0) else Color(0xFFE0E0E0)
    val textAlign = if (isUser) TextAlign.End else TextAlign.Start

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
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
