package pw.kmp.vasskrets.data.conversation.datasource

import pw.kmp.vasskrets.domain.conversation.model.Conversation
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RemoteConversationDataSource : ConversationDataSource {
    override suspend fun createNewChat(): Conversation {
        TODO("Not yet implemented")
    }

  /*  override suspend fun loadChatMetadata(): List<ConversationMetadata> {
        TODO("Not yet implemented")
    }*/

    override suspend fun loadChat(chatId: Uuid): Conversation? {
        TODO("Not yet implemented")
    }

    override suspend fun saveChat(conversation: Conversation): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChat(chatId: Uuid): Boolean {
        TODO("Not yet implemented")
    }

}