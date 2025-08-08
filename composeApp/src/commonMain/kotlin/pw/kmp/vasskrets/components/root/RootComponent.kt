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
import pw.kmp.vasskrets.components.DomainComponentEntry
import pw.kmp.vasskrets.components.conversation.ConversationComponentFactory
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.components.conversation.DefaultConversationComponent
import pw.kmp.vasskrets.components.home.DefaultHomeComponent
import pw.kmp.vasskrets.components.login.DefaultLoginComponent
import pw.kmp.vasskrets.components.notes.DefaultNotesComponent
import pw.kmp.vasskrets.domain.conversation.model.ConversationDestinationConfig
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.navigation.MainNavigationConfig
import pw.kmp.vasskrets.navigation.MyNavState
import pw.kmp.vasskrets.navigation.NavigationComponent
import pw.kmp.vasskrets.platform.ConversationsController
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val _currentSession = MutableStateFlow<Session?>(null)
    val currentSession: StateFlow<Session?> = _currentSession

    private val mainNavigation = StackNavigation<MainNavigationConfig>()

    val loginComponent = DefaultLoginComponent(
        componentContext = childContext(key = "login", lifecycle = null),
        onLoginSuccess = { authSession ->
            _currentSession.update { authSession }
        }
    )

    val interactionEnvironment = getKoin().get<InteractionEnvironment>()
    private val conversationMetadataUseCase = getKoin().get<ConversationsMetadataUseCase>()
    private val createConversation = getKoin().get<CreateNewConversationUseCase>()

    private val conversationComponentFactory =
        ConversationComponentFactory { conversationId, childContext ->
            val sendTextMessageUseCase = getKoin().get<SendTextMessageUseCase>()
            DefaultConversationComponent(
                conversationId = conversationId,
                sendTextMessageUseCase = sendTextMessageUseCase,
                componentContext = childContext
                    ?: childContext(key = "conversation_$conversationId", lifecycle = null)
            )
        }

    private val mainNavigationStack: Value<ChildStack<MainNavigationConfig, Child>> = childStack(
        source = mainNavigation,
        serializer = MainNavigationConfig.serializer(),
        initialConfiguration = MainNavigationConfig.Conversations,
        handleBackButton = true,
        childFactory = ::navigate,
    )

    val mainNavigator = NavigationComponent(
        componentContext = childContext(key = "main_navigation", lifecycle = null),
        navigation = mainNavigation,
        navigationStack = mainNavigationStack
    )

    private fun navigate(navConfig: MainNavigationConfig, context: ComponentContext): Child {
        return when (navConfig) {

            is MainNavigationConfig.Home -> Child.Home(
                component = DefaultHomeComponent(componentContext = context)
            )

            is MainNavigationConfig.Notes -> Child.Notes(
                component = DefaultNotesComponent(
                    componentContext = context
                )
            )

            is MainNavigationConfig.Conversations -> {
                val routerContext = context.childContext("router")
                val nodeContext = context.childContext("node")
                val dispatcherContext = context.childContext("dispatcher")

                val controller = ConversationsController(
                    context = routerContext,
                    createConversationUseCase = createConversation,
                    conversationsMetadataUseCase = conversationMetadataUseCase
                )

                val dispatcher: GenericNavigationDispatcher<ConversationDestinationConfig, DomainComponentEntry.ConversationEntry> =
                    GenericNavigationDispatcher(
                        serializer = MyNavState.serializer(ConversationDestinationConfig.serializer()),
                        childFactory = { config, childContext ->
                            DomainComponentEntry.ConversationEntry(
                                config.configUuid,
                                conversationComponentFactory(config.conversationUuid, childContext)
                            )
                        },
//                        routeConfigsFlow = controller.availableItems,
                        componentContext = dispatcherContext,
                        interactionContext = interactionEnvironment
                    )

                Child.Conversations(
                    component = ConversationNodeComponent(
                        factory = conversationComponentFactory,
                        context = nodeContext,
                        controller = controller,
                        navigationDispatcher = dispatcher
                    )
                )
            }


            MainNavigationConfig.Profile -> TODO()
            MainNavigationConfig.Settings -> TODO()
        }
    }

}
