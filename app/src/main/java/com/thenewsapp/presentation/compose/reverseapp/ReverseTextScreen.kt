package com.thenewsapp.presentation.compose.reverseapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenewsapp.R
import com.thenewsapp.presentation.compose.theme.AppTheme

@Composable
fun ReverseTextScreen(viewModel: ReverseTextViewModel = ReverseTextViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        var inputText by remember { mutableStateOf("") }

        val textState = viewModel.textValue.observeAsState()

        ReverseText(textState.value)

        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text(text = stringResource(id = R.string.enter_text)) },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { viewModel.onTextValueChange(inputText) },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()) {
            Text(text = stringResource(id = R.string.reverse_text))
        }
    }
}

@Composable
fun ReverseText(text: String?) {
    if (text != null) {
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
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