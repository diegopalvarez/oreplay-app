package org.oreplay.app.view.results

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.oreplay.app.model.data.Runner
import org.oreplay.app.view.results.splits.AccumulatedScreen
import org.oreplay.app.view.results.splits.SplitScreen

@Composable
fun SplitResultsScreenHost(
    navController: NavHostController,
    startDestination: SplitsDestination,
    data: List<Runner>,
) {
    val scroll = rememberScrollState()
    val verticalScroll = rememberLazyListState()

    NavHost(
        navController,
        startDestination.route
    ) {
        SplitsDestination.entries.forEach { destination ->
            composable (destination.route) {
                when (destination) {
                    SplitsDestination.SPLITS -> SplitScreen(data, scroll, verticalScroll)
                    SplitsDestination.ACCUMULATED -> AccumulatedScreen(data, scroll, verticalScroll)
                }
            }
        }
    }
}