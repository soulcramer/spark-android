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
@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.spark.compose)
    alias(libs.plugins.spark.application)
    alias(libs.plugins.spark.kotlinMultiplatform)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.protobuf)
    id("org.jetbrains.compose.hot-reload") version "1.0.0-alpha11"
    id("com.squareup.wire") version "5.3.3"
}

android {
    namespace = "com.adevinta.spark.catalog"
    defaultConfig.applicationId = "com.adevinta.spark.catalog"
    defaultConfig.resourceConfigurations.addAll(
        setOf("en-rGB", "fr"),
    )
    defaultConfig {
        versionName = version.toString()
        if (providers.environmentVariable("GITHUB_ACTION").isPresent) {
            versionName = version.toString().replace("SNAPSHOT", System.getenv("GITHUB_SHA").take(7))
        }
    }

    compileOptions.isCoreLibraryDesugaringEnabled = true

//    kotlinOptions {
//        freeCompilerArgs += listOf(
//            "-opt-in=com.adevinta.spark.InternalSparkApi",
//            "-opt-in=com.adevinta.spark.ExperimentalSparkApi",
//        )
//    }

    val keystore = rootProject.file("keystore.properties")
        .takeIf { it.exists() }
        ?.let { Properties().apply { load(it.inputStream()) } }

    val debug by signingConfigs.getting
    val release by signingConfigs.creating {
        if (keystore == null) return@creating
        keyAlias = keystore.getProperty("keyAlias")
        keyPassword = keystore.getProperty("keyPassword")
        storeFile = file(keystore.getProperty("storeFile"))
        storePassword = keystore.getProperty("storePassword")
    }

    buildTypes.named("release") {
        signingConfig = if (keystore != null) release else debug
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.sparkIcons)
            implementation(projects.sparkCore)
            implementation(projects.spark)

            implementation(kotlin("reflect"))
            implementation(libs.kotlinx.collections.immutable)

            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.uiUtil)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.uiTest)

            api(compose.material3AdaptiveNavigationSuite) {
                exclude(group = "org.jetbrains.androidx.window")
            }
            api("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0-alpha02") {
                exclude(group = "org.jetbrains.androidx.window")
            }
            api(libs.androidx.window.core)
            implementation(libs.androidx.navigation.compose)
            implementation(compose.components.resources)
            implementation(libs.androidx.graphics.shapes)
            implementation(libs.androidx.datastore.core)
            implementation(libs.protobuf.kotlin.lite)
            implementation(libs.material.motion)

        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.coroutines.swing)
        }

        androidMain.dependencies {
            implementation(libs.accompanist.drawablepainter)
            implementation(compose.preview)

            implementation(libs.androidx.activity)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appCompat)
            implementation(libs.androidx.datastore)
        }
    }
}

compose {
    resources {
        publicResClass = false
        packageOfResClass = "com.adevinta.spark.catalog"
        generateResClass = auto
    }

    desktop {
        application {
            mainClass = "com.adevinta.spark.catalog.MainKt"

            nativeDistributions {
                modules("java.sql")
                targetFormats(
                    TargetFormat.Dmg,
                    TargetFormat.Msi,
                    TargetFormat.Deb,
                )
                packageName = "com.adevinta.spark.catalog"
                packageVersion = "1.0.0"
            }
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugarJdkLibs)
}

wire {
    kotlin {}
    sourcePath {
        srcDir("src/commonMain/proto")
    }
}
