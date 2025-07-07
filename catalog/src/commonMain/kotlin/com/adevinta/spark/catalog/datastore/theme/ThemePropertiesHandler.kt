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
package com.adevinta.spark.catalog.datastore.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.adevinta.spark.catalog.themes.BrandMode
import com.adevinta.spark.catalog.themes.ColorMode
import com.adevinta.spark.catalog.themes.FontScaleMode
import com.adevinta.spark.catalog.themes.NavigationMode
import com.adevinta.spark.catalog.themes.TextDirection
import com.adevinta.spark.catalog.themes.Theme
import com.adevinta.spark.catalog.themes.ThemeMode
import com.adevinta.spark.catalog.themes.UserMode
import com.adevinta.spark.catalog.ui.shaders.ColorBlindNessType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.FileSystem
import okio.Path
import java.io.IOException
import com.adevinta.spark.catalog.datastore.theme.BrandMode as DatastoreBrandMode
import com.adevinta.spark.catalog.datastore.theme.ColorBlindNessType as DatastoreColorBlindNessType
import com.adevinta.spark.catalog.datastore.theme.ColorMode as DatastoreColorMode
import com.adevinta.spark.catalog.datastore.theme.FontScaleMode as DatastoreFontScaleMode
import com.adevinta.spark.catalog.datastore.theme.NavigationMode as DatastoreNavigationMode
import com.adevinta.spark.catalog.datastore.theme.TextDirection as DatastoreTextDirection
import com.adevinta.spark.catalog.datastore.theme.ThemeMode as DatastoreThemeMode
import com.adevinta.spark.catalog.datastore.theme.UserMode as DatastoreUserMode

/**
 * Handles theme properties persistence using DataStore with Protocol Buffers.
 * This implementation works on both Android and JVM platforms.
 */
internal class ThemePropertiesHandler(private val dataStore: DataStore<ThemeProperties>) {

    internal val properties: Flow<Theme> = dataStore.data
        .map { themeProperties -> themeProperties.toTheme() }
        .catch { exception ->
            when (exception) {
                is IOException -> emit(getDefaultTheme())
                else -> throw exception
            }
        }

    internal suspend fun updateProperties(theme: Theme) {
        dataStore.updateData { theme.toThemeProperties() }
    }

    companion object {
        internal const val DATA_STORE_FILE_NAME = "theme_properties.pb"

        /**
         * Creates a DataStore instance for the given file path.
         * This works for both Android and JVM platforms.
         */
        fun createDataStore(fileSystem: FileSystem, producePath: () -> Path): DataStore<ThemeProperties> =
            DataStoreFactory.create(
                storage = OkioStorage(
                    fileSystem = fileSystem,
                    producePath = producePath,
                    serializer = ThemePropertiesSerializer,
                ),
            )

        private fun getDefaultTheme(): Theme = Theme()
    }
}

@Composable
internal fun Flow<Theme>.collectAsStateWithDefault(): State<Theme> = collectAsState(
    initial = Theme(),
)

/**
 * Converts protobuf ThemeProperties to Domain Theme
 */
private fun ThemeProperties.toTheme(): Theme = Theme(
    fontScale = font_scale,
    userMode = when (user_mode) {
        DatastoreUserMode.USER_MODE_PART -> UserMode.Part
        DatastoreUserMode.USER_MODE_PRO -> UserMode.Pro
    },
    themeMode = when (theme_mode) {
        DatastoreThemeMode.THEME_MODE_SYSTEM -> ThemeMode.System
        DatastoreThemeMode.THEME_MODE_LIGHT -> ThemeMode.Light
        DatastoreThemeMode.THEME_MODE_DARK -> ThemeMode.Dark
    },
    colorMode = when (color_mode) {
        DatastoreColorMode.COLOR_MODE_BASELINE -> ColorMode.Baseline
        DatastoreColorMode.COLOR_MODE_BRAND -> ColorMode.Brand
        DatastoreColorMode.COLOR_MODE_DYNAMIC -> ColorMode.Dynamic
    },
    brandMode = when (brand_mode) {
        DatastoreBrandMode.BRAND_MODE_LEBONCOIN -> BrandMode.Leboncoin
        DatastoreBrandMode.BRAND_MODE_KLEINANZEIGEN -> BrandMode.Kleinanzeigen
        DatastoreBrandMode.BRAND_MODE_MILANUNCIOS -> BrandMode.Milanuncios
        DatastoreBrandMode.BRAND_MODE_SUBITO -> BrandMode.Subito
    },
    fontScaleMode = when (font_scale_mode) {
        DatastoreFontScaleMode.FONT_SCALE_MODE_SYSTEM -> FontScaleMode.System
        DatastoreFontScaleMode.FONT_SCALE_MODE_CUSTOM -> FontScaleMode.Custom
    },
    colorBlindNessType = when (color_blind_ness_type) {
        DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_NONE -> ColorBlindNessType.None
        DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_DEUTERANOMALY -> ColorBlindNessType.Deuteranomaly
        DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_PROTANOMALY -> ColorBlindNessType.Protanomaly
        DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_TRITANOMALY -> ColorBlindNessType.Tritanomaly
    },
    colorBlindNessSeverity = color_blind_ness_severity,
    navigationMode = when (navigation_mode) {
        DatastoreNavigationMode.NAVIGATION_MODE_DEFAULT -> NavigationMode.Default
        DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_X -> NavigationMode.SharedAxisX
        DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_Y -> NavigationMode.SharedAxisY
        DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_Z -> NavigationMode.SharedAxisZ
        DatastoreNavigationMode.NAVIGATION_MODE_FADE_THROUGH -> NavigationMode.FadeThrough
    },
    textDirection = when (text_direction) {
        DatastoreTextDirection.TEXT_DIRECTION_SYSTEM -> TextDirection.System
        DatastoreTextDirection.TEXT_DIRECTION_LTR -> TextDirection.LTR
        DatastoreTextDirection.TEXT_DIRECTION_RTL -> TextDirection.RTL
    },
    highlightSparkComponents = highlight_spark_components,
    highlightSparkTokens = highlight_spark_tokens,
)

