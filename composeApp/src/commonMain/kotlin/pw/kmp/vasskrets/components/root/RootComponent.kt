package pw.kmp.vasskrets.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import org.koin.core.component.KoinComponent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Child>()

    val childStack: Value<ChildStack<Child, Any>> = childStack(
        source = navigation,
        serializer = Child.serializer(),
        initialConfiguration = Child.Conversation(Uuid.random()),
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(child: Child, context: ComponentContext): Any {
        return when (child) {
            is Child.Login -> DefaultLoginComponent(
                onLoginSuccess = { sessionId /* TODO sessionId implementation #4 */ ->
                    navigation.replaceCurrent(Child.Home)
                },
                componentContext = context
            )
            is Child.Home -> DefaultHomeComponent(componentContext = context)
            is Child.Notes -> DefaultNotesComponent(componentContext = context)
            is Child.Conversation -> DefaultConversationComponent(componentContext = context)
        }
    }
}

interface LoginComponent
interface HomeComponent
interface NotesComponent
interface ConversationComponent

class DefaultLoginComponent(val onLoginSuccess: (sessionId: String) -> Unit, componentContext: ComponentContext) :  LoginComponent,  ComponentContext by componentContext, InstanceKeeper.Instance
class DefaultHomeComponent(componentContext: ComponentContext) : HomeComponent, ComponentContext by componentContext, InstanceKeeper.Instance
class DefaultNotesComponent(componentContext: ComponentContext) : NotesComponent, ComponentContext by componentContext, InstanceKeeper.Instance
class DefaultConversationComponent(componentContext: ComponentContext) : ConversationComponent, ComponentContext by componentContext, InstanceKeeper.Instance