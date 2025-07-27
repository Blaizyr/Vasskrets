package pw.kmp.vasskrets.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import pw.kmp.vasskrets.Session
import pw.kmp.vasskrets.components.NavigationComponent
import pw.kmp.vasskrets.components.NavigationConfig
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.components.conversation.DefaultConversationComponent
import pw.kmp.vasskrets.components.home.DefaultHomeComponent
import pw.kmp.vasskrets.components.login.DefaultLoginComponent
import pw.kmp.vasskrets.components.notes.DefaultNotesComponent
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import pw.kmp.vasskrets.platform.ConversationRouterProvider
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val _currentSession = MutableStateFlow<Session?>(null)
    val currentSession: StateFlow<Session?> = _currentSession

    private val navigation = StackNavigation<NavigationConfig>()

    val loginComponent = DefaultLoginComponent(
        componentContext = childContext(key = "login", lifecycle = null),
        onLoginSuccess = { authSession ->
            _currentSession.update { authSession }
        }
    )

    private val createNewConversationUseCase = getKoin().get<CreateNewConversationUseCase>()
    private val conversationFactory = ConversationComponentFactory { conversationId ->
        val sendTextMessageUseCase = getKoin().get<SendTextMessageUseCase>()
        DefaultConversationComponent(
            conversationId = conversationId,
            sendTextMessageUseCase = sendTextMessageUseCase,
            componentContext = childContext(key = "conversation_$conversationId", lifecycle = null)
        )
    }

    private val navigationStack: Value<ChildStack<NavigationConfig, Child>> = childStack(
        source = navigation,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.Notes,
        handleBackButton = true,
        childFactory = ::navigate,
    )

    val navigationComponent = NavigationComponent(
        componentContext = childContext(key = "navigation", lifecycle = null),
        navigation = navigation,
        navigationStack = navigationStack
    )

    private fun navigate(navConfig: NavigationConfig, context: ComponentContext): Child {
        return when (navConfig) {

            is NavigationConfig.Home -> Child.Home(
                component = DefaultHomeComponent(componentContext = context)
            )

            is NavigationConfig.Notes -> Child.Notes(
                component = DefaultNotesComponent(
                    componentContext = context
                )
            )

            is NavigationConfig.Conversations -> Child.Conversations(
                component = ConversationNodeComponent(
                    router = ConversationRouterProvider(
                        nodeComponentContext = context,
                        createNewConversationUseCase = createNewConversationUseCase,
                        factory = conversationFactory
                    )
                )
            )

            NavigationConfig.Profile -> TODO()
            NavigationConfig.Settings -> TODO()
        }
    }

}
