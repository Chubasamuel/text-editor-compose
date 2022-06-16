package jp.kaleidot725.texteditor

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color

@Composable
fun TextEditor(modifier: Modifier = Modifier) {
    val linesState by rememberTextEditorState(DemoText)

    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = linesState.fields,
            key = { _, item -> item.id }
        ) { index, textFieldState ->
            val focusRequester by remember { mutableStateOf(FocusRequester()) }

            LaunchedEffect(textFieldState.isSelected) {
                if (textFieldState.isSelected) focusRequester.requestFocus()
            }

            TextLine(
                number = (index + 1).toString().padStart(3, '0'),
                textFieldValue = textFieldState.value,
                onUpdateText = { newText ->
                    linesState.updateField(targetIndex = index, textFieldValue = newText)
                },
                onAddNewLine = { newText ->
                    linesState.splitField(targetIndex = index, textFieldValue = newText)
                },
                onDeleteNewLine = {
                    linesState.deleteField(targetIndex = index)
                },
                focusRequester = focusRequester,
                onFocus = {
                    linesState.selectField(targetIndex = index)
                },
                modifier = Modifier.background(if (textFieldState.isSelected) Color(0x80eaffea) else Color.White)
            )
        }
    }
}
