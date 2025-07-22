package pw.kmp.vasskrets.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pw.kmp.vasskrets.data.datasource.ChatDataSource
import pw.kmp.vasskrets.data.datasource.LocalChatDataSource
import pw.kmp.vasskrets.data.datasource.RemoteChatDataSource
import pw.kmp.vasskrets.data.repository.ChatRepository
import pw.kmp.vasskrets.data.storage.JsonStorage
import pw.kmp.vasskrets.domain.model.Chat

val sharedModule = module {
    single { JsonStorage() }
    single<ChatDataSource>(named("local")) { LocalChatDataSource(get(), Chat.serializer()) }
    single<ChatDataSource>(named("remoteApi")) { RemoteChatDataSource(get(), Chat.serializer()) }
    single<ChatDataSource>(named("remoteWebsocket")) {
        RemoteChatDataSource(
            get(),
            Chat.serializer()
        )
    }
    single {
        ChatRepository(
            get(named("local")),
            get(named("remoteApi")),
            get(named("remoteWebsocket"))
        )
    }
}
