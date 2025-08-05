package pw.kmp.vasskrets.ui.windowing

import pw.kmp.vasskrets.components.Entry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DefaultWindowManager : WindowManager {

    private val _openWindows = mutableMapOf<Uuid, Entry<*>>()
    override val windows: Map<Uuid, Entry<*>> get() = _openWindows

    override fun open(entry: Entry<*>) {
        _openWindows[entry.componentId] = entry
    }

    override fun dock(windowId: Uuid): Entry<*>? {
        val entryToDock = windows[windowId]
        _openWindows.values.remove(entryToDock)
        return entryToDock
    }

    override fun close(componentId: Uuid) {
        val toRemove = _openWindows.keys.find { it == componentId }
        _openWindows.remove(toRemove)
    }
}