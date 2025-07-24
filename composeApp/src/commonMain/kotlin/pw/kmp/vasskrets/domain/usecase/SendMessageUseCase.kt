package pw.kmp.vasskrets.domain.usecase

import pw.kmp.vasskrets.data.repository.ChatRepository
import pw.kmp.vasskrets.domain.model.chat.ChatSender
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SendTextMessageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(
        conversationId: Uuid,
        sender: ChatSender? = ChatSender.USER,
        text: String
    ) {
//        chatRepository.sendMessage(conversationId, sender, text)
        TODO("Not yet implemented in repo")
    }
}
