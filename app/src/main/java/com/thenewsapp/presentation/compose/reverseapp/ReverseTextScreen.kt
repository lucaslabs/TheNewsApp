package com.thenewsapp.presentation.compose.reverseapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.thenewsapp.R
import com.thenewsapp.presentation.compose.theme.AppTheme
import com.thenewsapp.presentation.compose.theme.Large
import com.thenewsapp.presentation.compose.theme.Medium
import com.thenewsapp.presentation.compose.theme.Small

@Composable
fun ReverseTextScreen(viewModel: ReverseTextViewModel = ReverseTextViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        val reversedTextState: State<String> = viewModel.reversedText.observeAsState("")

        val inputTextListState: State<List<String>> =
            viewModel.inputTextList.observeAsState(initial = emptyList())

        val paddingModifier = Modifier
            .padding(horizontal = Large, vertical = Medium)
            .fillMaxWidth()

        ReversedTextSection(
            text = reversedTextState.value,
            onTextValueChange = { text -> viewModel.onTextValueChange(text) },
            modifier = paddingModifier
        )

        InputTextListSection(
            textList = inputTextListState.value,
            modifier = paddingModifier
        )
    }
}

@Composable
fun ReversedTextSection(
    text: String,
    onTextValueChange: (String) -> Unit,
    modifier: Modifier,
) {
    var inputTextState: String by remember { mutableStateOf(value = "") }

    Text(
        text = stringResource(id = R.string.reversed_text, text),
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )

    TextField(
        value = inputTextState,
        onValueChange = { inputTextState = it },
        label = { Text(text = stringResource(id = R.string.enter_text)) },
        modifier = modifier
    )

    Button(
        onClick = { onTextValueChange(inputTextState) },
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.reverse_text))
    }
}

@Composable
fun InputTextListSection(textList: List<String>, modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.original_input_list),
        modifier = modifier
    )

    LazyColumn(modifier = modifier) {
        items(textList) { text ->
            InputTextItem(text = text)
        }
    }
}

@Composable
fun InputTextItem(text: String) {
    Text(text = text, Modifier.padding(vertical = Small))
    Divider(color = Color.Black)
}

@Preview(
    name = "portrait",
    showBackground = true,
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Preview(
    name = "landscape",
    showBackground = true,
    device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"
)
@Preview(
    name = "foldable",
    showBackground = true,
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480"
)
@Preview(
    name = "tablet",
    showBackground = true,
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"
)
@Composable
fun Preview() {
    AppTheme(darkTheme = false) {
        ReverseTextScreen()
    }
}