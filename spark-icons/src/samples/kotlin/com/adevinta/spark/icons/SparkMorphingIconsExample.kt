/*
 * Copyright (c) 2024-2025 Adevinta
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
package com.adevinta.spark.icons.samples

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adevinta.spark.icons.SparkIcon
import com.adevinta.spark.icons.SparkMorphingIcons
import com.adevinta.spark.icons.SparkMorphingIconsAsSparkIcons
import com.adevinta.spark.icons.SparkMorphingIconsDefaults
import com.adevinta.spark.components.icons.Icon

/**
 * Example demonstrating path morphing capabilities with Spark Icons.
 * Shows how our rememberVectorPainter approach can achieve morphing animations
 * with controllable durations and animation specs.
 */

@Preview
@Composable
private fun MorphingIconsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Spark Morphing Icons - Customizable Animations")
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsAsSparkIcons.PlusToClose(
                        durationMillis = 1000,
                        animationSpec = tween(1000, easing = FastOutSlowInEasing)
                    ),
                    contentDescription = "Plus to Close",
                    modifier = Modifier.size(48.dp)
                )
                Text("Plus → Close\n(1s, FastOutSlowIn)")
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsAsSparkIcons.MenuToArrow(
                        durationMillis = 2000,
                        animationSpec = tween(2000, easing = LinearEasing),
                        includeRotation = false
                    ),
                    contentDescription = "Menu to Arrow",
                    modifier = Modifier.size(48.dp)
                )
                Text("Menu → Arrow\n(2s, Linear, No Rotation)")
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsAsSparkIcons.PlayPause(
                        durationMillis = 800
                    ),
                    contentDescription = "Play Pause",
                    modifier = Modifier.size(48.dp)
                )
                Text("Play ⟷ Pause\n(800ms)")
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsAsSparkIcons.HeartFillMorph(
                        durationMillis = 3000,
                        includeScaling = false
                    ),
                    contentDescription = "Heart Fill",
                    modifier = Modifier.size(48.dp)
                )
                Text("Heart Fill\n(3s, No Scaling)")
            }
        }
    }
}

@Preview
@Composable
private fun DefaultMorphingIconsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Default Morphing Icons")
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsDefaults.PlusToClose,
                    contentDescription = "Plus to Close",
                    modifier = Modifier.size(48.dp)
                )
                Text("Default Plus")
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    sparkIcon = SparkMorphingIconsDefaults.PlayPause,
                    contentDescription = "Play Pause",
                    modifier = Modifier.size(48.dp)
                )
                Text("Default Play")
            }
        }
    }
}

@Preview
@Composable
private fun DirectPainterPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Direct Painter Usage with Custom Parameters")
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.foundation.Image(
                    painter = SparkMorphingIcons.PlusToClose(
                        durationMillis = 500,
                        autoMirror = false
                    ),
                    contentDescription = "Plus to Close",
                    modifier = Modifier.size(48.dp)
                )
                Text("500ms, No Mirror")
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                androidx.compose.foundation.Image(
                    painter = SparkMorphingIcons.MenuToArrow(
                        durationMillis = 2500,
                        includeRotation = true
                    ),
                    contentDescription = "Menu to Arrow",
                    modifier = Modifier.size(48.dp)
                )
                Text("2.5s, With Rotation")
            }
        }
    }
}

/**
 * Usage examples for developers
 */
@Composable
private fun MorphingUsageExamples() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("""
            ## Usage Examples
            
            ### 1. Using SparkIcon.AnimatedVector with Custom Duration:
            
            ```kotlin
            Icon(
                sparkIcon = SparkMorphingIconsAsSparkIcons.PlusToClose(
                    durationMillis = 1500,
                    animationSpec = tween(1500, easing = FastOutSlowInEasing)
                ),
                contentDescription = "Add or Close"
            )
            ```
            
            ### 2. Direct Painter with Custom Parameters:
            
            ```kotlin
            Image(
                painter = SparkMorphingIcons.MenuToArrow(
                    durationMillis = 800,
                    includeRotation = false
                ),
                contentDescription = "Menu"
            )
            ```
            
            ### 3. Default Instances (Quick Usage):
            
            ```kotlin
            Icon(
                sparkIcon = SparkMorphingIconsDefaults.PlayPause,
                contentDescription = "Play or Pause"
            )
            ```
            
            ### 4. Custom Animation Specs:
            
            ```kotlin
            val customIcon = SparkMorphingIconsAsSparkIcons.HeartFillMorph(
                durationMillis = 2000,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                ),
                includeScaling = true
            )
            ```
            
            ### Animation Controls Available:
            - **durationMillis**: Control animation speed (default varies by icon)
            - **animationSpec**: Use any Compose AnimationSpec (tween, spring, etc.)
            - **includeRotation**: Enable/disable rotation effects
            - **includeScaling**: Enable/disable scaling effects  
            - **autoMirror**: Control RTL mirroring behavior
            
            ### True Path Morphing:
            The icons now use your `animatePathAsState` function for smooth
            interpolation between compatible paths, giving results similar
            to Android's AnimatedVectorDrawable but cross-platform compatible!
        """.trimIndent())
    }
} 