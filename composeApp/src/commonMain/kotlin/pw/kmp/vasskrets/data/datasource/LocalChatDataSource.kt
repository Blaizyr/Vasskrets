package pw.kmp.vasskrets.data.datasource

import kotlinx.serialization.KSerializer
import pw.kmp.vasskrets.data.storage.JsonStorage
import pw.kmp.vasskrets.model.Chat
import pw.kmp.vasskrets.model.ChatMetadata
import pw.kmp.vasskrets.model.ChatType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LocalChatDataSource(
    private val jsonStorage: JsonStorage,
    private val serializer: KSerializer<Chat> = Chat.serializer(),
) : ChatDataSource {

    override suspend fun loadChatMetadata(): List<ChatMetadata> {
        return jsonStorage.loadMetadatas(type = ChatType)
    }

    override suspend fun loadChat(chatId: Uuid): Chat? {
        return jsonStorage.loadById(chatId.toString(), serializer)
    }

    override suspend fun saveChat(chat: Chat): Boolean {
        return jsonStorage.save(
            filename = chat.id.toString(),
            content = chat,
            serializer = serializer
        )
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        return jsonStorage.delete(chatId.toString())
    }

}