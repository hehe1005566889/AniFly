package ink.flybird.anifly.ui.pages.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import ink.flybird.anifly.ui.pages.AFRouteName

@Deprecated("Not In Used")
data class NavigationItem(
    val label : String,
    val icon : ImageVector,
    val dest : String
)

@Deprecated("Not In Used")
object RouteNavigation {


    var selectID : String = "主页"

    val NAVIGATIONS : List<NavigationItem> = listOf(
        NavigationItem("主页", Icons.Default.Home, AFRouteName.HomePage),
        NavigationItem("分类", Icons.Default.Directions, AFRouteName.HomePage),
        NavigationItem("社区", Icons.Default.Comment, AFRouteName.HomePage) //TODO Fix Up
    )
}