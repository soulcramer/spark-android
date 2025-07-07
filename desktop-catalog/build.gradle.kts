import org.jetbrains.compose.desktop.application.dsl.TargetFormat
plugins {
    // We have to use KMP due to Moko-resources
    // https://github.com/icerockdev/moko-resources/issues/263
    alias(libs.plugins.spark.kotlinMultiplatform)
    alias(libs.plugins.spark.compose)
    id("org.jetbrains.compose.hot-reload") version "1.0.0-alpha11"
}

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                implementation(projects.sparkIcons)
                implementation(projects.sparkCore)
                implementation(projects.spark)
//                implementation(projects.shared.qa)
                implementation(kotlin("reflect"))
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.uiUtil)
//                implementation(compose.preview)
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                implementation(compose.desktop.currentOs)
                implementation(compose.components.resources)
                implementation(libs.kotlin.coroutines.swing)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.adevinta.spark.catalog.MainKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.adevinta.spark.catalog"
            packageVersion = "1.0.0"
        }
    }
}
