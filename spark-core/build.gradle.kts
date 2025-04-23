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
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(libs.androidx.compose.material3.windowSizeClass)
            api(compose.components.resources)
            implementation(compose.preview)
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
    compilerOptions {
        freeCompilerArgs.addAll(
            listOf(
                "-opt-in=com.adevinta.spark.InternalSparkApi",
                "-opt-in=com.adevinta.spark.ExperimentalSparkApi",
            ),
        )
    }
//    androidLibrary {
//        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
//    }
}

//compose.resources {
//    publicResClass = true
//    packageOfResClass = "com.adevinta.spark.icons"
//    generateResClass = auto
//}
