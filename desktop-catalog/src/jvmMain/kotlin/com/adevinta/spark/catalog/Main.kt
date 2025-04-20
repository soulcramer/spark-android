package com.adevinta.spark.catalog

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.adevinta.spark.icons.allDrawableResources
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import java.util.Locale
import com.adevinta.spark.icons.Res as IconRes

public fun main(): Unit = application {

    Window(
        title = "Spark",
        onCloseRequest = ::exitApplication,
    ) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column {
                    Text(
                        text = "Catalog App",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineLarge,
                    )
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
                                !showAnimatedIcons && it is NamedAsset.AnimatedIcon
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
//                        TextField(
//                            value = query,
//                            onValueChange = { query = it },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp)
//                                .padding(top = 16.dp),
//                            placeholder = stringResource(id = R.string.icons_screen_search_helper),
//                            leadingContent = {
//                                Icon(sparkIcon = SparkIcons.Search, contentDescription = null)
//                            },
//                            trailingContent = {
//                                Icon(
//                                    modifier = Modifier.clickable { query = "" },
//                                    sparkIcon = SparkIcons.DeleteFill,
//                                    contentDescription = "Clear",
//                                )
//                            },
//                        )
//                        FlowRow(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .horizontalScroll(rememberScrollState())
//                                .padding(horizontal = 16.dp),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        ) {
//                            ChipSelectable(
//                                selected = showIcons,
//                                text = stringResource(R.string.icons_filter_icon),
//                                onClick = { showIcons = !showIcons },
//                                style = ChipStyles.Tinted,
//                                leadingIcon = if (showIcons) SparkIcons.Check else null,
//                            )
//                            ChipSelectable(
//                                selected = showAnimatedIcons,
//                                text = stringResource(R.string.icons_filter_icon_animated),
//                                onClick = { showAnimatedIcons = !showAnimatedIcons },
//                                style = ChipStyles.Tinted,
//                                leadingIcon = if (showAnimatedIcons) SparkIcons.Check else null,
//                            )
//                        }
                        LazyVerticalGrid(
                            modifier = Modifier
//                                .consumeWindowInsets(contentPadding)
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
//                            contentPadding = contentPadding,
                            columns = GridCells.Adaptive(minSize = 60.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = filteredIcons,
                                key = { it.name },
                                contentType = { it is NamedAsset.Icon },
                            ) { asset ->
//                                with(sharedTransitionScope) {
                                val drawableRes = asset.drawableRes
                                val iconName = asset.name
                                val isAnimated = asset is NamedAsset.AnimatedIcon
                                Column(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
//                                            .combinedClickable(
//                                                onLongClick = { copyToClipboard(context, iconName) },
//                                                onLongClickLabel = stringResource(R.string.icons_item_long_click_a11y),
//                                                onClick = {
//                                                    onIconClick(drawableRes, iconName, isAnimated)
//                                                },
//                                                onClickLabel = stringResource(R.string.icons_item_click_a11y),
//                                            )
                                        .semantics(mergeDescendants = true) {}
                                        .padding(8.dp)
                                        .animateItem(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Icon(
                                        painter = painterResource(drawableRes),
                                        contentDescription = null,
                                        modifier = Modifier
//                                            .sharedElement(
//                                                state = sharedTransitionScope.rememberSharedContentState(key = "icon-$iconName"),
//                                                animatedVisibilityScope = animatedContentScope,
//                                            )
                                            .size(40.dp),
                                    )
                                    Text(
                                        text = iconName.splitCamelWithSpaces(),
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Stable
private sealed class NamedAsset(open val name: String, open val drawableRes: DrawableResource) {
    data class Icon(override val name: String, override val drawableRes: DrawableResource) :
        NamedAsset(name, drawableRes)

    data class AnimatedIcon(override val name: String, override val drawableRes: DrawableResource) :
        NamedAsset(name, drawableRes)
}

public fun String.splitCamelWithSpaces(): String = CamelCaseRegex.replace(this, " $0").lowercase()

private val CamelCaseRegex = "(?<=.)[A-Z]".toRegex()

@OptIn(ExperimentalResourceApi::class)
private suspend fun getAllIconsRes() = withContext(Default) {
    IconRes.allDrawableResources.mapNotNull { (name, icon) ->
        val prefix = "spark_icons_"
        if (!name.startsWith(prefix)) return@mapNotNull null
        when {
            name.contains("animated") -> {
                val animatedName = name.removePrefix(prefix).removeSuffix("_animated").toPascalCase()
                NamedAsset.AnimatedIcon(animatedName, icon)
            }

            else -> NamedAsset.Icon(name.removePrefix(prefix).toPascalCase(), icon)
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
