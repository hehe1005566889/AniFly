package ink.flybird.anifly.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ink.flybird.anifly.ui.extension.customTabIndicatorOffset

@Composable
fun AFTabContontroller (
    pages : Map<String, @Composable () -> Unit>
) {

    Column {
        var tabIndex by remember { mutableIntStateOf(0) }
        val tabs = pages.keys.toList()

        TabRow(
            selectedTabIndex = tabIndex,
            indicator = @Composable { tabPositions ->
                TabRowDefaults.Indicator(Modifier.customTabIndicatorOffset(tabPositions[tabIndex]))
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }


        pages[tabs[tabIndex]]?.let { it() };
    }

}

@Preview
@Composable
fun PreViewAFTC()
{
    AFTabContontroller(
        mapOf(
            Pair("Main") @Composable { Button(onClick = { /*TODO*/ }) {
                Text(text = "awa1")
            } },

            Pair("Main1") @Composable { Button(onClick = { /*TODO*/ }) {
                Text(text = "awa2")
            } },

        )
    )
}