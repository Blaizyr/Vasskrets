package pw.kmp.vasskrets.domain.conversation.usecase

import pw.kmp.vasskrets.data.conversation.ConversationRepository
import pw.kmp.vasskrets.domain.conversation.model.Participant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SendTextMessageUseCase(
    private val conversationRepository: ConversationRepository
) {
    operator fun invoke(
        conversationId: Uuid,
        sender: Participant? = Participant.USER,
        text: String
    ) {
//        chatRepository.sendMessage(conversationId, sender, text)
        TODO("Not yet implemented in repo")
    }
}
