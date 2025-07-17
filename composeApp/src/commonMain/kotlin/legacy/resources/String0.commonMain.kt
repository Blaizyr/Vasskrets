@file:OptIn(InternalResourceApi::class)

package legacy.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.StringResource

private const val MD: String = "composeResources/vasskrets.composeapp.generated.resources/"

internal val Res.string.cyclone: StringResource by lazy {
      StringResource("string:cyclone", "cyclone", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 10, 27),
      ))
    }

internal val Res.string.open_github: StringResource by lazy {
      StringResource("string:open_github", "open_github", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 38, 35),
      ))
    }

internal val Res.string.run: StringResource by lazy {
      StringResource("string:run", "run", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 74, 15),
      ))
    }

internal val Res.string.stop: StringResource by lazy {
      StringResource("string:stop", "stop", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 90, 20),
      ))
    }

internal val Res.string.theme: StringResource by lazy {
      StringResource("string:theme", "theme", setOf(
        ResourceItem(setOf(), "${MD}values/strings.commonMain.cvr", 111, 21),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainString0Resources(map: MutableMap<String, StringResource>) {
  map.put("cyclone", Res.string.cyclone)
  map.put("open_github", Res.string.open_github)
  map.put("run", Res.string.run)
  map.put("stop", Res.string.stop)
  map.put("theme", Res.string.theme)
}
