package ink.flybird.anifly.ui.pages

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ink.flybird.anifly.data.common.AFSetting.useDarkTheme
import ink.flybird.anifly.ui.extension.animatedComposable
import ink.flybird.anifly.ui.pages.detil.DetilPageView
import ink.flybird.anifly.ui.pages.home.HomePageView
import ink.flybird.anifly.ui.pages.player.PlayerPageView
import ink.flybird.anifly.ui.theme.AppTheme

fun getID(name : String, navBackStackEntry: NavBackStackEntry) : String {
    val str = navBackStackEntry.arguments?.getString(name)
    if(str.isNullOrBlank())
        throw NullPointerException("Navigation : $name Is Null Or Empty")
    Log.i("anifly", str)
    return str
}

@OptIn(ExperimentalAnimationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun MainEntry(

) {
    val navController = rememberAnimatedNavController()

    AppTheme(useDarkTheme = false) {

        rememberSystemUiController().run {
            setStatusBarColor(Color.Transparent, !useDarkTheme)
            setSystemBarsColor(Color.Transparent, !useDarkTheme)
            setNavigationBarColor(Color.Transparent, !useDarkTheme)
        }

        AnimatedNavHost(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            navController = navController,
            startDestination = AFRouteName.HomePage
        ) {

            animatedComposable(route = AFRouteName.HomePage)
            {
                HomePageView(navController = navController)
            }

            animatedComposable(
                route = "${AFRouteName.PlayerPage}/{movieID}",
                arguments = listOf(navArgument("movieID") { type = NavType.StringType } )
            ) {backStackEntry ->
                val id = getID("movieID", backStackEntry)
                PlayerPageView(navController = navController, id = id)
            }

            animatedComposable(
                route = "${AFRouteName.BangumiDetailPage}/{bangumiID}",
                arguments = listOf(navArgument("bangumiID") { type = NavType.StringType } )
            ) {backStackEntry ->
                val id = getID("bangumiID", backStackEntry)
                DetilPageView(navController = navController, id = id)
            }
        }
    }
}