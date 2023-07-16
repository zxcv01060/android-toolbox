package tw.idv.louislee.toolbox.ui.page.datecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class DateCalculatorViewModel @Inject constructor() : ViewModel() {
    val pastFutureDaysCalculatorState: PastFutureDaysCalculatorState =
        PastFutureDaysCalculatorState()
    var pastFutureDaysCalculateResult: LocalDate by mutableStateOf(LocalDate.now())

    val subtractDaysCalculatorState: SubtractDaysCalculatorState =
        SubtractDaysCalculatorState()
    var subtractDaysCalculateResult: Long by mutableStateOf(0)

    fun onPastFutureDaysChange() {
        pastFutureDaysCalculateResult = pastFutureDaysCalculatorState.date.plusDays(
            pastFutureDaysCalculatorState.signedDays.toLong()
        )
    }

    fun onSubtractDaysChange() {
        subtractDaysCalculateResult = abs(
            ChronoUnit.DAYS.between(
                subtractDaysCalculatorState.firstDate,
                subtractDaysCalculatorState.secondDate
            )
        )
    }
}
