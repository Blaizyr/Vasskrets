package pw.kmp.vasskrets.components.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper

data class HomeState(val sessionId: String)
interface HomeComponent
class DefaultHomeComponent(componentContext: ComponentContext) : HomeComponent, ComponentContext by componentContext, InstanceKeeper.Instance
