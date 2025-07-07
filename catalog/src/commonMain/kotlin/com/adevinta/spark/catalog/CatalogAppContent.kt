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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adevinta.spark.catalog.configurator.ConfiguratorComponentsScreen
import com.adevinta.spark.catalog.examples.ComponentsScreen
import com.adevinta.spark.catalog.icons.IconDemoScreen
import com.adevinta.spark.catalog.model.Component
import com.adevinta.spark.catalog.tabbar.CatalogTabBar
import com.adevinta.spark.catalog.tabbar.CatalogTabs
import com.adevinta.spark.catalog.themes.NavigationMode
import com.adevinta.spark.catalog.themes.Theme
import com.adevinta.spark.catalog.themes.ThemePicker
import com.adevinta.spark.catalog.ui.BackdropScaffold
import com.adevinta.spark.catalog.ui.BackdropScaffoldDefaults
import com.adevinta.spark.catalog.ui.BackdropValue
import com.adevinta.spark.catalog.ui.rememberBackdropScaffoldState
import kotlinx.coroutines.launch

@Composable
public fun CatalogAppContent(
    components: List<Component>,
    theme: Theme,
    pagerState: PagerState,
    innerPadding: PaddingValues,
    onThemeChange: (Theme) -> Unit,
    navigationMode: NavigationMode,
    homeScreenValues: List<CatalogHomeScreen>,
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BackdropScaffold(
            scaffoldState = rememberBackdropScaffoldState(
                BackdropValue.Concealed,
            ),
            frontLayerScrimColor = Color.Unspecified,
            headerHeight = BackdropScaffoldDefaults.HeaderHeight,
            peekHeight = BackdropScaffoldDefaults.PeekHeight,
            backLayerBackgroundColor = MaterialTheme.colorScheme.background,
            appBar = {
                CatalogTabBar(
                    modifier = Modifier
                        .wrapContentWidth()
                        .sizeIn(maxWidth = 500.dp),
                ) { tabBarModifier ->
                    CatalogTabs(
                        modifier = tabBarModifier,
                        titles = CatalogHomeScreen.entries.map { it.name },
                        tabSelected = homeScreenValues[pagerState.currentPage],
                        onTabSelected = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    it.ordinal,
                                    animationSpec = tween(
                                        durationMillis = 250,
                                        easing = FastOutSlowInEasing,
                                    ),
                                )
                            }
                        },
                    )
                }
            },
            backLayerContent = {
                ThemePicker(
                    theme = theme,
                    onThemeChange = { theme ->
                        coroutineScope.launch {
                            onThemeChange(theme)
                        }
                    },
                )
            },
            frontLayerContent = {
                HorizontalPager(
                    state = pagerState,
                    flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                    beyondViewportPageCount = 1,
                    key = {
                        when (it) {
                            0 -> CatalogHomeScreen.Examples
                            1 -> CatalogHomeScreen.Configurator
                            2 -> CatalogHomeScreen.Icons
                            else -> CatalogHomeScreen.Examples
                        }.ordinal
                    },
                ) {
                    when (homeScreenValues[it]) {
                        CatalogHomeScreen.Examples -> ComponentsScreen(
                            components = components,
                            pagerState = pagerState,
                            contentPadding = innerPadding,
                            navigationMode = navigationMode,
                        )
                        CatalogHomeScreen.Configurator -> ConfiguratorComponentsScreen(
                            components = components,
                            pagerState = pagerState,
                            contentPadding = innerPadding,
                            navigationMode = navigationMode,
                        )
                        CatalogHomeScreen.Icons -> IconDemoScreen(
                            contentPadding = innerPadding,
                            pagerState = pagerState,
                            navigationMode = navigationMode,
                        )
                    }
                }
            },
        )
    }
}
