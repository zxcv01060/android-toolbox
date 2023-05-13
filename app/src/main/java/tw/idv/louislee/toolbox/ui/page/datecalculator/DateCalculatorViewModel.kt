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

interface DateCalculatorViewModel {
    val pastFutureDaysCalculatorState: PastFutureDaysCalculatorState
    val pastFutureDaysCalculateResult: LocalDate

    val subtractDaysCalculatorState: SubtractDaysCalculatorState
    val subtractDaysCalculateResult: Long

    fun onPastFutureDaysChange()

    fun onSubtractDaysChange()
}

@HiltViewModel
internal class DateCalculatorViewModelImpl @Inject constructor(

) : ViewModel(), DateCalculatorViewModel {
    override val pastFutureDaysCalculatorState: PastFutureDaysCalculatorState =
        PastFutureDaysCalculatorState()
    override var pastFutureDaysCalculateResult: LocalDate by mutableStateOf(LocalDate.now())

    override val subtractDaysCalculatorState: SubtractDaysCalculatorState =
        SubtractDaysCalculatorState()
    override var subtractDaysCalculateResult: Long by mutableStateOf(0)

    override fun onPastFutureDaysChange() {
        pastFutureDaysCalculateResult = pastFutureDaysCalculatorState.date.plusDays(
            pastFutureDaysCalculatorState.signedDays.toLong()
        )
    }

    override fun onSubtractDaysChange() {
        subtractDaysCalculateResult = abs(
            ChronoUnit.DAYS.between(
                subtractDaysCalculatorState.firstDate,
                subtractDaysCalculatorState.secondDate
            )
        )
    }
}

internal class DateCalculatorViewModelForPreview(
    override val pastFutureDaysCalculatorState: PastFutureDaysCalculatorState = PastFutureDaysCalculatorState(),
    pastFutureDaysCalculateResult: LocalDate = LocalDate.now(),
    override val subtractDaysCalculatorState: SubtractDaysCalculatorState = SubtractDaysCalculatorState(),
    subtractDaysCalculateResult: Long = 0
) : DateCalculatorViewModel {
    override var pastFutureDaysCalculateResult by mutableStateOf(pastFutureDaysCalculateResult)
    override var subtractDaysCalculateResult: Long by mutableStateOf(subtractDaysCalculateResult)

    override fun onPastFutureDaysChange() {
        pastFutureDaysCalculateResult = pastFutureDaysCalculatorState.date.plusDays(
            pastFutureDaysCalculatorState.signedDays.toLong()
        )
    }

    override fun onSubtractDaysChange() {
        subtractDaysCalculateResult = abs(
            ChronoUnit.DAYS.between(
                subtractDaysCalculatorState.firstDate,
                subtractDaysCalculatorState.secondDate
            )
        )
    }
}
