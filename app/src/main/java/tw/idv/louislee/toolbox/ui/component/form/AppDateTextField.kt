package tw.idv.louislee.toolbox.ui.component.form

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateTextField(
    date: LocalDate?,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    format: String = stringResource(id = R.string.common_date_format),
    label: String? = null,
    leadingIcon: @Composable () -> Unit = {
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = label)
    }
) {
    val interactionSource by remember {
        mutableStateOf(MutableInteractionSource())
    }
    val isPressed by interactionSource.collectIsPressedAsState()

    OutlinedTextField(
        modifier = modifier,
        readOnly = true,
        interactionSource = interactionSource,
        label = label?.let {
            { Text(text = it) }
        },
        leadingIcon = leadingIcon,
        value = date?.format(DateTimeFormatter.ofPattern(format)) ?: "",
        onValueChange = {}
    )

    val calendarState = rememberUseCaseState()
    if (isPressed) {
        calendarState.show()
    }
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date(
            selectedDate = date,
            onNegativeClick = calendarState::hide
        ) {
            onDateChange(it)
        }
    )
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        Surface {
            var dateTime by remember {
                mutableStateOf(
                    LocalDate.of(2023, 4, 18)
                )
            }

            AppDateTextField(
                modifier = Modifier.fillMaxWidth(),
                date = dateTime,
                onDateChange = { dateTime = it }
            )
        }
    }
}
