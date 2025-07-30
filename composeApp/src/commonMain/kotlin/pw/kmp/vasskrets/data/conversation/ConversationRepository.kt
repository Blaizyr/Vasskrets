package pw.kmp.vasskrets.data.conversation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import pw.kmp.vasskrets.data.conversation.datasource.ConversationDataSource
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class ConversationRepository(
    private val localDataSource: ConversationDataSource,
    private val websocketApiDataSource: ConversationDataSource,
    private val httpsApiDataSource: ConversationDataSource,
) {

    private val _allMetadata = MutableStateFlow<List<ConversationMetadata>>(emptyList())
    val allMetadata: StateFlow<List<ConversationMetadata>> get() = _allMetadata

    private val _activeChats = mutableMapOf<Uuid, MutableStateFlow<Conversation>>()
    val activeChats: Map<Uuid, StateFlow<Conversation>> get() = _activeChats

    suspend fun loadAll() {
        val metadataList = localDataSource.loadChatMetadata()
        _allMetadata.value = metadataList
    }

    suspend fun getChat(id: Uuid): StateFlow<Conversation>? =
        _activeChats[id] ?: localDataSource.loadChat(id)?.let {
            MutableStateFlow(it).also { flow -> _activeChats[id] = flow }
        }

    suspend fun createChat(): Uuid {
        val newChat = localDataSource.createNewChat()
        _activeChats[newChat.id] = MutableStateFlow(newChat)

        val newMetadata = ConversationMetadata(
            id = newChat.id,
            lastModified = newChat.beginsAt,
            title = newChat.name,
        )

        _allMetadata.update { it + newMetadata }
        return newChat.id
    }

    suspend fun updateChat(conversation: Conversation): Boolean {
        val flow = _activeChats[conversation.id] ?: return false
        flow.value = conversation
        return localDataSource.saveChat(conversation)
    }

    suspend fun deleteChat(id: Uuid): Boolean {
        _activeChats.remove(id)
        _allMetadata.update { it.filterNot { meta -> meta.id == id } }
        return localDataSource.deleteChat(id)
    }

    suspend fun updateMetadata(updated: ConversationMetadata) {
        _allMetadata.update { list ->
            list.map { if (it.id == updated.id) updated else it }
        }
    }
}
