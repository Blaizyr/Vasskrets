package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.components.conversation.Controller
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.domain.conversation.model.ConversationIdentity
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ConversationsController(
    private val context: ComponentContext,
    private val createConversationUseCase: CreateNewConversationUseCase,
    private val conversationsMetadataUseCase: ConversationsMetadataUseCase,
) : Controller<ConversationIdentity>, ComponentContext by context {

    private val scope = context.lifecycle.createCoroutineScope()
    private val _availableConversationIdentities = MutableStateFlow<List<ConversationIdentity>>(emptyList())
    override val availableItems: StateFlow<List<ConversationIdentity>> = _availableConversationIdentities.asStateFlow()

    init {
        scope.launch {
            conversationsMetadataUseCase
                .allConversations
                .collect { map ->
                    val previous = _availableConversationIdentities.value
                    val updated = map.map { metadata ->
                        val existing = previous.find { it.id == metadata.id }
                        if (existing?.metadata == metadata) existing
                        else ConversationIdentity(id = metadata.id, metadata = metadata)
                    }

                    if (updated != previous) {
                        _availableConversationIdentities.value = updated
                    }

                    if (map.isEmpty() && previous.isEmpty()) {
                        createNewConversation()
                    }
                }
        }
    }

    suspend fun createNewConversation(): ConversationIdentity? {
        val newEntry = createConversationUseCase()
        return availableItems.value.find { it.id == newEntry }
    }

}
