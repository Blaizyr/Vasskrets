package pw.kmp.vasskrets.data.conversation.datasource

import kotlinx.serialization.KSerializer
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RemoteConversationDataSource(
    private val jsonStorage: JsonStorage,
    private val serializer: KSerializer<Conversation> = Conversation.serializer(),
) : ConversationDataSource {
    override suspend fun createNewChat(): Conversation {
        TODO("Not yet implemented")
    }

    override suspend fun loadChatMetadata(): List<ConversationMetadata> {
        TODO("Not yet implemented")
    }

    override suspend fun loadChat(chatId: Uuid): Conversation? {
        TODO("Not yet implemented")
    }

    override suspend fun saveChat(conversation: Conversation): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        TODO("Not yet implemented")
    }

}