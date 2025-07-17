@file:OptIn(InternalResourceApi::class)

package legacy.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/vasskrets.composeapp.generated.resources/"

internal val Res.drawable.ic_cyclone: DrawableResource by lazy {
      DrawableResource("drawable:ic_cyclone", setOf(
        ResourceItem(setOf(), "${MD}drawable/ic_cyclone.xml", -1, -1),
      ))
    }

internal val Res.drawable.ic_dark_mode: DrawableResource by lazy {
      DrawableResource("drawable:ic_dark_mode", setOf(
        ResourceItem(setOf(), "${MD}drawable/ic_dark_mode.xml", -1, -1),
      ))
    }

internal val Res.drawable.ic_light_mode: DrawableResource by lazy {
      DrawableResource("drawable:ic_light_mode", setOf(
        ResourceItem(setOf(), "${MD}drawable/ic_light_mode.xml", -1, -1),
      ))
    }

internal val Res.drawable.ic_rotate_right: DrawableResource by lazy {
      DrawableResource("drawable:ic_rotate_right", setOf(
        ResourceItem(setOf(), "${MD}drawable/ic_rotate_right.xml", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("ic_cyclone", Res.drawable.ic_cyclone)
  map.put("ic_dark_mode", Res.drawable.ic_dark_mode)
  map.put("ic_light_mode", Res.drawable.ic_light_mode)
  map.put("ic_rotate_right", Res.drawable.ic_rotate_right)
}
