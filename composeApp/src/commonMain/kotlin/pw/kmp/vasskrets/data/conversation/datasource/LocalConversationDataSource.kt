package pw.kmp.vasskrets.data.conversation.datasource

import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.data.conversation.ConversationStorageConfig
import pw.kmp.vasskrets.data.conversation.ConversationStorageEntity
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class LocalConversationDataSource(
    private val jsonStorage: JsonStorage,
    private val storageConfig: ConversationStorageConfig,
) : ConversationDataSource {
    override suspend fun createNewChat(): Conversation {
        val newChat = Conversation()
        val metadata = ConversationMetadata( //TODO: add metadata persistence system #13
            id = newChat.id
        )
        saveChat(newChat)
        return newChat
    }

    /*  override suspend fun loadChatMetadata(): List<ConversationMetadata> {
          return jsonStorage.loadMetadatas(type = ConversationMetadataType)
      }
  */
    override suspend fun loadChat(chatId: Uuid): Conversation? {
        return jsonStorage.loadById(
            id =chatId.toString(),
            deserializer = storageConfig.serializer
        )?.messages?.let { Conversation(chatId, it) }
    }

    override suspend fun saveChat(conversation: Conversation): Boolean {
        val conversationStorageEntity = ConversationStorageEntity(
            id = conversation.id,
            messages = conversation.messages,
        )
        return jsonStorage.save(
            filename = conversationStorageEntity.id.toString(),
            content = conversationStorageEntity,
            serializer = storageConfig.serializer
        )
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        return jsonStorage.delete(chatId.toString())
    }

}