@file:OptIn(InternalResourceApi::class)

package legacy.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem
import org.jetbrains.compose.resources.getResourceUri
import org.jetbrains.compose.resources.readResourceBytes

private const val MD: String = "composeResources/vasskrets.composeapp.generated.resources/"

internal val Res.font.IndieFlower_Regular: FontResource by lazy {
      FontResource("font:IndieFlower_Regular", setOf(
        ResourceItem(setOf(), "${MD}font/IndieFlower-Regular.ttf", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainFont0Resources(map: MutableMap<String, FontResource>) {
  map.put("IndieFlower_Regular", Res.font.IndieFlower_Regular)
}

internal object Res {
  /**
   * Reads the content of the resource file at the specified path and returns it as a byte array.
   *
   * Example: `val bytes = Res.readBytes("files/key.bin")`
   *
   * @param path The path of the file to read in the compose resource's directory.
   * @return The content of the file as a byte array.
   */
  public suspend fun readBytes(path: String): ByteArray =
      readResourceBytes("composeResources/vasskrets.composeapp.generated.resources/" + path)

  /**
   * Returns the URI string of the resource file at the specified path.
   *
   * Example: `val uri = Res.getUri("files/key.bin")`
   *
   * @param path The path of the file in the compose resource's directory.
   * @return The URI string of the file.
   */
  public fun getUri(path: String): String =
      getResourceUri("composeResources/vasskrets.composeapp.generated.resources/" + path)

  public object drawable

  public object string

  public object array

  public object plurals

  public object font
}