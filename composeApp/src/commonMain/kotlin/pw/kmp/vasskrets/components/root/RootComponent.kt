package pw.kmp.vasskrets.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import org.koin.core.component.KoinComponent
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.components.conversation.DefaultConversationComponent
import pw.kmp.vasskrets.components.conversation.DefaultRootConversationComponent
import pw.kmp.vasskrets.components.home.DefaultHomeComponent
import pw.kmp.vasskrets.components.login.DefaultLoginComponent
import pw.kmp.vasskrets.components.notes.DefaultNotesComponent
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Child>()

    private val createNewConversationUseCase = getKoin().get<CreateNewConversationUseCase>()
    private val conversationFactory = ConversationComponentFactory { conversationId ->
        val sendTextMessageUseCase = getKoin().get<SendTextMessageUseCase>()
        DefaultConversationComponent(
            childContext(key = "conversation_$conversationId", lifecycle = null),
            conversationId,
            sendTextMessageUseCase
        )
    }

    val childStack: Value<ChildStack<Child, Any>> = childStack(
        source = navigation,
        serializer = Child.serializer(),
        initialConfiguration = Child.Conversations,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(child: Child, context: ComponentContext): Any {
        return when (child) {
            is Child.Login -> DefaultLoginComponent(
                componentContext = context,
                onLoginSuccess = { sessionId /* TODO sessionId implementation #4 */ ->
                    navigation.replaceCurrent(Child.Home)
                }
            )
            is Child.Home -> DefaultHomeComponent(componentContext = context)
            is Child.Notes -> DefaultNotesComponent(componentContext = context)
            is Child.Conversations -> DefaultRootConversationComponent(
                componentContext = context,
                createNewConversationUseCase = createNewConversationUseCase,
                conversationFactory = conversationFactory
            )
        }
    }
}
