package pw.kmp.vasskrets.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.data.datasource.ChatDataSource
import pw.kmp.vasskrets.model.Chat
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ChatRepository(
    private val localDataSource: ChatDataSource,
    private val websocketApiDataSource: ChatDataSource,
    private val httpsApiDataSource: ChatDataSource
)  {

    private val _activeChats = mutableMapOf<Uuid, MutableStateFlow<Chat>>()

    suspend fun getChat(id: Uuid): StateFlow<Chat>? {
        val chat = localDataSource.loadChat(id) ?: return null
        return _activeChats.getOrPut(id) { MutableStateFlow(chat) }
    }

}
