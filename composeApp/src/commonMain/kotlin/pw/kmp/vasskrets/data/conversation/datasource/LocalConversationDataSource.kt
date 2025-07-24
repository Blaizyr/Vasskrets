package pw.kmp.vasskrets.data.conversation.datasource

import kotlinx.serialization.KSerializer
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import pw.kmp.vasskrets.domain.ChatType
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class LocalConversationDataSource(
    private val jsonStorage: JsonStorage,
    private val serializer: KSerializer<Conversation> = Conversation.serializer(),
) : ConversationDataSource {
    override suspend fun createNewChat(): Conversation {
        val newChatId = Uuid.random()
        val newChat = Conversation(
                id = newChatId,
                name = "",
                beginsAt = Clock.System.now(),
                tags = emptyList(),
                messages = emptyList(),
                meta = emptyMap()
            )
        saveChat(newChat)
        return newChat
    }

    override suspend fun loadChatMetadata(): List<ConversationMetadata> {
        return jsonStorage.loadMetadatas(type = ChatType)
    }

    override suspend fun loadChat(chatId: Uuid): Conversation? {
        return jsonStorage.loadById(chatId.toString(), serializer)
    }

    override suspend fun saveChat(conversation: Conversation): Boolean {
        return jsonStorage.save(
            filename = conversation.id.toString(),
            content = conversation,
            serializer = serializer
        )
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        return jsonStorage.delete(chatId.toString())
    }

}