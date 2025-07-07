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
package com.adevinta.spark.catalog.icons

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.catalog.Res
import com.adevinta.spark.catalog.icons.NamedAsset.Companion.prefix
import com.adevinta.spark.catalog.icons_item_click_a11y
import com.adevinta.spark.catalog.icons_item_long_click_a11y
import com.adevinta.spark.catalog.icons_screen_search_helper
import com.adevinta.spark.catalog.util.splitCamelWithSpaces
import com.adevinta.spark.components.icons.Icon
import com.adevinta.spark.components.text.Text
import com.adevinta.spark.components.textfields.TextField
import com.adevinta.spark.icons.DeleteFill
import com.adevinta.spark.icons.Search
import com.adevinta.spark.icons.SparkIcon
import com.adevinta.spark.icons.SparkIcons
import com.adevinta.spark.icons.allDrawableResources
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import java.util.Locale
import com.adevinta.spark.icons.Res as IconRes


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
public fun IconsScreen(
    contentPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    onIconClick: (iconId: String, name: String, isAnimated: Boolean) -> Unit,
) {
    @Suppress("DEPRECATION") val clipboard = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    var icons: List<NamedAsset> by remember {
        mutableStateOf(emptyList())
    }
    LaunchedEffect(Unit) {
        icons = getAllIconsRes()
    }
    var query: String by rememberSaveable { mutableStateOf("") }
    var showIcons by rememberSaveable { mutableStateOf(true) }
    var showAnimatedIcons by rememberSaveable { mutableStateOf(true) }

    val filteredIcons by remember(query, showIcons, showAnimatedIcons) {
        derivedStateOf {
            if (query.isEmpty()) {
                icons
            } else {
                icons.filter { it.name.contains(query, ignoreCase = true) }
            }.filterNot {
                !showIcons && it is NamedAsset.Icon
            }.filterNot {
                it is NamedAsset.AnimatedIcon
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            placeholder = stringResource(Res.string.icons_screen_search_helper),
            leadingContent = {
                Icon(sparkIcon = SparkIcons.Search, contentDescription = null)
            },
            trailingContent = {
                Icon(
                    modifier = Modifier.clickable { query = "" },
                    sparkIcon = SparkIcons.DeleteFill,
                    contentDescription = "Clear",
                )
            },
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
//            ChipSelectable(
//                selected = showIcons,
//                text = stringResource(Res.string.icons_filter_icon),
//                onClick = { showIcons = !showIcons },
//                style = ChipStyles.Tinted,
//                leadingIcon = if (showIcons) SparkIcons.Check else null,
//            )
//            ChipSelectable(
//                selected = showAnimatedIcons,
//                text = stringResource(Res.string.icons_filter_icon_animated),
//                onClick = { showAnimatedIcons = !showAnimatedIcons },
//                style = ChipStyles.Tinted,
//                leadingIcon = if (showAnimatedIcons) SparkIcons.Check else null,
//            )
        }
        LazyVerticalGrid(
            modifier = modifier
                .consumeWindowInsets(contentPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = contentPadding,
            columns = GridCells.FixedSize(size = 60.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = filteredIcons,
                key = { it.name },
                contentType = { it is NamedAsset.Icon },
            ) { asset ->
                with(sharedTransitionScope) {
                    val drawableRes = asset.drawableRes
                    val iconName = asset.simplifiedName
                    val isAnimated = asset is NamedAsset.AnimatedIcon
                    Column(
                        modifier = Modifier
                            .clip(SparkTheme.shapes.small)
                            .combinedClickable(
                                onLongClick = {
                                    coroutineScope.launch {
                                        clipboard.setText(AnnotatedString(iconName))
                                    }
                                },
                                onLongClickLabel = stringResource(Res.string.icons_item_long_click_a11y),
                                onClick = {
                                    onIconClick(asset.name, iconName, isAnimated)
                                },
                                onClickLabel = stringResource(Res.string.icons_item_click_a11y),
                            )
                            .semantics(mergeDescendants = true) {}
                            .padding(8.dp)
                            .animateItem(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            sparkIcon = SparkIcon.DrawableRes(drawableRes),
                            contentDescription = null,
                            modifier = Modifier
                                .sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "icon-$iconName"),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                                .size(24.dp),
                        )
                        Text(
                            text = iconName.splitCamelWithSpaces(),
                            style = SparkTheme.typography.caption,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Stable
private sealed class NamedAsset(
    open val name: String,
    open val drawableRes: DrawableResource,
) {
    abstract val simplifiedName: String

    companion object {
        const val prefix = "spark_icons_"
    }

    data class Icon(
        override val name: String,
        override val drawableRes: DrawableResource,
    ) : NamedAsset(name, drawableRes) {
        override val simplifiedName: String = name.removePrefix(prefix).toPascalCase()
    }

    data class AnimatedIcon(
        override val name: String,
        override val drawableRes: DrawableResource,
    ) : NamedAsset(name, drawableRes) {
        override val simplifiedName: String = name.removePrefix(prefix).removeSuffix("_animated").toPascalCase()
    }
}

@OptIn(ExperimentalResourceApi::class)
private suspend fun getAllIconsRes() = withContext(Default) {
    IconRes.allDrawableResources.mapNotNull { (name, icon) ->
        if (!name.startsWith(prefix)) return@mapNotNull null
        when {
//            name.contains("animated") -> {
//                NamedAsset.AnimatedIcon(name, icon)
//            }

            else -> NamedAsset.Icon(name, icon)
        }
    }
}

private fun String.toPascalCase(): String = split("_").joinToString(separator = "") { str ->
    str.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(Locale.ROOT)
        } else {
            it.toString()
        }
    }
}
