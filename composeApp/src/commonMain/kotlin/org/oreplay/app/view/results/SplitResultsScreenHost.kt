package org.oreplay.app.view.results

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.oreplay.app.model.EventClient
import org.oreplay.app.model.RunnerResult
import org.oreplay.app.model.data.Runner
import org.oreplay.app.view.results.splits.AccumulatedScreen
import org.oreplay.app.view.results.splits.SplitScreen
import org.oreplay.app.viewmodel.ResultsScreenComponent

@Composable
fun SplitResultsScreenHost(
    navController: NavHostController,
    startDestination: SplitsDestination,
    data: List<Runner>,
) {
    NavHost(
        navController,
        startDestination.route
    ) {
        SplitsDestination.entries.forEach { destination ->
            composable (destination.route) {
                when (destination) {
                    SplitsDestination.SPLITS -> SplitScreen(data)
                    SplitsDestination.ACCUMULATED -> AccumulatedScreen(data)
                }
            }
        }
    }
}