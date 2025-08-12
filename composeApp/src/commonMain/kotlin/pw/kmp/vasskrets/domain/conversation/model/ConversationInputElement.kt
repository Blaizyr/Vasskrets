package pw.kmp.vasskrets.domain.conversation.model

sealed class ConversationInputElement {
    data class Image(val uri: String) : ConversationInputElement()
    data class File(val name: String, val uri: String) : ConversationInputElement()
}
