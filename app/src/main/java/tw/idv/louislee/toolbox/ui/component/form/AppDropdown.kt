package tw.idv.louislee.toolbox.ui.component.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.model.DropdownOption
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
inline fun <reified T : Enum<T>> AppDropdown(
    noinline optionLabelSupplier: @Composable (T) -> String,
    noinline onOptionSelected: (T) -> Unit,
    selectedOptionValue: T?,
    modifier: Modifier = Modifier,
    label: String? = null,
    noinline optionFilter: (T) -> Boolean = { true }
) {
    val options = enumValues<T>()
        .filter(optionFilter)
        .map { DropdownOption(label = optionLabelSupplier(it), value = it) }

    AppDropdown(
        modifier = modifier,
        options = options,
        onOptionSelected = onOptionSelected,
        selectedOptionValue = selectedOptionValue,
        label = label
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdown(
    options: List<DropdownOption<T>>,
    onOptionSelected: (T) -> Unit,
    selectedOptionValue: T?,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        val selectedOption = options.firstOrNull { selectedOptionValue == it.value }
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = if (label != null) {
                { Text(text = label) }
            } else {
                null
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            value = selectedOption?.label ?: "",
            onValueChange = {}
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            for (option in options) {
                DropdownMenuItem(
                    text = { Text(text = option.label) },
                    onClick = {
                        onOptionSelected(option.value)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    val options = (1..1000)
        .map { DropdownOption(label = "選項$it", value = it) }
    var selectedOptionValue by remember {
        mutableStateOf<Int?>(null)
    }

    ToolboxTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        ) {
            AppDropdown(
                label = "標籤",
                options = options,
                onOptionSelected = { selectedOptionValue = it },
                selectedOptionValue = selectedOptionValue
            )

            Text(text = "目前選擇的值：$selectedOptionValue")
        }
    }
}
