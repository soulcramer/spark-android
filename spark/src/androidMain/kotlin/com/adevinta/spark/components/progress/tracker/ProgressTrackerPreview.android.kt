package com.adevinta.spark.components.progress.tracker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.adevinta.spark.PreviewTheme
import kotlinx.collections.immutable.persistentListOf

@PreviewScreenSizes
@Composable
private fun PreviewProgressTracker() {
    PreviewTheme(padding = PaddingValues(0.dp)) {
        var selectedStep by remember { mutableIntStateOf(0) }
        val size = ProgressSizes.Medium
        ProgressTrackerRow(
            items = persistentListOf(
                ProgressStep("Lorem ipsume", false),
                ProgressStep("Lorem ipsume dolar sit amet", true),
                ProgressStep("Lorem ipsume", false),
                ProgressStep("Lorem ipsume", true),
                ProgressStep("Lorem ipsume", false),
            ),
            size = size,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
        ProgressTrackerColumn(
            items = persistentListOf(
                ProgressStep(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                            "ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation.",
                    true,
                ),
                ProgressStep(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                            "ut labore et dolore magna aliqua.",
                    true,
                ),
                ProgressStep("Lorem ipsume dolar sit amet", true),
            ),
            size = size,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
    }
}

@Composable
@Preview(
    fontScale = 2f,
)
private fun PreviewProgressSizes() {
    PreviewTheme(padding = PaddingValues(0.dp)) {
        var selectedStep by remember { mutableIntStateOf(1) }
        val items = persistentListOf(
            ProgressStep("Lorem ipsume", true),
            ProgressStep("Lorem ipsume dolar sit amet", true),
            ProgressStep("Lorem ipsume", false),
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Large,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Medium,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Small,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
    }
}

@Composable
private fun PreviewProgressStyles() {
    PreviewTheme(padding = PaddingValues(0.dp)) {
        var selectedStep by remember { mutableIntStateOf(1) }
        val items = persistentListOf(
            ProgressStep("Lorem ipsume", true),
            ProgressStep("Lorem ipsume dolar sit amet", true),
            ProgressStep("Lorem ipsume", false),
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Large,
            style = ProgressStyles.Tinted,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Medium,
            style = ProgressStyles.Tinted,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
        ProgressTrackerRow(
            items = items,
            size = ProgressSizes.Small,
            style = ProgressStyles.Tinted,
            onStepClick = {
                selectedStep = it
            },
            selectedStep = selectedStep,
        )
    }
}

@Composable
@Preview
private fun PreviewProgressWithNoLabel() {
    PreviewTheme(padding = PaddingValues(0.dp), contentPadding = 0.dp) {
        val selectedStep by remember { mutableIntStateOf(1) }
        val items = persistentListOf(
            ProgressStep("", true),
            ProgressStep("", true),
            ProgressStep("", false),
        )
        for (size in ProgressSizes.entries) {
            ProgressTrackerRow(
                items = items,
                size = size,
                selectedStep = selectedStep,
            )
        }
        Row {
            for (size in ProgressSizes.entries) {
                ProgressTrackerColumn(
                    items = persistentListOf(
                        ProgressStep("qzd", false),
                        ProgressStep("qzd", false),
                        ProgressStep("zdq", false),
                    ),
                    size = size,
                    selectedStep = selectedStep,
                )
            }
        }
    }
}

@Composable
@Preview(
    group = "ProgressIndicator",
)
private fun PreviewProgressIndicator() {
    PreviewTheme {
        val selectedStep by remember { mutableIntStateOf(1) }
        val items = persistentListOf(
            ProgressStep("", true),
            ProgressStep("", true),
            ProgressStep("", false),
        )
        items.forEachIndexed { index, progressStep ->
            val isDone = index < selectedStep
            StepIndicator(
                colors = ProgressTrackerIntent.Basic.colors(),
                size = ProgressSizes.Large,
                style = ProgressStyles.Outlined,
                index = index,
                enabled = progressStep.enabled,
                selected = index == selectedStep,
                done = isDone,
                hasIndicatorContent = true,
                onClick = {},
                interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
            )
        }
    }
} 
