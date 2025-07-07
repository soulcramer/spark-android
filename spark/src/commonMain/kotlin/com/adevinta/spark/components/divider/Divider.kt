/*
 * Copyright (c) 2023 Adevinta
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.adevinta.spark.components.divider

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adevinta.spark.tools.modifiers.sparkUsageOverlay
import androidx.compose.material3.HorizontalDivider as MaterialHorizontalDivider
import androidx.compose.material3.VerticalDivider as MaterialVerticalDivider

/**
 * HorizontalDivider Component.
 * A divider is a thin line that groups content in lists and layouts.
 *
 * @param modifier The modifier to be applied to the divider.
 * @param intent The intent defining the color of the divider.
 */
@Composable
public fun HorizontalDivider(
    modifier: Modifier = Modifier,
    intent: DividerIntent = DividerIntent.Outline,
) {
    MaterialHorizontalDivider(
        color = intent.color(),
        modifier = modifier
            .sparkUsageOverlay()
            .fillMaxWidth(),
    )
}

/**
 * VerticalDivider Component.
 * A divider is a thin line that groups content in lists and layouts.
 *
 * @param modifier The modifier to be applied to the divider.
 * @param intent The intent defining the color of the divider.
 */
@Composable
public fun VerticalDivider(
    modifier: Modifier = Modifier,
    intent: DividerIntent = DividerIntent.Outline,
) {
    MaterialVerticalDivider(
        color = intent.color(),
        modifier = modifier
            .sparkUsageOverlay()
            .fillMaxHeight(),
    )
}
