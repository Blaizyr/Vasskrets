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
import pw.kmp.vasskrets.InteractionEnvironment
import pw.kmp.vasskrets.Session
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.components.conversation.DefaultConversationComponent
import pw.kmp.vasskrets.components.home.DefaultHomeComponent
import pw.kmp.vasskrets.components.login.DefaultLoginComponent
import pw.kmp.vasskrets.components.notes.DefaultNotesComponent
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.navigation.MyNavState
import pw.kmp.vasskrets.navigation.NavigationComponent
import pw.kmp.vasskrets.navigation.NavigationConfig
import pw.kmp.vasskrets.platform.ConversationNavConfig
import pw.kmp.vasskrets.platform.ConversationRouterV2
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

    val interactionEnvironment = getKoin().get<InteractionEnvironment>()
    private val conversationMetadataUseCase = getKoin().get<ConversationsMetadataUseCase>()
    private val createConversation = getKoin().get<CreateNewConversationUseCase>()

    private val conversationComponentFactory = ConversationComponentFactory { conversationId, childContext ->
        val sendTextMessageUseCase = getKoin().get<SendTextMessageUseCase>()
        DefaultConversationComponent(
            conversationId = conversationId,
            sendTextMessageUseCase = sendTextMessageUseCase,
            componentContext = childContext ?: childContext(key = "conversation_$conversationId", lifecycle = null)
        )
    }

    private val navigationStack: Value<ChildStack<NavigationConfig, Child>> = childStack(
        source = navigation,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.Conversations,
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

            is NavigationConfig.Conversations -> {
                val routerContext = context.childContext("router")
                val nodeContext = context.childContext("node")
                val dispatcherContext = context.childContext("dispatcher")

                val routerV2 = ConversationRouterV2(
                    context = routerContext,
                    createConversationUseCase = createConversation,
                    conversationsMetadataUseCase = conversationMetadataUseCase
                )

                val dispatcher = GenericNavigationDispatcher(
                    componentContext = dispatcherContext,
                    serializer = MyNavState.serializer(ConversationNavConfig.serializer()),
                    childFactory = { config, childContext ->
                        Entry.ConversationEntry(
                            Uuid.random(),
                            conversationComponentFactory(config.id, childContext)
                        )
                    },
                    routeConfigsFlow = routerV2.routeConfigs,
                    interactionContext = interactionEnvironment
                )

                Child.Conversations(
                    component = ConversationNodeComponent(
                        factory = conversationComponentFactory,
                        context = nodeContext,
                        routerV2 = routerV2,
                        navigationDispatcher = dispatcher
                    )
                )
            }


            NavigationConfig.Profile -> TODO()
            NavigationConfig.Settings -> TODO()
        }
    }

}
