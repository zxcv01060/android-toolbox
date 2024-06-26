package tw.idv.louislee.toolbox.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.page.datecalculator.DateCalculatorScreen
import tw.idv.louislee.toolbox.ui.page.encryption.EncryptionScreen
import tw.idv.louislee.toolbox.ui.page.generator.GeneratorScreen
import tw.idv.louislee.toolbox.ui.page.pdf.PdfScreen
import tw.idv.louislee.toolbox.ui.page.urlshortener.UrlShortenerScreen
import tw.idv.louislee.toolbox.ui.preview.DrawerValuePreviewParameterProvider
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = DrawerScreen.DATE_CALCULATOR.route
) {
    ToolboxTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    val coroutineScope = rememberCoroutineScope()
                    Spacer(modifier = Modifier.height(12.dp))

                    var selectedItem by remember {
                        mutableStateOf(startDestination)
                    }

                    for (screen in DrawerScreen.entries) {
                        NavigationDrawerItem(
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            label = { Text(text = screen.title()) },
                            selected = selectedItem == screen.route,
                            onClick = {
                                selectedItem = screen.route
                                navController.navigate(route = screen.route)
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(DrawerScreen.DATE_CALCULATOR.route) {
                    DateCalculatorScreen(drawerState = drawerState)
                }
                composable(DrawerScreen.URL_SHORTENER.route) {
                    UrlShortenerScreen(drawerState = drawerState)
                }
                composable(DrawerScreen.PDF.route) {
                    PdfScreen(drawerState = drawerState)
                }
                composable(DrawerScreen.GENERATOR.route) {
                    GeneratorScreen(drawerState = drawerState)
                }
                composable(DrawerScreen.ENCRYPTION.route) {
                    EncryptionScreen(drawerState = drawerState)
                }
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview(@PreviewParameter(DrawerValuePreviewParameterProvider::class) value: DrawerValue) {
    Content(drawerState = rememberDrawerState(initialValue = value))
}
