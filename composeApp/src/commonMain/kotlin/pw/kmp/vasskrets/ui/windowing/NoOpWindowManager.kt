package pw.kmp.vasskrets.ui.windowing

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class NoOpWindowManager : WindowManager {
    private val _windows = MutableStateFlow(WindowManagerState())
    override val state: StateFlow<WindowManagerState> = _windows.asStateFlow()
    override fun open(entry: Entry<*>) {}
    override fun dock(windowId: Uuid): Entry<*>? = null
    override fun close(componentId: Uuid) {}
}
