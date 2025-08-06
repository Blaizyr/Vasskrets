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

    private val _windows = MutableStateFlow(WindowManagerState())
    override val windows: StateFlow<WindowManagerState> = _windows.asStateFlow()

    override fun open(entry: Entry<*>) {
        val newWindowId = Uuid.random()
        _windows.update { currentState ->
            currentState.copy(
                windows = currentState.windows + (newWindowId to entry)
            )
        }
    }

    override fun dock(windowId: Uuid): Entry<*>? {
        val entryToDock = _windows.value.windows[windowId]
        if (entryToDock != null) {
            _windows.update { currentState ->
                currentState.copy(
                    windows = currentState.windows - windowId
                )
            }
        }
        return entryToDock
    }

    override fun close(componentId: Uuid) {
        val matchingWindowEntry = _windows.value.windows.entries.find { (_, entry) ->
            entry.componentId == componentId
        }
        if (matchingWindowEntry != null) {
            _windows.update { currentState ->
                currentState.copy(
                    windows = currentState.windows - matchingWindowEntry.key
                )
            }
        }
    }
}
