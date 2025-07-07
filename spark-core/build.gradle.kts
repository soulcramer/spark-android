plugins {
    alias(libs.plugins.spark.library)
    alias(libs.plugins.spark.compose)
    alias(libs.plugins.spark.kotlinMultiplatform)
    alias(libs.plugins.spark.dokka)
    alias(libs.plugins.spark.publishing)
    alias(libs.plugins.spark.dependencyGuard)
}

android {
    namespace = "com.adevinta.spark.core"
    resourcePrefix = "spark_core_"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.sparkIcons)
            api(compose.ui)
            api(compose.uiUtil)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.components.resources)
//            api(compose.material3AdaptiveNavigationSuite) {
//                exclude(group = "org.jetbrains.androidx.window")
//            }
            api("org.jetbrains.compose.material3.adaptive:adaptive:1.2.0-alpha02") {
                exclude(group = "org.jetbrains.androidx.window")
            }
            api(libs.androidx.window.core)
        }
        androidMain.dependencies {
            implementation(compose.components.uiToolingPreview)
            implementation(libs.accompanist.drawablepainter)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core)

            implementation(libs.androidx.appCompat.resources)
            api(compose.animationGraphics)
        }
        jvmMain.dependencies {
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.adevinta.spark.core"
    generateResClass = auto
}

//compose.resources {
//    publicResClass = true
//    packageOfResClass = "com.adevinta.spark.icons"
//    generateResClass = auto
//}
