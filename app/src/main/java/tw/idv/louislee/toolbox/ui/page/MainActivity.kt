package tw.idv.louislee.toolbox.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import tw.idv.louislee.toolbox.ui.page.generagor.GeneratorScreen
import tw.idv.louislee.toolbox.ui.page.urlshortener.UrlShortenerScreen
import tw.idv.louislee.toolbox.ui.preview.DrawerValuePreviewParameterProvider
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

                    for (screen in DrawerScreen.values()) {
                        NavigationDrawerItem(
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            label = { Text(text = screen.title()) },
                            selected = navController.currentDestination?.route == screen.route,
                            onClick = {
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
                composable(DrawerScreen.GENERATOR.route) {
                    GeneratorScreen(drawerState = drawerState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun Preview(@PreviewParameter(DrawerValuePreviewParameterProvider::class) value: DrawerValue) {
    Content(drawerState = rememberDrawerState(initialValue = value))
}
