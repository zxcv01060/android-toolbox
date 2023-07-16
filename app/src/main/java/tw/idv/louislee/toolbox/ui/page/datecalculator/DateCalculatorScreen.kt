package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAB_PAST_FUTURE_DAYS = 0
private const val TAB_SUBTRACT_DAYS = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalculatorScreen(drawerState: DrawerState) {
    var selectedTabIndex by remember {
        mutableStateOf(TAB_PAST_FUTURE_DAYS)
    }

    val viewModel = hiltViewModel<DateCalculatorViewModel>()
    Content(
        drawerState = drawerState,
        selectedTabIndex = selectedTabIndex,
        onTabSelect = { selectedTabIndex = it },
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    drawerState: DrawerState,
    selectedTabIndex: Int,
    onTabSelect: (Int) -> Unit,
    viewModel: DateCalculatorViewModel
) {
    ToolboxTheme {
        AppToolbar(
            title = stringResource(id = R.string.date_calculator_title),
            drawerState = drawerState
        ) {
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
                        TAB_PAST_FUTURE_DAYS -> PastFutureDaysTabContent(viewModel = viewModel)
                        TAB_SUBTRACT_DAYS -> SubtractDaysTabContent(viewModel = viewModel)
                    }
                }
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

@Composable
private fun PastFutureDaysTabContent(viewModel: DateCalculatorViewModel) {
    val state = viewModel.pastFutureDaysCalculatorState

    CalculatorTabContent(
        calculatorContent = {
            PastFutureDaysCalculator(
                state = state,
                onValueChange = viewModel::onPastFutureDaysChange
            )
        },
        resultContent = {
            Text(
                text = stringResource(
                    id = if (state.isPast) {
                        R.string.date_calculator_past_future_days_past_result
                    } else {
                        R.string.date_calculator_past_future_days_future_result
                    },
                    state.safeDays,
                    viewModel.pastFutureDaysCalculateResult.format(
                        DateTimeFormatter.ofPattern(stringResource(id = R.string.common_date_include_week_format))
                    )
                )
            )
        }
    )
}

@Composable
private fun CalculatorTabContent(
    calculatorContent: @Composable () -> Unit,
    resultContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        calculatorContent()

        Spacer(modifier = Modifier.height(height = 16.dp))

        Text(text = stringResource(id = R.string.common_calculate_result))

        resultContent()
    }
}

@Composable
private fun SubtractDaysTabContent(viewModel: DateCalculatorViewModel) {
    val state = viewModel.subtractDaysCalculatorState

    CalculatorTabContent(
        calculatorContent = {
            SubtractDaysCalculator(
                state = state,
                onValueChange = viewModel::onSubtractDaysChange
            )
        },
        resultContent = {
            Text(
                text = stringResource(
                    id = R.string.date_calculator_subtract_days_result,
                    viewModel.subtractDaysCalculateResult
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun FirstTabPreview() {
    val viewModel = DateCalculatorViewModel()
    viewModel.pastFutureDaysCalculatorState.date = LocalDate.of(2023, 5, 15)
    viewModel.pastFutureDaysCalculatorState.days = 5

    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        selectedTabIndex = TAB_PAST_FUTURE_DAYS,
        onTabSelect = {},
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun SecondTabPreview() {
    val viewModel = DateCalculatorViewModel()
    viewModel.subtractDaysCalculatorState.firstDate = LocalDate.of(2023, 5, 10)
    viewModel.subtractDaysCalculatorState.secondDate = LocalDate.of(2023, 5, 14)

    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        selectedTabIndex = TAB_SUBTRACT_DAYS,
        onTabSelect = {},
        viewModel = viewModel
    )
}
