package pw.kmp.vasskrets.data

import kotlinx.serialization.KSerializer

interface StorageConfig<E> {
    val subDir: String
    val serializer: KSerializer<E>
}
