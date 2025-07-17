package pw.kmp.vasskrets.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun FilePathDisplay(
    modifier: Modifier = Modifier,
    filePath: String,
    onCopyClick: (path: String) -> Unit,
) {
    var copied by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = filePath,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .horizontalScroll(rememberScrollState()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(onClick = {
            onCopyClick(filePath)
            copied = true
        }) {
            Text("\"\uD83D\uDCCB")
         /*   Icon(
                imageVector = ,
                contentDescription = "Copy file path"
            )*/
        }
    }

    if (copied) {
        LaunchedEffect(Unit) {
            delay(1500)
            copied = false
        }
        ToastNotification("Skopiowano ścieżkę!")
    }
}

@Composable
fun ToastNotification(text: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
