package tw.idv.louislee.toolbox.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.page.datecalculator.DateCalculatorViewModel
import tw.idv.louislee.toolbox.ui.page.datecalculator.DateCalculatorViewModelForPreview
import tw.idv.louislee.toolbox.ui.page.datecalculator.DateCalculatorViewModelImpl
import tw.idv.louislee.toolbox.ui.page.datecalculator.PastFutureDaysCalculator
import tw.idv.louislee.toolbox.ui.page.datecalculator.PastFutureDaysCalculatorState
import tw.idv.louislee.toolbox.ui.page.datecalculator.SubtractDaysCalculator
import tw.idv.louislee.toolbox.ui.page.datecalculator.SubtractDaysCalculatorState
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAB_PAST_FUTURE_DAYS = 0
private const val TAB_SUBTRACT_DAYS = 1

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedTabIndex by remember {
                mutableStateOf(TAB_PAST_FUTURE_DAYS)
            }

            val viewModel = viewModel<DateCalculatorViewModelImpl>()
            Content(
                selectedTabIndex = selectedTabIndex,
                onTabSelect = { selectedTabIndex = it },
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun Content(
    selectedTabIndex: Int,
    onTabSelect: (Int) -> Unit,
    viewModel: DateCalculatorViewModel
) {
    ToolboxTheme {
        AppToolbar(title = stringResource(id = R.string.date_calculator_title)) {
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

@AppPreview
@Composable
private fun FirstTabPreview() {
    Content(
        selectedTabIndex = TAB_PAST_FUTURE_DAYS,
        onTabSelect = {},
        viewModel = DateCalculatorViewModelForPreview(
            pastFutureDaysCalculatorState = PastFutureDaysCalculatorState(
                date = LocalDate.of(2023, 5, 15),
                days = 5
            )
        )
    )
}

@AppPreview
@Composable
private fun SecondTabPreview() {
    Content(
        selectedTabIndex = TAB_SUBTRACT_DAYS,
        onTabSelect = {},
        viewModel = DateCalculatorViewModelForPreview(
            subtractDaysCalculatorState = SubtractDaysCalculatorState(
                firstDate = LocalDate.of(2023, 5, 10),
                secondDate = LocalDate.of(2023, 5, 14)
            )
        )
    )
}
