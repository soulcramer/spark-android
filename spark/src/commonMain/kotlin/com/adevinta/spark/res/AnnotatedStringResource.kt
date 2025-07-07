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
package com.adevinta.spark.res

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import com.adevinta.spark.PreviewTheme
import com.adevinta.spark.Res
import com.adevinta.spark.SparkTheme
import com.adevinta.spark.components.text.Text
import com.adevinta.spark.core.tokens.SparkColors
import com.adevinta.spark.core.tokens.SparkTypography
import com.adevinta.spark.spark_annotatedStringResource_test
import com.adevinta.spark.spark_annotatedStringResource_test_args
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Load an annotated string resource with formatting.
 *
 * ```xml
 * <string name="hello">Hello, <annotation variable="who" color="main">.</annotation>!</string>
 * ```
 *
 * ```kotlin
 * annotatedStringResource(
 *     resource = Res.string.hello,
 *     formatArgs = persistentMapOf("who" to "Bob"),
 * )
 * ```
 *
 * Beware, when using annotations with a `variable` mapping (`<annotation variable="...">`):
 * - The annotation content must not be empty, otherwise it will be stripped by AAPT. You can use a space, a dot, or anything else.
 * - The entire annotation content will be replaced by the provided argument mapping.
 * - If the argument mapping is not found, a [NoSuchElementException] will be thrown!
 *
 * @param resource the string resource identifier
 * @param formatArgs the format arguments
 * @return the [AnnotatedString] data associated with the resource
 */
