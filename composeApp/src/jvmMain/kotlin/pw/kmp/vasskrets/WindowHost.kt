package pw.kmp.vasskrets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.window.Window
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.ui.conversation.ConversationScreen
import pw.kmp.vasskrets.ui.NotesScreen
import pw.kmp.vasskrets.ui.windowing.WindowManager
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun WindowHost(windowManager: WindowManager) {
    val openWindows = windowManager.windows.values.toList()

    for (window in openWindows) {
        key(window.componentId) {
            Window(
                onCloseRequest = {
                    windowManager.close(window.componentId)
                },
                title = "Conversation: ${window.componentId/* TODO conversation name */}"
            ) {
                when(window) {
                    is Entry.ConversationEntry -> ConversationScreen(window.component)
                    is Entry.NoteEntry -> NotesScreen(window.component)
                }
            }
        }
    }
}
