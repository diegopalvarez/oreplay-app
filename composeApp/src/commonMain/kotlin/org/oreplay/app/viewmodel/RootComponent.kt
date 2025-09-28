package org.oreplay.app.viewmodel

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.oreplay.app.model.Class
import org.oreplay.app.model.Event
import org.oreplay.app.model.Stage
import kotlin.jvm.JvmSerializableLambda

class RootComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext {

    // Create navigation stack
    private val navigation = StackNavigation<Configuration>()
    val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    // Child creation function
    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when(config) {
            Configuration.HomeScreen -> Child.HomeScreen(
                HomeScreenComponent(
                    componentContext = context,
                    onNavigateToEventScreen = { raceEvent ->
                        navigation.pushNew(Configuration.EventScreen(raceEvent))
                    }
                )
            )
            is Configuration.EventScreen -> Child.EventScreen(
                EventScreenComponent(
                    event = config.raceEvent,
                    componentContext = context,
                    onGoBack = {
                        navigation.pop()
                    },
                    onNavigateToClassScreen = { stage ->
                        navigation.pushNew(Configuration.ClassScreen(stage))
                    }
                )
            )
            is Configuration.ClassScreen -> Child.ClassScreen(
                ClassScreenComponent(
                    componentContext = context,
                    stage = config.stage,
                    onGoBack = {
                        navigation.pop()
                    },
                    onNavigateToResultsScreen = { stage, raceClass ->
                        navigation.pushNew(Configuration.ResultsScreen(stage, raceClass))
                    }
                )
            )

            is Configuration.ResultsScreen -> Child.ResultsScreen(
                ResultsScreenComponent(
                    componentContext = context,
                    stage = config.stage,
                    raceClass = config.raceClass,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    // Declare Screens
    sealed class Child {
        data class HomeScreen(val component: HomeScreenComponent): Child()
        data class EventScreen(val component: EventScreenComponent): Child()

        data class ClassScreen(val component: ClassScreenComponent) : Child()

        data class ResultsScreen(val component: ResultsScreenComponent): Child()
    }

    // Declare Configuration
    @Serializable
    sealed class Configuration {
        /**
         * Home Screen: Shows the current, past and future events
         */
        @Serializable
        data object HomeScreen: Configuration()

        /**
         * Event Screen: After choosing an event, shows the different stages scheduled, if applicable
         */
        @Serializable
        data class EventScreen(val raceEvent: Event): Configuration()

        /**
         * Class Screen: For the chosen stage, shows the available classes
         */
        @Serializable
        data class ClassScreen(val stage: Stage): Configuration()

        /**
         * Results Screen: For the chosen class, shows its results
         */
        @Serializable
        data class ResultsScreen(val stage: Stage, val raceClass: Class): Configuration()
    }
}