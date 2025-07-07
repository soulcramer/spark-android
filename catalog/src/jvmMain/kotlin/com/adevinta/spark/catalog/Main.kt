/*
 * Copyright (c) 2025 Adevinta
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
package com.adevinta.spark.catalog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.singleWindowApplication
import com.adevinta.spark.catalog.datastore.theme.ThemePropertiesHandler
import com.adevinta.spark.catalog.datastore.theme.collectAsStateWithDefault
import com.adevinta.spark.catalog.datastore.theme.getDataStore
import com.adevinta.spark.catalog.model.Components
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
public fun main(): Unit = singleWindowApplication {
    val coroutineScope = rememberCoroutineScope()
    val dataStore = remember {
        getDataStore()
    }
    val themeProperties = ThemePropertiesHandler(dataStore)
    val theme by themeProperties.properties
        .collectAsStateWithDefault()
    DesktopApp(
        theme = theme,
        onThemeChange = { theme ->
            coroutineScope.launch {
                themeProperties.updateProperties(theme)
            }
        },
        components = Components,
    )
}
