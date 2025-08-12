package pw.kmp.vasskrets.navigation.domain

import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.ChildNavState.Status.CREATED
import com.arkivanov.decompose.router.children.ChildNavState.Status.RESUMED
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import kotlinx.serialization.Serializable

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