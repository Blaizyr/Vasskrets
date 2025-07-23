package pw.kmp.vasskrets.components.notes

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper

data class NotesState(val sessionId: String)
interface NotesComponent
class DefaultNotesComponent(componentContext: ComponentContext) : NotesComponent, ComponentContext by componentContext, InstanceKeeper.Instance
