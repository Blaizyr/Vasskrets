package pw.kmp.vasskrets.ui.windowing

import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface WindowManager {
    val windows: StateFlow<WindowManagerState>
    fun open(entry: Entry<*>)
    fun dock(windowId: Uuid): Entry<*>?
    fun close(componentId: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
data class WindowManagerState(
    val windows: Map<Uuid, Entry<*>> = emptyMap() // TODO Zdecydować: Czy faktycznie potrzeba zagnieżdżonego entry w entry (domain w windw)? #12
)