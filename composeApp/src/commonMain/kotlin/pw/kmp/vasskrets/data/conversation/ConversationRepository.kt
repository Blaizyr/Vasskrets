package pw.kmp.vasskrets.data.conversation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.data.conversation.datasource.ConversationDataSource
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class ConversationRepository(
    private val localDataSource: ConversationDataSource,
    private val websocketApiDataSource: ConversationDataSource,
    private val httpsApiDataSource: ConversationDataSource
)  {

    private val _activeChats = mutableMapOf<Uuid, MutableStateFlow<Conversation>>()

    suspend fun getChat(id: Uuid): StateFlow<Conversation>? {
        val chat = localDataSource.loadChat(id) ?: return null
        return _activeChats.getOrPut(id) { MutableStateFlow(chat) }
    }

    suspend fun createChat(): Uuid {
        val newChat = localDataSource.createNewChat()
        _activeChats[newChat.id] = MutableStateFlow(newChat)
        return newChat.id
    }

}
