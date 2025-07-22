package pw.kmp.vasskrets.data.datasource

import kotlinx.serialization.KSerializer
import pw.kmp.vasskrets.data.storage.JsonStorage
import pw.kmp.vasskrets.domain.model.Chat
import pw.kmp.vasskrets.domain.model.ChatMetadata
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RemoteChatDataSource(
    private val jsonStorage: JsonStorage,
    private val serializer: KSerializer<Chat> = Chat.serializer(),
) : ChatDataSource {
    override suspend fun createNewChat(): Chat {
        TODO("Not yet implemented")
    }

    override suspend fun loadChatMetadata(): List<ChatMetadata> {
        TODO("Not yet implemented")
    }

    override suspend fun loadChat(chatId: Uuid): Chat? {
        TODO("Not yet implemented")
    }

    override suspend fun saveChat(chat: Chat): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        TODO("Not yet implemented")
    }

}