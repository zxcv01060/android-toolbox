package tw.idv.louislee.toolbox.ui.component.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import tw.idv.louislee.toolbox.ui.AppPreview

@Composable
fun AppIconButton(icon: ImageVector, @StringRes contentDescription: Int, onClick: () -> Unit) {
    AppIconButton(
        icon = icon,
        contentDescription = stringResource(id = contentDescription),
        onClick = onClick
    )
}

@Composable
fun AppIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
fun AppIconButton(
    @DrawableRes drawable: Int,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    AppIconButton(
        drawable = drawable,
        contentDescription = stringResource(id = contentDescription),
        onClick = onClick
    )
}

@Composable
fun AppIconButton(@DrawableRes drawable: Int, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(painter = painterResource(id = drawable), contentDescription = contentDescription)
    }
}

@Composable
fun AppIconButton(painter: Painter, @StringRes contentDescription: Int, onClick: () -> Unit) {
    AppIconButton(
        painter = painter,
        contentDescription = stringResource(id = contentDescription),
        onClick = onClick
    )
}

@Composable
fun AppIconButton(painter: Painter, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(painter = painter, contentDescription = contentDescription)
    }
}

@AppPreview
@Composable
private fun Preview() {
    Column {
        var text by remember {
            mutableStateOf("")
        }
        AppIconButton(icon = Icons.Filled.Search, contentDescription = "") {
            text = "Click!"
        }

        Text(text = text)
    }
}
