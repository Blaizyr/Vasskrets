package pw.kmp.vasskrets.navigation

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.ChildNavState.Status.CREATED
import com.arkivanov.decompose.router.children.ChildNavState.Status.RESUMED
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.InteractionContext
import pw.kmp.vasskrets.createCoroutineScope

@Serializable
data class MyNavState<T : Any>(
    val childrenConfigs: List<T>,
) : NavState<T> {
    override val children: List<ChildNavState<T>> = childrenConfigs.mapIndexed { idx, config ->
        SimpleChildNavState(
            configuration = config,
            status = if (idx == childrenConfigs.lastIndex) RESUMED else CREATED
        )
    }
}

sealed class NavEvent<T> {
    data class Open<T>(val config: T) : NavEvent<T>()
    data class Close<T>(val config: T) : NavEvent<T>()
}

class GenericNavigationDispatcher<T : Any, K : Any>(
    componentContext: ComponentContext,
    private val serializer: KSerializer<MyNavState<T>>,
    private val childFactory: (T, ComponentContext) -> K,
    private val routeConfigsFlow: StateFlow<List<T>>,
    private val interactionContext: InteractionContext
): ComponentContext by componentContext {

    private val navSource = SimpleNavigation<NavEvent<T>>()
    private val currentState = MutableStateFlow<MyNavState<T>>(MyNavState(emptyList()))

    init {
          componentContext.lifecycle.createCoroutineScope().launch {
            routeConfigsFlow.collect { configs ->
                val currentConfigs = currentState.value.childrenConfigs
                val toAdd = configs.filterNot { it in currentConfigs }
                val toRemove = currentConfigs.filterNot { it in configs }
                toAdd.forEach { open(it) }
                toRemove.forEach { close(it) }
            }
        }
    }

    fun open(config: T) {
        navSource.navigate(NavEvent.Open(config))
    }

    fun close(config: T) {
        navSource.navigate(NavEvent.Close(config))
    }
    val childrenState: Value<List<Child.Created<T, K>>> = children(
        source = navSource,
        stateSerializer = serializer,
        initialState = { MyNavState(emptyList()) },
        key = "generic_nav",
        navTransformer = { state, event ->
            when (event) {
                is NavEvent.Open -> MyNavState(state.childrenConfigs + event.config)
                is NavEvent.Close -> MyNavState(state.childrenConfigs.filterNot { it == event.config })
            }
        },
        stateMapper = { _, children ->
            children.filterIsInstance<Child.Created<T, K>>()
        },
        backTransformer = { state ->
            state.childrenConfigs.takeIf { it.isNotEmpty() }?.let {
                { MyNavState(it.dropLast(1)) }
            }
        },
        childFactory = childFactory
    )

}
