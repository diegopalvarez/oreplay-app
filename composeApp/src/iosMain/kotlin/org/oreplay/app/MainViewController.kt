package org.oreplay.app

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.ktor.client.engine.darwin.Darwin
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.createHTTPClient
import org.oreplay.app.viewmodel.RootComponent

fun MainViewController() = ComposeUIViewController {
    val client = remember {
        EventClient(createHTTPClient(Darwin.create()))
    }
    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()), client)
    }
    App(
    root
    )
}