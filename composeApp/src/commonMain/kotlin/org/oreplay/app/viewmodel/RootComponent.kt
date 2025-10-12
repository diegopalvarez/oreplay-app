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
import org.oreplay.app.model.Club
import org.oreplay.app.model.Event
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.Stage
import org.oreplay.app.viewmodel.classes.ClassScreenComponent
import org.oreplay.app.viewmodel.home.HomeScreenComponent
import org.oreplay.app.viewmodel.results.ClubResultsScreenComponent
import org.oreplay.app.viewmodel.results.ResultsScreenComponent
import org.oreplay.app.viewmodel.stages.EventScreenComponent

class RootComponent(
    componentContext: ComponentContext,
    val client: EventClient
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
        context: ComponentContext,
    ): Child {
        return when(config) {
            Configuration.HomeScreen -> Child.HomeScreen(
                HomeScreenComponent(
                    componentContext = context,
                    client = client,
                    onNavigateToEventScreen = { raceEvent ->
                        navigation.pushNew(Configuration.EventScreen(raceEvent))
                    }
                )
            )
            is Configuration.EventScreen -> Child.EventScreen(
                EventScreenComponent(
                    event = config.raceEvent,
                    componentContext = context,
                    client = client,
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
                    client = client,
                    onGoBack = {
                        navigation.pop()
                    },
                    onNavigateToResultsScreen = { stage, raceClass ->
                        navigation.pushNew(Configuration.ResultsScreen(stage, raceClass))
                    },
                    onNavigateToClubResultsScreen = { stage, club ->
                        navigation.pushNew(Configuration.ClubResultsScreen(stage, club))
                    }
                )
            )

            is Configuration.ResultsScreen -> Child.ResultsScreen(
                ResultsScreenComponent(
                    componentContext = context,
                    stage = config.stage,
                    raceClass = config.raceClass,
                    client = client,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )

            is Configuration.ClubResultsScreen -> Child.ClubResultsScreen(
                ClubResultsScreenComponent(
                    componentContext = context,
                    stage = config.stage,
                    club = config.club,
                    client = client,
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

        data class ClubResultsScreen(val component: ClubResultsScreenComponent) : Child()
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

        /**
         * Club Results Screen: For the chosen club, shows its results
         */
        @Serializable
        data class ClubResultsScreen(val stage: Stage, val club: Club): Configuration()
    }
}