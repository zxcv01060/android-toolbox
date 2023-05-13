package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.form.AppDateTextField
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate

class PastFutureDaysCalculatorState(
    date: LocalDate = LocalDate.now(),
    isPast: Boolean = false,
    days: Int? = null
) {
    var date by mutableStateOf(date)
    var isPast by mutableStateOf(isPast)
    var days by mutableStateOf(days)
    val signedDays
        get() = if (isPast) {
            -safeDays
        } else {
            safeDays
        }
    val safeDays get() = days ?: 0
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastFutureDaysCalculator(state: PastFutureDaysCalculatorState, onValueChange: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_past_future_days_date),
            date = state.date,
            onDateChange = {
                state.date = it
                onValueChange()
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.isPast,
                onCheckedChange = {
                    state.isPast = it
                    onValueChange()
                }
            )

            Text(
                modifier = Modifier.clickable {
                    state.isPast = state.isPast.not()
                    onValueChange()
                },
                text = stringResource(id = R.string.date_calculator_past_future_days_is_past)
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = stringResource(id = R.string.date_calculator_past_future_days_count))
            },
            value = state.days?.toString() ?: "",
            onValueChange = {
                state.days = it.toIntOrNull()
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
            PastFutureDaysCalculator(
                state = PastFutureDaysCalculatorState(
                    date = LocalDate.of(2023, 5, 13),
                    isPast = false,
                    days = 15
                ),
                onValueChange = {}
            )
        }
    }
}
