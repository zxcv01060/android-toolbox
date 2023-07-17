package tw.idv.louislee.toolbox.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tw.idv.louislee.toolbox.R

enum class DrawerScreen(val route: String) {
    DATE_CALCULATOR("DateCalculator"),
    URL_SHORTENER("UrlShortener"),
    GENERATOR("Generator"),
    ENCRYPTION("Encryption")
}

@Composable
fun DrawerScreen.title(): String = when (this) {
    DrawerScreen.DATE_CALCULATOR -> stringResource(id = R.string.date_calculator_title)
    DrawerScreen.URL_SHORTENER -> stringResource(id = R.string.url_shortener_title)
    DrawerScreen.GENERATOR -> stringResource(id = R.string.generator_title)
    DrawerScreen.ENCRYPTION -> stringResource(id = R.string.encryption_title)
}
