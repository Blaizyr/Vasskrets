package pw.kmp.vasskrets.ui.windowing.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.ui.windowing.WindowManager
import kotlin.reflect.KClass

fun <T : Any> WindowManager.scopedReactiveTo(type: KClass<T>): StateFlow<List<Entry<T>>> {
    return this.state
        .map { managerState ->
            managerState.windows
                .filter { type.isInstance(it.component) }
                .map { it as Entry<T> }
        }.stateIn(
            scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

inline fun <reified T : Any> WindowManager.scopedReactive(): StateFlow<List<Entry<T>>> =
    scopedReactiveTo(T::class)
