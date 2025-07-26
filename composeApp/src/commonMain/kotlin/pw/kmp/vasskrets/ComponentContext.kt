package pw.kmp.vasskrets

import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun Lifecycle.createCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    this.subscribe(object : Lifecycle.Callbacks {
        override fun onDestroy() {
            scope.cancel()
        }
    })

    return scope
}
