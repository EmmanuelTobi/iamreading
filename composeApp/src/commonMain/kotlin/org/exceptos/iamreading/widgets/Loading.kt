package org.exceptos.iamreading.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.exceptos.iamreading.themes.DarkGray

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .testTag("LoadingIndicator")
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(100.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = DarkGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextWidget(
                text = "Loading...",
                fontWeight = FontWeight.Bold
            )
        }
    }
}