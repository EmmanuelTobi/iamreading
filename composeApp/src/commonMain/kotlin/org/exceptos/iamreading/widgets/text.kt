package org.exceptos.iamreading.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.exceptos.iamreading.themes.Pink80

@Composable
fun TextWidget(
    text: String? = null,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    color: Color? = null) {

    Text(
        text = text ?: "Hello",
        style = TextStyle(
            fontSize = fontSize ?: 16.sp,
            fontWeight = fontWeight ?: FontWeight.Normal,
            color = color ?: Pink80,
            letterSpacing = 0.5.sp
        )
    )

}

