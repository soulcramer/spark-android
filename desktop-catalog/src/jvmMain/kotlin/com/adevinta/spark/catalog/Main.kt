/*
 * Copyright (c) 2025 Adevinta
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

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.tivi.inject.DesktopApplicationComponent
import app.tivi.inject.WindowComponent
import app.tivi.inject.create
import app.tivi.screens.DiscoverScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator

public fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }

    LaunchedEffect(applicationComponent) {
        applicationComponent.initializers.initialize()
    }

    Window(
        title = "Tivi",
        onCloseRequest = ::exitApplication,
    ) {
        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }

        val backstack = rememberSaveableBackStack(listOf(DiscoverScreen))
        val navigator = rememberCircuitNavigator(backstack) { /* no-op */ }

        component.tiviContent.Content(
            backstack = backstack,
            navigator = navigator,
            onOpenUrl = {
                // no-op for now
                false
            },
            modifier = Modifier,
        )
    }
}
