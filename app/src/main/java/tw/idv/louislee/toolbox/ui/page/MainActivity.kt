package tw.idv.louislee.toolbox.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.page.datecalculator.PastFutureDaysCalculator
import tw.idv.louislee.toolbox.ui.page.datecalculator.SubtractDaysCalculator
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

const val TAB_PAST_FUTURE_DAYS = 0
const val TAB_SUBTRACT_DAYS = 1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedTabIndex by remember {
                mutableStateOf(TAB_PAST_FUTURE_DAYS)
            }

            Content(selectedTabIndex = selectedTabIndex, onTabSelect = { selectedTabIndex = it })
        }
    }
}

@Composable
private fun Content(selectedTabIndex: Int, onTabSelect: (Int) -> Unit) {
    ToolboxTheme {
        AppToolbar(title = stringResource(id = R.string.date_calculator_title)) {
            DateCalculatorGroup(selectedTabIndex = selectedTabIndex, onTabSelect = onTabSelect)
        }
    }
}

@Composable
private fun DateCalculatorGroup(selectedTabIndex: Int, onTabSelect: (Int) -> Unit) {
    Column {
        DateCalculatorTabRow(
            selectedTabIndex = selectedTabIndex,
            onTabSelect = onTabSelect
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        ) {
            when (selectedTabIndex) {
                TAB_PAST_FUTURE_DAYS -> PastFutureDaysCalculator()
                TAB_SUBTRACT_DAYS -> SubtractDaysCalculator()
                else -> throw IllegalArgumentException("不支援的Tab Index: $selectedTabIndex")
            }
        }
    }
}

@Composable
private fun DateCalculatorTabRow(selectedTabIndex: Int, onTabSelect: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        Tab(
            selected = selectedTabIndex == TAB_PAST_FUTURE_DAYS,
            onClick = { onTabSelect(TAB_PAST_FUTURE_DAYS) },
            text = {
                Text(
                    text = stringResource(id = R.string.date_calculator_past_future_days_title)
                )
            }
        )
        Tab(
            selected = selectedTabIndex == TAB_SUBTRACT_DAYS,
            onClick = { onTabSelect(TAB_SUBTRACT_DAYS) },
            text = {
                Text(
                    text = stringResource(id = R.string.date_calculator_subtract_days_title)
                )
            }
        )
    }
}

@AppPreview
@Composable
private fun FirstTabPreview() {
    Content(selectedTabIndex = TAB_PAST_FUTURE_DAYS, onTabSelect = {})
}

@AppPreview
@Composable
private fun SecondTabPreview() {
    Content(selectedTabIndex = TAB_SUBTRACT_DAYS, onTabSelect = {})
}
