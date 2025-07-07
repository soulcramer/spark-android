package com.adevinta.spark.components.placeholder

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.adevinta.spark.PreviewTheme
import com.adevinta.spark.SparkTheme

@PreviewLightDark
@Composable
internal fun PreviewPlaceHolder() {
    PreviewTheme {
        Column(verticalArrangement = spacedBy(4.dp)) {
            Text("Text Placeholder")
            Text("Text Placeholder", modifier = Modifier.textPlaceholder(true))
            Text("Text Placeholder with longer text", modifier = Modifier.textPlaceholder(true))
            Text("Text short", modifier = Modifier.textPlaceholder(true))
        }
        Column(verticalArrangement = spacedBy(4.dp)) {
            Text("Default Placeholder")
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .placeholder(true),
            )
        }
        Column(verticalArrangement = spacedBy(4.dp)) {
            Text("Illustration Placeholder")
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .illustrationPlaceholder(visible = true, shape = SparkTheme.shapes.extraLarge),
            )
        }
    }
} 