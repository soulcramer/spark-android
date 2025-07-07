package com.adevinta.spark.catalog.themes

import androidx.compose.foundation.lazy.LazyListScope

public actual fun LazyListScope.ShaderItems(
    theme: Theme,
    onThemeChange: (theme: Theme) -> Unit,
) {
    // No-op for JVM/desktop
} 
