package pw.kmp.vasskrets.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import pw.kmp.vasskrets.components.root.Child

@OptIn(DelicateDecomposeApi::class)
class NavigationComponent(
    componentContext: ComponentContext,
    private val navigation: StackNavigation<NavigationConfig> = StackNavigation(),
    val navigationStack: Value<ChildStack<NavigationConfig, Child>>
) : ComponentContext by componentContext {

    fun navigateTo(target: NavigationConfig) {
        navigation.push(target)
    }
}