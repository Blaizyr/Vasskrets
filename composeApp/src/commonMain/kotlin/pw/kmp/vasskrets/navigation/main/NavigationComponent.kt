package pw.kmp.vasskrets.navigation.main

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
    private val navigation: StackNavigation<MainNavigationConfig> = StackNavigation(),
    val navigationStack: Value<ChildStack<MainNavigationConfig, Child>>
) : ComponentContext by componentContext {

    fun navigateTo(target: MainNavigationConfig) {
        navigation.push(target)
    }
}
