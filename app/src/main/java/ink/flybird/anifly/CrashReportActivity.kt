package ink.flybird.anifly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AFCrashPanel(
    reason : String
) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(5.dp)) {

        Text(text = "We're Sorry", fontSize = 32.sp, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "AniFly Was Crashing For Some Reason", fontSize = 18.sp, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(text = "Current Result : $reason", fontSize = 18.sp, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(2.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 5.dp)) {
                Text(text = "Report")
            }
            Button(onClick = {

            }, modifier = Modifier.padding(start = 5.dp)) {
                Text(text = "Restart")
            }
        }
    }
}

@Preview
@Composable
fun PreviewCSP()
{
    AFCrashPanel("999666")
}

class CrashReportActivity() : ComponentActivity() {

    constructor(str : String) : this() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AFCrashPanel("")
        }
    }
}