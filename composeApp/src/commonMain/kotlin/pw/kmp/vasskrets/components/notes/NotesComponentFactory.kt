package pw.kmp.vasskrets.components.notes

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun interface NotesComponentFactory {
    operator fun invoke(notesId: Uuid): NotesComponent
}