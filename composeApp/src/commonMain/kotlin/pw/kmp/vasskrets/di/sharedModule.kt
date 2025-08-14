package pw.kmp.vasskrets.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.data.conversation.ConversationRepository
import pw.kmp.vasskrets.data.conversation.ConversationStorageConfig
import pw.kmp.vasskrets.data.conversation.datasource.ConversationDataSource
import pw.kmp.vasskrets.data.conversation.datasource.LocalConversationDataSource
import pw.kmp.vasskrets.data.conversation.datasource.RemoteConversationDataSource
import pw.kmp.vasskrets.domain.conversation.usecase.ConversationsMetadataUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.CreateNewConversationUseCase
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import pw.kmp.vasskrets.ui.windowing.NoOpWindowManager
import pw.kmp.vasskrets.ui.windowing.WindowManager

val sharedModule = module {
    single { JsonStorage() }
    single { ConversationStorageConfig }
    single<ConversationDataSource>(named("local")) { LocalConversationDataSource(get(), get()) }
    single<ConversationDataSource>(named("remoteApi")) { RemoteConversationDataSource() }
    single<ConversationDataSource>(named("remoteWebsocket")) { RemoteConversationDataSource() }
    single {
        ConversationRepository(
            get(named("local")),
            get(named("remoteApi")),
            get(named("remoteWebsocket"))
        )
    }
    single { ConversationsMetadataUseCase(get()) }
    single { CreateNewConversationUseCase(get()) }
    single { SendTextMessageUseCase(get()) }
    single<WindowManager> { NoOpWindowManager() }
}
