package ink.flybird.anifly.ui.pages.home.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import ink.flybird.anifly.ui.components.AFDialog
import ink.flybird.anifly.ui.components.AFSearchBar2
import ink.flybird.anifly.ui.pages.AFRouteName

@Composable
fun MainPageHeaderView(
    navController: NavController,
    text : String? = null
) {

    var dialog by rememberSaveable { mutableStateOf(false) }
    var pageId by rememberSaveable { mutableStateOf(false) }

    AFSearchBar2(
        barButton = Icons.Default.AccountCircle,
        onBarButtonAction = {
            dialog = true
        },
        onBarSearch = {
            navController.navigate("${AFRouteName.SearchPage}/$it")
        },
        onActive = {
            pageId = !pageId
        },
        t = text
    )


    if(dialog)
    {
        AFDialog(title = "提示", content = "在线功能正在开发中...", button = "确定") {
            dialog = false
        }
    }

}