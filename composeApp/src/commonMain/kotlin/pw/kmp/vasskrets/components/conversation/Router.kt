package pw.kmp.vasskrets.components.conversation

import kotlinx.coroutines.flow.StateFlow

interface Router<Config> {
    val routeConfigs: StateFlow<List<Config>>
}
