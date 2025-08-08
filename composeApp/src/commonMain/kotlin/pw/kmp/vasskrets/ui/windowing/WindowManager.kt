package pw.kmp.vasskrets.ui.windowing

import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.components.DomainComponentEntry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface WindowManager {
    val state: StateFlow<WindowManagerState>
    fun open(entry: DomainComponentEntry<*>)
    fun dock(windowId: Uuid): DomainComponentEntry<*>?
    fun close(componentId: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
data class WindowManagerState(
    val windows: List<DomainComponentEntry<*>> = emptyList(),
)