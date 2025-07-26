package pw.kmp.vasskrets.platform

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
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
        nodeComponentContext: ComponentContext,
        factory: ConversationComponentFactory,
        createNewConversationUseCase: CreateNewConversationUseCase,
    ): ConversationRouter
}
