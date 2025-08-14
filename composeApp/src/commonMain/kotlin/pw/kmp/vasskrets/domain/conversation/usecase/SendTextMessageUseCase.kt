package pw.kmp.vasskrets.domain.conversation.usecase

import pw.kmp.vasskrets.data.conversation.ConversationRepository
import pw.kmp.vasskrets.domain.conversation.model.ConversationMessage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SendTextMessageUseCase(
    private val conversationRepository: ConversationRepository,
) {
    suspend operator fun invoke(
        conversationId: Uuid,
        messageToSend: ConversationMessage,
    ) {
        conversationRepository.sendMessage(
            conversationId = conversationId,
            newMessage = messageToSend,
        )
    }
}
