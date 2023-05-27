package ink.flybird.anifly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ink.flybird.anifly.ui.components.AFVideoPlayer

@Composable
fun AFFullScreenPlayer(

) {

    var state by remember { mutableStateOf(false) }

    if(state)
    {
        val context = LocalContext.current
    }

  //  val controller = AFPlayerController()


}

class FullScreenPlayerActivity(
) : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

        }
    }
}