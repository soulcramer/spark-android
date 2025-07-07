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
package com.adevinta.spark.catalog.configurator.samples.image

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import coil3.annotation.ExperimentalCoilApi
import com.adevinta.spark.catalog.model.Configurator
import com.adevinta.spark.catalog.ui.WipIllustration
import com.adevinta.spark.catalog.util.SampleSourceUrl
import com.adevinta.spark.components.text.Text

public val ImageConfigurator: Configurator = Configurator(
    id = "image",
    name = "Image",
    description = "Image configuration",
    sourceUrl = "$SampleSourceUrl/ImageSample.kt",
) {
    ImageSample()
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalCoilApi::class)
@Composable
private fun ColumnScope.ImageSample() {
    WipIllustration()
    Text(text = "We're waiting for a fix in Compose KMP to enable Coil to work with DrawableResource")
    /*var state by remember { mutableStateOf(ImageState.Success) }
    var width by remember { mutableStateOf<Int?>(1) }
    var height by remember { mutableStateOf<Int?>(1) }
    var showBorder by remember { mutableStateOf(true) }
    var blurEdges by remember { mutableStateOf(true) }
    var contentScale by remember { mutableStateOf(ImageContentScale.Crop) }
    var aspectRatio by remember { mutableStateOf(ImageAspectRatio.Custom) }
    var imageShape by remember { mutableStateOf(ImageShape.Medium) }
    var selectedImage by remember { mutableStateOf(SelectedImage.Narrow) }
    val painter = painterResource(selectedImage.res)
//    val bitmap = imageResource(selectedImage.res).
    val imageRequest = ImageRequest.Builder(LocalPlatformContext.current)
        .crossfade(true)
        .data(state.ordinal)
        .build()

    val imageMaxWidth = when {
        LocalWindowSizeClass.current.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> 350.dp
        LocalWindowSizeClass.current.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> 250.dp
        else -> 200.dp
    }
    val previewHandler = AsyncImagePreviewHandler { _, request ->
        when (state) {
            ImageState.Error -> AsyncImagePainter.State.Error(
                painter = painter,
                result = ErrorResult(null, request, Throwable("")),
            )

            ImageState.Empty -> AsyncImagePainter.State.Empty
            ImageState.Loading -> AsyncImagePainter.State.Loading(painter)
            ImageState.Success -> AsyncImagePainter.State.Success(
                painter = painter,
                result = SuccessResult(null, request, DataSource.DISK),
            )
        }
    }
    CompositionLocalProvider(
        LocalAsyncImagePreviewHandler provides previewHandler,
        LocalAsyncImageModelEqualityDelegate provides AsyncImageModelEqualityDelegate.AllProperties,
        LocalInspectionMode provides true,
    ) {
        SparkImage(
            model = imageRequest,
            contentDescription = stringResource(selectedImage.contentDescription),
            modifier = Modifier
                .width(imageMaxWidth)
                .ifTrue(showBorder) {
                    border(1.dp, SparkTheme.colors.outlineHigh)
                }
                .aspectRatio(
                    ratio = if (aspectRatio == ImageAspectRatio.Custom) {
                        (width?.toFloat() ?: 1f) / (height?.toFloat() ?: 1f)
                    } else {
                        aspectRatio.ratio
                    },
                    matchHeightConstraintsFirst = true,
                )
                .align(Alignment.CenterHorizontally)
                .clip(imageShape.shape)
                .animateContentSize(),
            contentScale = contentScale.scale,
            blurEdges = blurEdges,
        )
    }

    ButtonGroup(
        title = "Image type",
        selectedOption = selectedImage,
        onOptionSelect = { selectedImage = it },
    )

    ButtonGroup(
        title = "States",
        selectedOption = state,
        onOptionSelect = { state = it },
    )

    SwitchLabelled(
        checked = showBorder,
        onCheckedChange = { showBorder = it },
    ) {
        Text(text = "Show border", modifier = Modifier.fillMaxWidth())
    }

    SwitchLabelled(
        checked = blurEdges,
        onCheckedChange = { blurEdges = it },
    ) {
        Text(text = "Blur Edges", modifier = Modifier.fillMaxWidth())
    }

    DropdownEnum(
        modifier = Modifier.fillMaxWidth(),
        title = "Shapes",
        selectedOption = imageShape,
        onOptionSelect = { imageShape = it },
    )

    var expandedScale by remember { mutableStateOf(false) }
    SingleChoiceDropdown(
        modifier = Modifier.fillMaxWidth(),
        value = contentScale.name,
        label = "Scale",
        expanded = expandedScale,
        onExpandedChange = {
            expandedScale = !expandedScale
        },
        onDismissRequest = {
            expandedScale = false
        },
        dropdownContent = {
            ImageContentScale.entries.forEach {
                DropdownMenuItem(
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(it.name)
                            Text(
                                text = annotatedStringResource(it.descriptionRes),
                                style = SparkTheme.typography.caption,
                            )
                        }
                    },
                    onClick = {
                        contentScale = it
                        expandedScale = false
                    },
                    selected = contentScale == it,
                )
            }
        },
    )

    ButtonGroup(
        title = "Aspect Ratio",
        selectedOption = aspectRatio.label,
        options = ImageAspectRatio.entries.map(ImageAspectRatio::label),
        onOptionSelect = { option ->
            aspectRatio = ImageAspectRatio.entries.firstOrNull {
                option == it.label
            } ?: aspectRatio
        },
    )

    AnimatedVisibility(aspectRatio == ImageAspectRatio.Custom) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                value = width?.toString().orEmpty(),
                label = "Width",
                onValueChange = {
                    width = it.toIntOrNull()
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            TextField(
                value = height?.toString().orEmpty(),
                label = "Height",
                onValueChange = {
                    height = it.toIntOrNull()
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
    }*/
}
/*
private enum class ImageState {
    Loading {
        override fun transformation(
            painter: Painter,
            drawable: Drawable,
            imageRequest: ImageRequest,
        ): AsyncImagePainter.State = AsyncImagePainter.State.Loading(painter)
    },
    Empty {
        override fun transformation(
            painter: Painter,
            drawable: Drawable,
            imageRequest: ImageRequest,
        ): AsyncImagePainter.State = AsyncImagePainter.State.Empty
    },
    Error {
        override fun transformation(
            painter: Painter,
            drawable: Drawable,
            imageRequest: ImageRequest,
        ): AsyncImagePainter.State = AsyncImagePainter.State.Error(
            painter,
            ErrorResult(null, imageRequest, Throwable("")),
        )
    },
    Success {
        override fun transformation(
            painter: Painter,
            drawable: Drawable,
            imageRequest: ImageRequest,
        ): AsyncImagePainter.State = AsyncImagePainter.State.Success(
            painter,
            SuccessResult(drawable.asImage(), imageRequest, DataSource.DISK),
        )
    },
    ;

    abstract fun transformation(
        painter: Painter,
        drawable: Drawable,
        imageRequest: ImageRequest,
    ): AsyncImagePainter.State
}

private enum class ImageShape {
    None {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.none
    },
    Small {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.small
    },
    Medium {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.medium
    },
    Large {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.large
    },
    ExtraLarge {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.extraLarge
    },
    Full {
        override val shape: Shape
            @Composable
            get() = SparkTheme.shapes.full
    },
    Hexagon {
        override val shape: Shape
            @Composable
            get() = RoundedPolygonShape(
                polygon = RoundedPolygon(
                    numVertices = 6,
                    rounding = CornerRounding(0.2f),
                ),
            )
    },
    Wavy {
        override val shape: Shape
            @Composable
            get() = RoundedPolygonShape(
                polygon = RoundedPolygon.star(
                    numVerticesPerRadius = 9,
                    innerRadius = 0.65f,
                    rounding = CornerRounding(2f),
                ),
            )
    },
    ;

    @get:Composable
    abstract val shape: Shape
}

private enum class SelectedImage(val res: DrawableResource, val contentDescription: StringResource) {
    Wide(
        Res.drawable.img_wide_image_configurator,
        Res.string.component_image_wide_description,
    ),
    Narrow(
        Res.drawable.img_narrow_image_configurator,
        Res.string.component_image_narrow_description,
    ),
}

private enum class ImageContentScale(val scale: ContentScale, val descriptionRes: StringResource) {
    Crop(scale = ContentScale.Crop, descriptionRes = Res.string.component_image_content_scale_crop_description),
    Fit(scale = ContentScale.Fit, descriptionRes = Res.string.component_image_content_scale_fit_description),
    FillHeight(
        scale = ContentScale.FillHeight,
        descriptionRes = Res.string.component_image_content_scale_fill_height_description,
    ),
    FillWidth(
        scale = ContentScale.FillWidth,
        descriptionRes = Res.string.component_image_content_scale_fill_width_description,
    ),
    Inside(scale = ContentScale.Inside, descriptionRes = Res.string.component_image_content_scale_inside_description),
    None(scale = ContentScale.None, descriptionRes = Res.string.component_image_content_scale_none_description),
    FillBounds(
        scale = ContentScale.FillBounds,
        descriptionRes = Res.string.component_image_content_scale_fill_bounds_description,
    ),
}

private enum class ImageAspectRatio(val label: String, val ratio: Float) {
    Custom(
        label = "Custom",
        ratio = 1f,
    ),
    Square(
        label = "1:1",
        ratio = 1f,
    ),
    OldTV(
        label = "4:3",
        ratio = 4 / 3f,
    ),
    Screen(
        label = "16:9",
        ratio = 16 / 9f,
    ),
}

@Preview
@Composable
private fun ImageSamplePreview() {
    PreviewTheme { ImageSample() }
}*/
