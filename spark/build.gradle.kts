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
plugins {
    alias(libs.plugins.spark.library)
    alias(libs.plugins.spark.compose)
    alias(libs.plugins.spark.kotlinMultiplatform)
    alias(libs.plugins.spark.dokka)
    alias(libs.plugins.spark.publishing)
    alias(libs.plugins.spark.dependencyGuard)
}

android {
    namespace = "com.adevinta.spark"
    resourcePrefix = "spark_"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "com.adevinta.spark"
    generateResClass = always
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.sparkIcons)
            api(projects.sparkCore)
            api(compose.ui)
            api(compose.uiUtil)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.animationGraphics)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.kotlinx.collections.immutable)
            implementation(libs.androidx.lifecycle)
            api(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            api(compose.material3AdaptiveNavigationSuite) {
                exclude(group = "org.jetbrains.androidx.window")
            }
            api("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0-alpha02") {
                exclude(group = "org.jetbrains.androidx.window")
            }
            api(libs.androidx.window.core)
            implementation("be.digitalia.compose.htmlconverter:htmlconverter:1.1.0")
        }

        androidMain.dependencies {
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.constraintlayout.compose)
            implementation(libs.accompanist.drawablepainter)
            implementation(libs.androidx.appCompat.resources)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core)

            // Removed all androidx Compose dependencies to avoid conflicts with JetBrains Compose Multiplatform
            // Using JetBrains Compose equivalents from commonMain instead
        }

        jvmMain.dependencies {
            // JVM-specific dependencies can be added here
        }
    }
}
