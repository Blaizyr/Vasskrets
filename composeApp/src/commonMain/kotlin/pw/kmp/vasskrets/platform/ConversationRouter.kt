package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.createCoroutineScope
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
data class ConversationNavConfig(val id: Uuid)

@OptIn(ExperimentalUuidApi::class)
class ConversationRouterV2(
    private val context: ComponentContext,
    private val createConversationUseCase: CreateNewConversationUseCase,
) : ComponentContext by context {

    private val _activeConfigs = MutableStateFlow<List<ConversationNavConfig>>(emptyList())
    val activeConfigs: StateFlow<List<ConversationNavConfig>> = _activeConfigs.asStateFlow()

    private val scope = context.lifecycle.createCoroutineScope()

    init {
        scope.launch {
            val isEmpty = activeConfigs.value.isEmpty()
            if (isEmpty) {
                createNewConversation()
            }
        }
    }

    fun openConversation(id: Uuid) {
        val newConfig = ConversationNavConfig(id)
        _activeConfigs.value = _activeConfigs.value + newConfig
    }

    fun closeConversation(id: Uuid) {
        _activeConfigs.value = _activeConfigs.value.filterNot { it.id == id }
    }

    suspend fun createNewConversation() {
        val newEntry = createConversationUseCase()
        openConversation(newEntry)
    }

}
