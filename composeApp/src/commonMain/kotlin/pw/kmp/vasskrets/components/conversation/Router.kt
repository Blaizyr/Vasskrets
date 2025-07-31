package pw.kmp.vasskrets.components.conversation

import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.navigation.ViewIntent

interface Router<Config> {
    val routeConfigs: StateFlow<List<Config>>
    fun handle(intent: ViewIntent)
}
