package pw.kmp.vasskrets.domain.conversation.usecase

import pw.kmp.vasskrets.data.conversation.ConversationRepository

class ConversationsMetadataUseCase(
    private val conversationRepository: ConversationRepository
) {
    val allConversations = conversationRepository.allMetadata
}
