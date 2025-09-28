package org.oreplay.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.oreplay.app.model.EventClient
import org.oreplay.app.view.ClassScreen
import org.oreplay.app.view.EventScreen
import org.oreplay.app.view.HomeScreen
import org.oreplay.app.view.ResultsScreen
import org.oreplay.app.viewmodel.*

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(client: EventClient, root: RootComponent) {
    MaterialTheme{
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation<Any, Any>(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.HomeScreen -> HomeScreen(instance.component, client)
                is RootComponent.Child.EventScreen -> EventScreen(instance.component.event, instance.component, client)
                is RootComponent.Child.ClassScreen -> ClassScreen(instance.component, client)
                is RootComponent.Child.ResultsScreen -> ResultsScreen(instance.component, client)
            }
        }
    }
}