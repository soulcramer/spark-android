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
package com.adevinta.spark.icons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Demonstration of SparkAnimatedIcons usage across different KMP targets.
 * 
 * This example shows:
 * 1. Direct usage with Painter (recommended for simple cases)
 * 2. Integration with SparkIcon system (recommended for consistent icon handling)
 * 3. Various animation types available
 */
@Composable
fun SparkAnimatedIconsDemo() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Spark Animated Icons Demo")
        
        // Direct usage with Painter
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Direct Painter Usage")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = SparkAnimatedIcons.CollapseExpand,
                        contentDescription = "Collapse/Expand",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Icon(
                        painter = SparkAnimatedIcons.PulsingHeart,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Icon(
                        painter = SparkAnimatedIcons.LoadingSpinner,
                        contentDescription = "Loading",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Icon(
                        painter = SparkAnimatedIcons.BellShake,
                        contentDescription = "Notification",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Icon(
                        painter = SparkAnimatedIcons.BouncingArrowDown,
                        contentDescription = "Scroll down",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        
        // Integration with SparkIcon system
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("SparkIcon Integration")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Using extension functions for SparkIcon integration
                    com.adevinta.spark.components.icons.Icon(
                        sparkIcon = SparkAnimatedIcons.collapseExpand(),
                        contentDescription = "Collapse/Expand",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    com.adevinta.spark.components.icons.Icon(
                        sparkIcon = SparkAnimatedIcons.pulsingHeart(),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(32.dp)
                    )
                    
                    com.adevinta.spark.components.icons.Icon(
                        sparkIcon = SparkAnimatedIcons.loadingSpinner(),
                        contentDescription = "Loading",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

/**
 * Example of how to use animated icons in different contexts.
 */
@Composable
fun AnimatedIconUsageExamples() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Animation Use Cases")
        
        // Loading state
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = SparkAnimatedIcons.LoadingSpinner,
                contentDescription = "Loading",
                modifier = Modifier.size(16.dp)
            )
            Text(" Loading data...", modifier = Modifier.padding(start = 8.dp))
        }
        
        // Notification indicator
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = SparkAnimatedIcons.BellShake,
                contentDescription = "New notifications",
                modifier = Modifier.size(16.dp)
            )
            Text(" You have new notifications", modifier = Modifier.padding(start = 8.dp))
        }
        
        // Scroll hint
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Scroll down for more")
            Icon(
                painter = SparkAnimatedIcons.BouncingArrowDown,
                contentDescription = "Scroll down",
                modifier = Modifier.size(16.dp).padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun SparkAnimatedIconsDemoPreview() {
    SparkAnimatedIconsDemo()
}

@Preview
@Composable
fun AnimatedIconUsageExamplesPreview() {
    AnimatedIconUsageExamples()
} 