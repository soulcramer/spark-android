/*
 * Copyright (c) 2023-2025 Adevinta
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
package com.adevinta.spark.catalog.configurator.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.adevinta.spark.catalog.Res
import com.adevinta.spark.catalog.component_menu_dev_docs
import com.adevinta.spark.catalog.component_menu_guidlines
import com.adevinta.spark.catalog.component_menu_issue
import com.adevinta.spark.catalog.component_menu_source
import com.adevinta.spark.catalog.model.Component
import com.adevinta.spark.catalog.model.Components
import com.adevinta.spark.catalog.model.Configurator
import com.adevinta.spark.catalog.util.IssueUrl
import com.adevinta.spark.components.divider.HorizontalDivider
import com.adevinta.spark.components.iconbuttons.IconButtonGhost
import com.adevinta.spark.components.icons.Icon
import com.adevinta.spark.components.menu.DropdownMenu
import com.adevinta.spark.components.menu.DropdownMenuItem
import com.adevinta.spark.components.menu.DropdownMenuItemColumnScope
import com.adevinta.spark.components.scaffold.Scaffold
import com.adevinta.spark.components.snackbars.SnackbarHost
import com.adevinta.spark.components.snackbars.SnackbarHostState
import com.adevinta.spark.components.text.Text
import com.adevinta.spark.core.tokens.Layout
import com.adevinta.spark.icons.Color
import com.adevinta.spark.icons.Computer
import com.adevinta.spark.icons.FeedbackOutline
import com.adevinta.spark.icons.Link
import com.adevinta.spark.icons.SparkIcons
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
public fun ConfiguratorComponentScreen(
    component: Component,
    configurator: Configurator,
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = Layout.bodyMargin)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier.align(Alignment.End),
            ) {
                var expanded by remember { mutableStateOf(false) }
                IconButtonGhost(
                    icon = SparkIcons.Link,
                    onClick = { expanded = true },
                    contentDescription = "Localized description",
                )

                ConfiguratorComponentMenu(
                    component = component,
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                )
            }

            with(configurator) {
                this@Column.content(snackbarHostState)
            }
        }
    }
}

@Composable
private fun ConfiguratorComponentMenu(
    component: Component,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        LinkItem(
            label = Res.string.component_menu_guidlines,
            uri = component.guidelinesUrl,
            icon = SparkIcons.Color,
        )
        LinkItem(
            label = Res.string.component_menu_dev_docs,
            uri = component.docsUrl,
            icon = SparkIcons.Computer,
        )
        LinkItem(
            label = Res.string.component_menu_source,
            uri = component.sourceUrl,
            icon = SparkIcons.Computer,
        )
        HorizontalDivider()
        LinkItem(
            label = Res.string.component_menu_issue,
            uri = IssueUrl,
            icon = SparkIcons.FeedbackOutline,
        )
    }
}

@Composable
private fun DropdownMenuItemColumnScope.LinkItem(
    label: StringResource,
    uri: String,
    icon: com.adevinta.spark.icons.SparkIcon,
) {
    val uriHandler = LocalUriHandler.current
    DropdownMenuItem(
        text = { Text(stringResource(label)) },
        onClick = { uriHandler.openUri(uri) },
        leadingIcon = {
            Icon(
                sparkIcon = icon,
                contentDescription = null,
            )
        },
    )
}

@Preview
@Composable
private fun ConfiguratorComponentMenuPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        ConfiguratorComponentMenu(
            component = Components.first(),
            expanded = true,
        ) {}
    }
}
