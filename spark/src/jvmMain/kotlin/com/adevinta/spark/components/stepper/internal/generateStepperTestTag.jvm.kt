package com.adevinta.spark.components.stepper.internal

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription

@OptIn(ExperimentalComposeUiApi::class)
public actual fun Modifier.generateStepperTestTag(testTag: String?, action: String): Modifier = testTag?.let {
    semantics {
        contentDescription = "" // handled by semantics modifier
        stateDescription = "" // handled by semantics modifier
        // testTagsAsResourceId is omitted as it's only available for Android
    }.testTag("${testTag}$action")
} ?: this 
