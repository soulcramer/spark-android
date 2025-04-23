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

package com.adevinta.spark.icons.internal

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
//import com.adevinta.spark.icons.internal.AddToFillPath.addPath

//object AddToFillPath {
//    val addPath =
//        PathParser().parsePathString("M 7.16 2 C 5.792 2 4.479 2.544 3.511 3.511 C 2.544 4.479 2 5.792 2 7.16 L 2 16.84 C 2 18.208 2.544 19.521 3.511 20.489 C 4.479 21.456 5.792 22 7.16 22 L 16.84 22 C 18.208 22 19.521 21.456 20.489 20.489 C 21.456 19.521 22 18.208 22 16.84 L 22 7.16 C 22 5.792 21.456 4.479 20.489 3.511 C 19.521 2.544 18.208 2 16.84 2 Z M 4.02 7.16 C 4.02 5.43 5.42 4.02 7.16 4.02 L 16.84 4.02 C 18.57 4.02 19.98 5.42 19.98 7.16 L 19.98 16.84 C 19.98 18.57 18.58 19.98 16.84 19.98 L 7.16 19.98 C 6.328 19.98 5.528 19.649 4.94 19.06 C 4.351 18.472 4.02 17.672 4.02 16.84 Z")
//            .toNodes()
//    val fillPath = PathParser().parsePathString(
//        "M 7 2 C 5.674 2 4.402 2.527 3.464 3.464 C 2.527 4.402 2 5.674 2 7 L 2 17 C 2 17.878 2.231 18.74 2.67 19.5 C 3.109 20.26 3.74 20.891 4.5 21.33 C 5.26 21.769 6.122 22 7 22 L 17 22 C 17.878 22 18.74 21.769 19.5 21.33 C 20.26 20.891 20.891 20.26 21.33 19.5 C 21.769 18.74 22 17.878 22 17 L 22 7 C 22 6.122 21.769 5.26 21.33 4.5 C 20.891 3.74 20.26 3.109 19.5 2.67 C 18.74 2.231 17.878 2 17 2 Z M 7.133 11.403 C 7.289 11.248 7.5 11.16 7.72 11.16 C 8.87 11.16 10.02 11.16 11.17 11.16 C 11.17 10.01 11.17 8.86 11.17 7.71 C 11.17 7.49 11.258 7.279 11.413 7.123 C 11.569 6.968 11.78 6.88 12 6.88 C 12.22 6.88 12.431 6.968 12.587 7.123 C 12.742 7.279 12.83 7.49 12.83 7.71 C 12.83 8.86 12.83 10.01 12.83 11.16 C 13.98 11.16 15.13 11.16 16.28 11.16 C 16.5 11.16 16.711 11.248 16.867 11.403 C 17.022 11.559 17.11 11.77 17.11 11.99 C 17.11 12.21 17.022 12.421 16.867 12.577 C 16.711 12.732 16.5 12.82 16.28 12.82 C 15.13 12.82 13.98 12.82 12.83 12.82 C 12.83 13.97 12.83 15.12 12.83 16.27 C 12.83 16.49 12.742 16.701 12.587 16.857 C 12.431 17.012 12.22 17.1 12 17.1 C 11.78 17.1 11.569 17.012 11.413 16.857 C 11.258 16.701 11.17 16.49 11.17 16.27 C 11.17 15.12 11.17 13.97 11.17 12.82 C 10.02 12.82 8.87 12.82 7.72 12.82 C 7.5 12.82 7.289 12.732 7.133 12.577 C 6.978 12.421 6.89 12.21 6.89 11.99 C 6.89 11.77 6.978 11.559 7.133 11.403",
//    ).toNodes()
//    val plusPath = PathParser().parsePathString("M 12 6.84 C 12.178 6.838 12.353 6.884 12.507 6.972 C 12.661 7.061 12.789 7.189 12.878 7.343 C 12.966 7.497 13.012 7.672 13.01 7.85 L 13.01 10.99 L 16.15 10.99 C 16.327 10.997 16.499 11.05 16.648 11.145 C 16.797 11.24 16.919 11.373 17 11.53 C 17.081 11.687 17.119 11.863 17.11 12.04 C 17.1 12.291 16.996 12.53 16.818 12.708 C 16.64 12.886 16.401 12.99 16.15 13 L 13.01 13 L 13.01 16.14 C 13.012 16.318 12.966 16.493 12.878 16.647 C 12.789 16.801 12.661 16.929 12.507 17.018 C 12.353 17.106 12.178 17.152 12 17.15 C 11.822 17.152 11.647 17.106 11.493 17.018 C 11.339 16.929 11.211 16.801 11.122 16.647 C 11.034 16.493 10.988 16.318 10.99 16.14 L 10.99 13 L 7.85 13 C 7.672 13.002 7.497 12.956 7.343 12.868 C 7.189 12.779 7.061 12.651 6.972 12.497 C 6.884 12.343 6.838 12.168 6.84 11.99 C 6.838 11.812 6.884 11.637 6.972 11.483 C 7.061 11.329 7.189 11.201 7.343 11.112 C 7.497 11.024 7.672 10.978 7.85 10.98 L 10.99 10.98 L 10.99 7.84 C 10.988 7.662 11.034 7.487 11.122 7.333 C 11.211 7.179 11.339 7.051 11.493 6.962 C 11.647 6.874 11.822 6.828 12 6.83 Z")
//        .toNodes()
//}

//@OptIn(ExperimentalAnimationGraphicsApi::class)
//val AddToFill = AnimatedImageVector.
//
//
//
//@Preview
//@Composable
//fun JellyfishAnimation() {
//    val vectorPainter = rememberVectorPainter(
//        defaultWidth = 24.dp,
//        defaultHeight = 24.dp,
//        viewportWidth = 24f,
//        viewportHeight = 24f,
//        autoMirror = true,
//    ) { _, _ ->
//        val duration = 3000
//        val transition = rememberInfiniteTransition()
//        val scale = animateFloat(
//            initialValue = 0f,
//            targetValue = 1f,
//            animationSpec = tween(duration, easing = EaseInOut)
//        ).value
//        Path(
//            pathData = addPath,
//            fill = SolidColor(Color.White),
//            fillAlpha = 0.49f,
//        )
//        Group(
//            name = "Add",
//            translationY = translationY,
//            pivotX = 12f,
//            pivotY = 12f,
//            scaleX = 0.8f,
//            scaleY = 0.8f,
//        ) {
//            Path(
//                pathData = AddToFillPath.plusPath,
//                fill = SolidColor(Color.White),
//                fillAlpha = 0.49f,
//            )
//        }
//    }
//
//    Image(
//        vectorPainter,
//        contentDescription = "Jellyfish",
//        modifier = Modifier
//            .fillMaxSize()
//            .background(largeRadialGradient),
//    )
//}
