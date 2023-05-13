package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.form.AppDateTextField
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate

class SubtractDaysCalculatorState(
    firstDate: LocalDate = LocalDate.now(),
    secondDate: LocalDate = LocalDate.now()
) {
    var firstDate by mutableStateOf(firstDate)
    var secondDate by mutableStateOf(secondDate)
}

@Composable
fun SubtractDaysCalculator(state: SubtractDaysCalculatorState, onValueChange: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_subtract_days_first_date),
            date = state.firstDate,
            onDateChange = {
                state.firstDate = it
                onValueChange()
            }
        )

        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_subtract_days_second_date),
            date = state.secondDate,
            onDateChange = {
                state.secondDate = it
                onValueChange()
            }
        )
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        Surface {
            SubtractDaysCalculator(
                state = SubtractDaysCalculatorState(
                    firstDate = LocalDate.of(2023, 5, 13),
                    secondDate = LocalDate.of(2023, 5, 16)
                ),
                onValueChange = {}
            )
        }
    }
}
