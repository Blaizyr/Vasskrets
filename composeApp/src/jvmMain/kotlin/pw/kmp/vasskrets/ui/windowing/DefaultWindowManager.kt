package pw.kmp.vasskrets.ui.windowing

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DefaultWindowManager : WindowManager {

    private val _state = MutableStateFlow(WindowManagerState())
    override val state: StateFlow<WindowManagerState> = _state.asStateFlow()

    override fun open(entry: Entry<*>) {
        _state.update { currentState ->
            currentState.copy(
                windows = currentState.windows + entry
            )
        }
    }

    override fun dock(windowId: Uuid): Entry<*>? {
        val entryToDock = state.value.windows.find { it == windowId }
        if (entryToDock != null) {
            _state.update { currentState ->
                currentState.copy(
                    windows = currentState.windows - entryToDock
                )
            }
        }
        return entryToDock
    }

    override fun close(componentId: Uuid) {
        val matchingWindowEntry = _state.value.windows.find { entry ->
            entry.componentId == componentId
        }
        if (matchingWindowEntry != null) {
            _state.update { currentState ->
                currentState.copy(
                    windows = currentState.windows - matchingWindowEntry
                )
            }
        }
    }
}
