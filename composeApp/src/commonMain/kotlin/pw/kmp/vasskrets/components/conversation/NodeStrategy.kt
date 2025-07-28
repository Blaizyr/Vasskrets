package pw.kmp.vasskrets.components.conversation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface NodeStrategy {
    fun createNew()
    fun open(id: Uuid)
    fun close(id: Uuid)
}
