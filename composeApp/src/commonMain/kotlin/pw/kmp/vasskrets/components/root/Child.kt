package pw.kmp.vasskrets.components.root

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.components.home.HomeComponent
import pw.kmp.vasskrets.components.notes.NotesComponent

@Serializable
sealed interface Child {

    @Serializable
    data class Home(val component: HomeComponent) : Child

    @Serializable
    data class Notes(val component: NotesComponent) : Child

    @Serializable
    data class Conversations(val component: @Contextual ConversationNodeComponent) : Child

    @Serializable
    data class Settings(val dupa : String) : Child

    @Serializable
    data class Profile(val dupa : String) : Child

}
