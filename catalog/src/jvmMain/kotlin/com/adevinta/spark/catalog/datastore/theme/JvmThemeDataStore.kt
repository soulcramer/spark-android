/*
 * Copyright (c) 2023-2025 Adevinta
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
package com.adevinta.spark.catalog.datastore.theme

import androidx.datastore.core.DataStore
import com.adevinta.spark.catalog.datastore.theme.AppConfigPaths.configDir
import com.adevinta.spark.catalog.datastore.theme.ThemePropertiesHandler.Companion.DATA_STORE_FILE_NAME
import com.adevinta.spark.catalog.datastore.theme.ThemePropertiesHandler.Companion.createDataStore
import okio.FileSystem
import okio.Path.Companion.toPath
import java.io.File

/**
 * Cross-platform configuration directory provider for Spark Catalog.
 * Follows platform-specific conventions for application data storage.
 */
public object AppConfigPaths {
    
    /**
     * Main configuration directory following platform conventions:
     * - Windows: %APPDATA%/SparkCatalog
     * - macOS: ~/Library/Application Support/SparkCatalog  
     * - Linux: ~/.config/spark-catalog (or $XDG_CONFIG_HOME/spark-catalog)
     */
    public val configDir: String by lazy { getConfigDirectory() }
    
    /**
     * Legacy directory for backward compatibility.
     * Will be used as fallback if standard directories are not accessible.
     */
    public val legacyDir: String = "${System.getProperty("user.home")}/.spark-catalog"
    
    private fun getConfigDirectory(): String {
        val osName = System.getProperty("os.name", "").lowercase()

        return try {
            when {
                osName.contains("windows") -> {
                    val appData = System.getenv("APPDATA")
                    if (appData != null) "$appData/SparkCatalog" else legacyDir
                }
                osName.contains("mac") -> {
                    "${System.getProperty("user.home")}/Library/Application Support/SparkCatalog"
                }
                else -> { // Linux and other Unix-like systems
                    val xdgConfigHome = System.getenv("XDG_CONFIG_HOME")
                    if (xdgConfigHome != null) {
                        "$xdgConfigHome/spark-catalog"
                    } else {
                        "${System.getProperty("user.home")}/.config/spark-catalog"
                    }
                }
            }
        } catch (e: Exception) {
            // Fallback to legacy directory if any issues occur
            System.err.println("Warning: Could not determine platform-specific config directory, using fallback: ${e.message}")
            legacyDir
        }
    }
}

internal fun getDataStore(): DataStore<ThemeProperties> {
    val configDir = File(System.getenv("SPARK_CATALOG_CONFIG_DIR") ?: configDir)
    val producePath = { configDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }
    return createDataStore(fileSystem = FileSystem.SYSTEM, producePath = producePath)
}
