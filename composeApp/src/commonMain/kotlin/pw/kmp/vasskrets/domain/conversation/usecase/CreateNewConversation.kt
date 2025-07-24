package pw.kmp.vasskrets.domain.conversation.usecase

import pw.kmp.vasskrets.data.conversation.ConversationRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CreateNewConversationUseCase(
    private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(): Uuid {
        return conversationRepository.createChat()
    }
}
