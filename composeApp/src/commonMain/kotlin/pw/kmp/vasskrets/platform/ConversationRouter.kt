package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.components.conversation.Router
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ConversationRouter {
    val activeComponents: StateFlow<List<Entry.ConversationEntry>>
    fun openConversation(id: Uuid)
    fun closeConversation(id: Uuid)
    fun createNewConversation()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object ConversationRouterProvider {
    operator fun invoke(
        context: ComponentContext,
        factory: ConversationComponentFactory,
        createConversation: CreateNewConversationUseCase,
    ): ConversationRouter
}

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ConversationNavConfig(val id: Uuid, val metadata: ConversationMetadata)

@OptIn(ExperimentalUuidApi::class)
class ConversationRouterV2(
    private val context: ComponentContext,
    private val createConversationUseCase: CreateNewConversationUseCase,
    private val conversationsMetadataUseCase: ConversationsMetadataUseCase,
) : Router<ConversationNavConfig>, ComponentContext by context {

    private val scope = context.lifecycle.createCoroutineScope()
    private val _routeConfigs = MutableStateFlow<List<ConversationNavConfig>>(emptyList())
    override val routeConfigs: StateFlow<List<ConversationNavConfig>> = _routeConfigs.asStateFlow()


    init {
        scope.launch {
            conversationsMetadataUseCase
                .allConversations
                .collect { map ->
                    val previous = _routeConfigs.value
                    val updated = map.map { metadata ->
                        val existing = previous.find { it.id == metadata.id }
                        if (existing?.metadata == metadata) existing
                        else ConversationNavConfig(id = metadata.id, metadata = metadata)
                    }

                    if (updated != previous) {
                        _routeConfigs.value = updated
                    }

                    if (map.isEmpty() && previous.isEmpty()) {
                        createNewConversation()
                    }
                }
        }
    }

    suspend fun createNewConversation(): ConversationNavConfig? {
        val newEntry = createConversationUseCase()
        return routeConfigs.value.find { it.id == newEntry }
    }

}
