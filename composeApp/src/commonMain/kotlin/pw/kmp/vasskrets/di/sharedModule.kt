package pw.kmp.vasskrets.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.data.conversation.ConversationRepository
import pw.kmp.vasskrets.data.conversation.datasource.ConversationDataSource
import pw.kmp.vasskrets.data.conversation.datasource.LocalConversationDataSource
import pw.kmp.vasskrets.data.conversation.datasource.RemoteConversationDataSource
import pw.kmp.vasskrets.domain.conversation.model.Conversation
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase

val sharedModule = module {
    single { JsonStorage() }
    single<ConversationDataSource>(named("local")) { LocalConversationDataSource(get(), Conversation.serializer()) }
    single<ConversationDataSource>(named("remoteApi")) { RemoteConversationDataSource(get(), Conversation.serializer()) }
    single<ConversationDataSource>(named("remoteWebsocket")) {
        RemoteConversationDataSource(
            get(),
            Conversation.serializer()
        )
    }
    single {
        ConversationRepository(
            get(named("local")),
            get(named("remoteApi")),
            get(named("remoteWebsocket"))
        )
    }
    single { CreateNewConversationUseCase(get()) }
    single { SendTextMessageUseCase(get()) }
}