@Composable
public fun annotatedStringResource(
    resource: StringResource,
    formatArgs: PersistentMap<String, String>,
): AnnotatedString {
    val rawString = stringResource(resource)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource, formatArgs) {
        val processedString = rawString.processVariableAnnotations(formatArgs)
        processedString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Load an annotated string resource with formatting.
 *
 * Be aware that using this method you'll lose the annotations support for variables.
 *
 * @param resource the string resource identifier
 * @param formatArgs the format arguments
 * @return the [AnnotatedString] data associated with the resource
 */
@Deprecated(
    message = "Use the annotatedStringResource with PersistentMap overload instead",
    replaceWith = ReplaceWith("annotatedStringResource(resource, persistentMapOf(*formatArgs.map { it to it.toString() }.toTypedArray()))"),
)
@Composable
public fun annotatedStringResource(resource: StringResource, vararg formatArgs: Any): AnnotatedString {
    val rawString = stringResource(resource, *formatArgs)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource, formatArgs) {
        rawString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Load an annotated string resource.
 *
 * @param resource the string resource identifier
 * @return the [AnnotatedString] data associated with the resource
 */
@Composable
public fun annotatedStringResource(resource: StringResource): AnnotatedString {
    val rawString = stringResource(resource)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource) {
        rawString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Load a styled plurals resource.
 *
 * @param resource the plural string resource identifier
 * @param count the count
 * @return the pluralized string data associated with the resource
 * @see annotatedStringResource for more details
 */
@Composable
public fun annotatedPluralStringResource(
    resource: PluralStringResource,
    count: Int,
): AnnotatedString {
    val rawString = pluralStringResource(resource, count)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource, count) {
        rawString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Load a styled plurals resource with provided format arguments.
 *
 * @param resource the plural string resource identifier
 * @param count the count
 * @param formatArgs arguments used in the format string
 * @return the pluralized string data associated with the resource
 */
@Composable
public fun annotatedPluralStringResource(
    resource: PluralStringResource,
    count: Int,
    formatArgs: PersistentMap<String, String>,
): AnnotatedString {
    val rawString = pluralStringResource(resource, count, count)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource, count, formatArgs) {
        val processedString = rawString.processVariableAnnotations(formatArgs)
        processedString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Load a styled plurals resource with provided format arguments.
 *
 * @param resource the plural string resource identifier
 * @param count the count
 * @param formatArgs arguments used in the format string
 * @return the pluralized string data associated with the resource
 */
@Deprecated(
    message = "Use the annotatedPluralStringResource with PersistentMap overload instead",
    replaceWith = ReplaceWith("annotatedPluralStringResource(resource, count, persistentMapOf(*formatArgs.map { it to it.toString() }.toTypedArray()))"),
)
@Composable
public fun annotatedPluralStringResource(
    resource: PluralStringResource,
    count: Int,
    vararg formatArgs: Any,
): AnnotatedString {
    val rawString = pluralStringResource(resource, count, *formatArgs)
    val colors = SparkTheme.colors
    val typography = SparkTheme.typography
    
    return remember(resource, count, formatArgs) {
        rawString.parseAnnotatedString(colors, typography)
    }
}

/**
 * Process variable annotations in the string by replacing them with the provided arguments.
 */
private fun String.processVariableAnnotations(formatArgs: PersistentMap<String, String>): String {
    var result = this
    
    // Find all variable annotations and replace them
    val variablePattern = Regex("""<annotation[^>]*variable="([^"]*)"[^>]*>([^<]*)</annotation>""")
    val matches = variablePattern.findAll(this).toList().reversed() // Process in reverse to maintain indices
    
    for (match in matches) {
        val variableName = match.groupValues[1]
        val replacement = formatArgs.getValue(variableName)
        result = result.replaceRange(match.range, replacement)
    }
    
    return result
}

/**
 * Parse a string with annotations into an AnnotatedString using HtmlConverterCompose and custom Spark annotations.
 */
private fun String.parseAnnotatedString(
    colors: SparkColors,
    typography: SparkTypography,
): AnnotatedString {
    // First, extract custom Spark annotations and replace them with placeholders
    /*val sparkAnnotations = mutableListOf<SparkAnnotation>()
    var processedString = this
    val annotationPattern = Regex("""<annotation([^>]*)>([^<]*)</annotation>""")
    
    // Find all Spark annotations and replace with placeholders
    val matches = annotationPattern.findAll(this).toList().reversed()
    for ((index, match) in matches.withIndex()) {
        val attributes = match.groupValues[1]
        val content = match.groupValues[2]
        val placeholder = "\uE000${index}\uE001" // Use private use Unicode characters as placeholders
        
        sparkAnnotations.add(SparkAnnotation(placeholder, attributes, content))
        processedString = processedString.replaceRange(match.range, placeholder)
    }*/
    
    // Use HtmlConverterCompose for HTML parsing
    val baseAnnotatedString = htmlToAnnotatedString(this)
    
    // Apply Spark-specific annotations
    return baseAnnotatedString
    /*buildAnnotatedString {
        append(baseAnnotatedString)

        // Find placeholders in the resulting text and apply Spark styles
        sparkAnnotations.forEach { annotation ->
            val placeholderIndex = text.indexOf(annotation.placeholder)
            if (placeholderIndex != -1) {
                val startIndex = placeholderIndex
                val endIndex = placeholderIndex + annotation.placeholder.length

                // Replace placeholder with actual content
                replace(startIndex, endIndex, annotation.content)

                // Apply Spark styling
                val style = parseSparkAnnotationAttributes(annotation.attributes, colors, typography)
                if (style != null) {
                    addStyle(style, startIndex, startIndex + annotation.content.length)
                }
            }
        }
    }*/
}

/**
 * Parse Spark annotation attributes to create a SpanStyle.
 */
private fun parseSparkAnnotationAttributes(
    attributes: String,
    colors: SparkColors,
    typography: SparkTypography,
): SpanStyle? {
    val colorPattern = Regex("""color="([^"]*)"""")
    val typographyPattern = Regex("""typography="([^"]*)"""")
    
    var style = SpanStyle()
    var hasStyle = false
    
    // Parse color
    colorPattern.find(attributes)?.let { match ->
        val colorValue = match.groupValues[1]
        SparkStringAnnotations.parseColorToken(colorValue, colors)?.let { color ->
            style = style.copy(color = color)
            hasStyle = true
        }
    }
    
    // Parse typography
    typographyPattern.find(attributes)?.let { match ->
        val typographyValue = match.groupValues[1]
        SparkStringAnnotations.parseTypographyToken(typographyValue, typography)?.let { textStyle ->
            style = style.merge(textStyle.toSpanStyle())
            hasStyle = true
        }
    }
    
    return if (hasStyle) style else null
}

/**
 * Data class for Spark annotations
 */
private data class SparkAnnotation(
    val placeholder: String,
    val attributes: String,
    val content: String,
)

/**
 * Represents a set of annotations supported by spark that can be used in a string resource.
 */
public object SparkStringAnnotations {

    /**
     * Parse a color token and return the corresponding Color.
     */
    internal fun parseColorToken(colorValue: String, colors: SparkColors): Color? = when (colorValue) {
        "main" -> colors.main
        "support" -> colors.support
        "success" -> colors.success
        "alert" -> colors.alert
        "error" -> colors.error
        "info" -> colors.info
        "neutral" -> colors.neutral
        "accent" -> colors.accent
        else -> null
    }

    /**
     * Parse a typography token and return the corresponding TextStyle.
     */
    internal fun parseTypographyToken(typographyValue: String, typography: SparkTypography) = when (typographyValue) {
        "display1" -> typography.display1
        "display2" -> typography.display2
        "display3" -> typography.display3
        "headline1" -> typography.headline1
        "headline2" -> typography.headline2
        "subhead" -> typography.subhead
        "large" -> typography.body1
        "body1" -> typography.body1
        "body2" -> typography.body2
        "caption" -> typography.caption
        "small" -> typography.small
        "callout" -> typography.callout
                 else -> null
     }
}

@Preview
@Composable
private fun AnnotatedStringResourcePreview() {
    PreviewTheme {
        Column {
            Text(
                text = annotatedStringResource(Res.string.spark_annotatedStringResource_test),
            )
            Text(
                text = annotatedStringResource(
                    Res.string.spark_annotatedStringResource_test_args,
                    persistentMapOf("who" to "Bob"),
                ),
            )
        }
    }
} 
