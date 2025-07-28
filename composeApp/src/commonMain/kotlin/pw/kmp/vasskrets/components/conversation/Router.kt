package pw.kmp.vasskrets.components.conversation

import kotlinx.coroutines.flow.StateFlow
import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface Router {
    val activeComponents: StateFlow<List<Entry<*>>>
    fun open(id: Uuid)
    fun close(id: Uuid)
    fun createNew()
}
