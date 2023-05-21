package ink.flybird.anifly.ui.pages.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import ink.flybird.anifly.ui.pages.AFRouteName

data class NavigationItem(
    val label : String,
    val icon : ImageVector,
    val dest : String
)

object RouteNavigation {


    var selectID : String = "Home"

    val NAVIGATIONS : List<NavigationItem> = listOf(
        NavigationItem("Home", Icons.Default.Home, AFRouteName.HomePage),
        NavigationItem("Themes", Icons.Default.Directions, AFRouteName.HomePage),
        NavigationItem("Community", Icons.Default.Comment, AFRouteName.HomePage) //TODO Fix Up
    )
}