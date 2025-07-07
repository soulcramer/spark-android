package com.adevinta.spark.components.rating

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

@Composable
public actual fun firstLocale(): Locale {
    return LocalConfiguration.current.locales[0]
} 
