package pw.kmp.vasskrets.ui.windowing.extension

import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.ui.windowing.DomainWindowScope
import pw.kmp.vasskrets.ui.windowing.WindowManager
import kotlin.reflect.KClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Suppress("UNCHECKED_CAST")
fun <T : Any> WindowManager.scopedTo(type: KClass<T>): DomainWindowScope<T> {
    return object : DomainWindowScope<T> {

        override val windows: Map<Uuid, Entry<T>>
            get() = this@scopedTo.windows
                .filterValues { type.isInstance(it.component) }
                .mapValues { (_, v) -> v as Entry<T> }

        override fun open(entry: Entry<T>) {
            this@scopedTo.open(entry)
        }

        override fun dock(entryId: Uuid): Entry<T>? {
            val entry = this@scopedTo.dock(entryId)
            return if (type.isInstance(entry?.component)) {
                entry as Entry<T>
            } else null
        }

        override fun close(entryId: Uuid) {
            this@scopedTo.close(entryId)
        }
    }
}

inline fun <reified T : Any> WindowManager.scoped(): DomainWindowScope<T> =
    scopedTo(T::class)
