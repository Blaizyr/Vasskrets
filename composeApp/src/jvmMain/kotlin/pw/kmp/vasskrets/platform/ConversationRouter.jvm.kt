@file:OptIn(ExperimentalUuidApi::class)

package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import pw.kmp.vasskrets.WindowManager
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ConversationRouter(
    private val nodeContext: ComponentContext,
    private val factory: ConversationComponentFactory,
    private val createNewConversationUseCase: CreateNewConversationUseCase,
    private val windowManager: WindowManager,
) : ComponentContext by nodeContext {
    private val scope = nodeContext.lifecycle.createCoroutineScope()
    private val _activeConversations = MutableStateFlow<List<Entry.ConversationEntry>>(emptyList())
    actual val activeComponents = _activeConversations.asStateFlow()
    val dockedConversations: List<Entry.ConversationEntry> = emptyList()

    actual fun openConversation(id: Uuid) {
        if (activeComponents.value.any { it.componentId == id }) return
        val component = factory(id)

        _activeConversations.update { current ->
            current + Entry.ConversationEntry(
               componentId =  Uuid.random(),
               component =  component,
            )
        }
    }

    actual fun closeConversation(id: Uuid) { }

    actual fun createNewConversation() {
        scope.launch {
            val newConversationId = createNewConversationUseCase()
            openConversation(newConversationId)
        }
    }

    fun undockConversationEntry(componentId: Uuid) {
        val conversationEntry = activeComponents.value.find { it.componentId == componentId }
        conversationEntry?.let {
            windowManager.open(conversationEntry)
        }
    }

}

actual object ConversationRouterProvider {
    actual operator fun invoke(
        nodeComponentContext: ComponentContext,
        factory: ConversationComponentFactory,
        createNewConversationUseCase: CreateNewConversationUseCase,
    ): ConversationRouter {
        return ConversationRouter(
            nodeContext = nodeComponentContext,
            factory = factory,
            createNewConversationUseCase = createNewConversationUseCase,
            windowManager = getKoin().get<WindowManager>()
        )
    }
}
