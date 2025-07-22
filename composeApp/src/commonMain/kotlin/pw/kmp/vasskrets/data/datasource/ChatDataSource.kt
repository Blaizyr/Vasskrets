package pw.kmp.vasskrets.data.datasource

import pw.kmp.vasskrets.domain.model.Chat
import pw.kmp.vasskrets.domain.model.ChatMetadata
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ChatDataSource {

    suspend fun createNewChat() : Chat

    suspend fun loadChatMetadata(): List<ChatMetadata>

    suspend fun loadChat(chatId: Uuid): Chat?

    suspend fun saveChat(chat: Chat): Boolean

    suspend fun deleteChat(chatId: Uuid): Boolean
}
