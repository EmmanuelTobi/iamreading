package org.exceptos.iamreading.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheets(
    onDismiss: ( isOpen: Boolean) -> Unit,
    content: @Composable () -> Unit,
    title: String?
) {

    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss(sheetState.isVisible)
        },
        sheetState = sheetState,
        dragHandle = null,
        modifier = Modifier.background(Color.Transparent)
    ) {
        // Sheet content
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Row() {

                if (title != null) {
                    TextWidget(text = title)
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    onDismiss(
                    sheetState.isVisible)
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }

            }
            Spacer(modifier = Modifier.height(34.dp))
            content()
        }
    }
}