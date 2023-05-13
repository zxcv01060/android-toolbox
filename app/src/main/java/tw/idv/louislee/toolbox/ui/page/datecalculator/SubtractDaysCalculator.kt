package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.form.AppDateTextField
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
fun SubtractDaysCalculator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var firstDate by rememberSaveable {
            mutableStateOf(LocalDate.now())
        }
        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_subtract_days_first_date),
            date = firstDate,
            onDateChange = { firstDate = it }
        )

        var secondDate by rememberSaveable {
            mutableStateOf(LocalDate.now())
        }
        AppDateTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.date_calculator_subtract_days_second_date),
            date = secondDate,
            onDateChange = { secondDate = it }
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        Column {
            Text(text = stringResource(id = R.string.common_calculate_result))

            Text(
                text = stringResource(
                    id = R.string.date_calculator_subtract_days_result,
                    abs(ChronoUnit.DAYS.between(firstDate, secondDate))
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
            SubtractDaysCalculator()
        }
    }
}
