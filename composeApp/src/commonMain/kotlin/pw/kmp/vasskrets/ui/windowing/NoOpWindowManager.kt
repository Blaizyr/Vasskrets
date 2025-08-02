package pw.kmp.vasskrets.ui.windowing

import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class NoOpWindowManager : WindowManager {
    override val windows: Map<Uuid, Entry<*>> = emptyMap()
    override fun open(entry: Entry<*>) {}
    override fun dock(windowId: Uuid): Entry<*>? = null
    override fun close(componentId: Uuid) {}
}
