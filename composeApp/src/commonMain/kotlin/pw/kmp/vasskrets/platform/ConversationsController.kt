package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.components.conversation.Controller
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ConversationNavConfig(val id: Uuid, val metadata: ConversationMetadata)

@OptIn(ExperimentalUuidApi::class)
class ConversationsController(
    private val context: ComponentContext,
    private val createConversationUseCase: CreateNewConversationUseCase,
    private val conversationsMetadataUseCase: ConversationsMetadataUseCase,
) : Controller<ConversationNavConfig>, ComponentContext by context {

    private val scope = context.lifecycle.createCoroutineScope()
    private val _availableConversationIdentities = MutableStateFlow<List<ConversationNavConfig>>(emptyList())
    override val availableItems: StateFlow<List<ConversationNavConfig>> = _availableConversationIdentities.asStateFlow()

    init {
        scope.launch {
            conversationsMetadataUseCase
                .allConversations
                .collect { map ->
                    val previous = _availableConversationIdentities.value
                    val updated = map.map { metadata ->
                        val existing = previous.find { it.id == metadata.id }
                        if (existing?.metadata == metadata) existing
                        else ConversationNavConfig(id = metadata.id, metadata = metadata)
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

    suspend fun createNewConversation(): ConversationNavConfig? {
        val newEntry = createConversationUseCase()
        return availableItems.value.find { it.id == newEntry }
    }

}
