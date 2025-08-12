package pw.kmp.vasskrets.navigation.extension

import com.arkivanov.decompose.Child

inline fun <reified C : Any, reified I : Any> Child.Created<*, *>.asTypedChild(): Child.Created<C, I>? {
    return if (configuration is C && instance is I) {
        @Suppress("UNCHECKED_CAST")
        this as Child.Created<C, I>
    } else null
}
