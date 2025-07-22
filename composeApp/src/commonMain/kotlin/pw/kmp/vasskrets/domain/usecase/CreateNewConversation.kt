package pw.kmp.vasskrets.domain.usecase

import pw.kmp.vasskrets.data.repository.ChatRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CreateNewConversationUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): Uuid {
        return chatRepository.createChat()
    }
}
