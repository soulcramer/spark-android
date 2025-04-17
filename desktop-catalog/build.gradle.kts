import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    // We have to use KMP due to Moko-resources
    // https://github.com/icerockdev/moko-resources/issues/263
    alias(libs.plugins.spark.kotlinMultiplatform)
    alias(libs.plugins.spark.compose)
}

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
//                implementation(projects.shared.qa)
//                implementation(compose.desktop.currentOs)
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
