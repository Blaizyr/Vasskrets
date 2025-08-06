package pw.kmp.vasskrets.ui.windowing

import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface DomainWindowScope<T : Any> {
    val windows: Map<Uuid, Entry<T>>
    fun open(entry: Entry<T>)
    fun dock(entryId: Uuid): Entry<T>?
    fun close(entryId: Uuid)
}
