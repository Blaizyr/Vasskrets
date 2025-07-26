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
actual class ConversationRouter {
    actual val activeComponents: StateFlow<List<Entry.ConversationEntry>>
        get() = TODO("Not yet implemented")

    actual fun openConversation(id: Uuid) { }

    actual fun closeConversation(id: Uuid) { }

    actual fun createNewConversation() { }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object ConversationRouterProvider {
    actual operator fun invoke(
        nodeComponentContext: ComponentContext,
        factory: ConversationComponentFactory,
        createNewConversationUseCase: CreateNewConversationUseCase,
    ): ConversationRouter {
        TODO("Not yet implemented")
    }
}
