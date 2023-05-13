package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastFutureDaysCalculator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var date by rememberSaveable {
            mutableStateOf(LocalDate.now())
        }
        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_past_future_days_date),
            date = date,
            onDateChange = { date = it }
        )

        var isPast by rememberSaveable {
            mutableStateOf(false)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isPast, onCheckedChange = { isPast = it })

            Text(
                modifier = Modifier.clickable { isPast = isPast.not() },
                text = stringResource(id = R.string.date_calculator_past_future_days_is_past)
            )
        }

        var days by rememberSaveable {
            mutableStateOf<Int?>(0)
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = stringResource(id = R.string.date_calculator_past_future_days_count))
            },
            value = days?.toString() ?: "",
            onValueChange = { days = it.toIntOrNull() }
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        if (days == null) {
            return
        }
        Column {
            Text(text = stringResource(id = R.string.common_calculate_result))

            Text(
                text = stringResource(
                    id = if (isPast) {
                        R.string.date_calculator_past_future_days_past_result
                    } else {
                        R.string.date_calculator_past_future_days_future_result
                    },
                    days!!,
                    date.plusDays(days!!.toLong())
                        .format(
                            DateTimeFormatter.ofPattern(
                                stringResource(id = R.string.common_date_include_week_format)
                            )
                        )
                )
            )
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        Surface {
            PastFutureDaysCalculator()
        }
    }
}
