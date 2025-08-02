package pw.kmp.vasskrets.ui.windowing

import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface WindowManager {
    val windows: Map<Uuid, Entry<*>>
    fun open(entry: Entry<*>)
    fun dock(windowId: Uuid): Entry<*>?
    fun close(componentId: Uuid)
}
