package pw.kmp.vasskrets.data.conversation.datasource

import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationDataSource {

    suspend fun createNewChat() : Conversation

    suspend fun loadChatMetadata(): List<ConversationMetadata>

    suspend fun loadChat(chatId: Uuid): Conversation?

    suspend fun saveChat(conversation: Conversation): Boolean

    suspend fun deleteChat(chatId: Uuid): Boolean
}
