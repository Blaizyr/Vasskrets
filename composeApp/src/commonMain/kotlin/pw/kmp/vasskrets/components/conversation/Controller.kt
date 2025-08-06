package pw.kmp.vasskrets.components.conversation

import kotlinx.coroutines.flow.StateFlow

interface Controller<Config> {
    val availableItems: StateFlow<List<Config>>
}
