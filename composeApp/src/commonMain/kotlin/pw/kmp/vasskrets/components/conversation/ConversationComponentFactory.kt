package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun interface ConversationComponentFactory {
    operator fun invoke(conversationId: Uuid, context: ComponentContext?): ConversationComponent
}