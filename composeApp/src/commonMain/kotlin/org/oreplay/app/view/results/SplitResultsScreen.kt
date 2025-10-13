package org.oreplay.app.view.results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
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
import androidx.navigation.compose.rememberNavController
import org.oreplay.app.model.data.Runner

enum class SplitsDestination(
    val route: String,
    val label: String,
    val contentDescription: String,
    val icon: ImageVector,
) {
    SPLITS("splitResults", "Splits", "Split results of the runners of the class", Icons.Filled.Timer),
    ACCUMULATED("accumulatedResults", "Accumulated", "Accumulated split results of the runner of the class", Icons.Filled.AreaChart),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitResultsScreen(
    data: List<Runner>,
    contentPadding: PaddingValues,
) {
    val navController = rememberNavController()
    val startDestination = SplitsDestination.SPLITS
    var selectedDestination by rememberSaveable {
        mutableIntStateOf(startDestination.ordinal)
    }

    val filteredData = data.filter { runner -> runner.result.finishTime != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        SecondaryTabRow(
            selectedTabIndex = selectedDestination,
            modifier = Modifier.fillMaxWidth(),
        ) {
            SplitsDestination.entries.forEachIndexed { index, destination ->
                Tab(
                    selected = selectedDestination == index,
                    onClick = {
                        navController.navigate(route = destination.route)
                        selectedDestination = index
                    },
                    text = {
                        Text(
                            text = destination.label,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    }
                )
            }
        }
        SplitResultsScreenHost(navController, startDestination, filteredData)
    }
}