/**
 * Converts Domain Theme to protobuf ThemeProperties
 */
private fun Theme.toThemeProperties(): ThemeProperties = ThemeProperties(
    font_scale = fontScale,
    user_mode = when (userMode) {
        UserMode.Part -> DatastoreUserMode.USER_MODE_PART
        UserMode.Pro -> DatastoreUserMode.USER_MODE_PRO
    },
    theme_mode = when (themeMode) {
        ThemeMode.System -> DatastoreThemeMode.THEME_MODE_SYSTEM
        ThemeMode.Light -> DatastoreThemeMode.THEME_MODE_LIGHT
        ThemeMode.Dark -> DatastoreThemeMode.THEME_MODE_DARK
    },
    color_mode = when (colorMode) {
        ColorMode.Baseline -> DatastoreColorMode.COLOR_MODE_BASELINE
        ColorMode.Brand -> DatastoreColorMode.COLOR_MODE_BRAND
        ColorMode.Dynamic -> DatastoreColorMode.COLOR_MODE_DYNAMIC
    },
    brand_mode = when (brandMode) {
        BrandMode.Leboncoin -> DatastoreBrandMode.BRAND_MODE_LEBONCOIN
        BrandMode.Kleinanzeigen -> DatastoreBrandMode.BRAND_MODE_KLEINANZEIGEN
        BrandMode.Milanuncios -> DatastoreBrandMode.BRAND_MODE_MILANUNCIOS
        BrandMode.Subito -> DatastoreBrandMode.BRAND_MODE_SUBITO
    },
    font_scale_mode = when (fontScaleMode) {
        FontScaleMode.System -> DatastoreFontScaleMode.FONT_SCALE_MODE_SYSTEM
        FontScaleMode.Custom -> DatastoreFontScaleMode.FONT_SCALE_MODE_CUSTOM
    },
    color_blind_ness_type = when (colorBlindNessType) {
        ColorBlindNessType.None -> DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_NONE
        ColorBlindNessType.Deuteranomaly -> DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_DEUTERANOMALY
        ColorBlindNessType.Protanomaly -> DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_PROTANOMALY
        ColorBlindNessType.Tritanomaly -> DatastoreColorBlindNessType.COLOR_BLINDNESS_TYPE_TRITANOMALY
    },
    color_blind_ness_severity = colorBlindNessSeverity,
    navigation_mode = when (navigationMode) {
        NavigationMode.Default -> DatastoreNavigationMode.NAVIGATION_MODE_DEFAULT
        NavigationMode.SharedAxisX -> DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_X
        NavigationMode.SharedAxisY -> DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_Y
        NavigationMode.SharedAxisZ -> DatastoreNavigationMode.NAVIGATION_MODE_SHARED_AXIS_Z
        NavigationMode.FadeThrough -> DatastoreNavigationMode.NAVIGATION_MODE_FADE_THROUGH
    },
    text_direction = when (textDirection) {
        TextDirection.System -> DatastoreTextDirection.TEXT_DIRECTION_SYSTEM
        TextDirection.LTR -> DatastoreTextDirection.TEXT_DIRECTION_LTR
        TextDirection.RTL -> DatastoreTextDirection.TEXT_DIRECTION_RTL
    },
    highlight_spark_components = highlightSparkComponents,
    highlight_spark_tokens = highlightSparkTokens,
)
