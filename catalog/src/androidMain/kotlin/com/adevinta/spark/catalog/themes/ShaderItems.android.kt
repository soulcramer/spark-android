package com.adevinta.spark.catalog.themes

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import com.adevinta.spark.catalog.ui.shaders.colorblindness.ColorBlindSetting


public actual fun LazyListScope.ShaderItems(
    theme: Theme,
    onThemeChange: (theme: Theme) -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        item {
            Column {
                ColorBlindSetting(
                    colorBlindNessType = theme.colorBlindNessType,
                    severity = theme.colorBlindNessSeverity,
                    onTypeChange = { onThemeChange(theme.copy(colorBlindNessType = it)) },
                    onSeverityChange = { onThemeChange(theme.copy(colorBlindNessSeverity = it)) },
                )
            }
        }
    }
} 
