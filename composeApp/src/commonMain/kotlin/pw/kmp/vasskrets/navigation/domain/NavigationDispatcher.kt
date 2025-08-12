package pw.kmp.vasskrets.navigation.domain

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.KSerializer
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class GenericNavigationDispatcher<T : Any, K : Any>(
    componentContext: ComponentContext,
    private val serializer: KSerializer<MyNavState<T>>,
    private val childFactory: (T, ComponentContext) -> K,
) : ComponentContext by componentContext {

    private val navSource = SimpleNavigation<NavEvent<T>>()
//    private val currentState = MutableStateFlow<MyNavState<T>>(MyNavState(emptyList()))

    val childrenValue: Value<List<Child.Created<T, K>>> = children(
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

    fun open(config: T) {
        navSource.navigate(NavEvent.Open(config))
    }

    fun close(config: T) {
        navSource.navigate(NavEvent.Close(config))
    }

}
