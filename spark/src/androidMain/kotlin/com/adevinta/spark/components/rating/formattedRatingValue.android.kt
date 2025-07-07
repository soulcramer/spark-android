package com.adevinta.spark.components.rating

import android.icu.text.NumberFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.Locale

@Composable
public actual fun formattedRatingValue(locale: Locale, value: Float): String = remember(locale, value) {
    val numberFormat = NumberFormat.getInstance(locale)
    numberFormat.maximumFractionDigits = 1
    numberFormat.format(value)
} 
