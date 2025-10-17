package org.oreplay.app.view.results.splits

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import org.oreplay.app.model.controls.ControlItem
import org.oreplay.app.model.data.Runner
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration

@Composable
fun ResultsTable(
    data: List<Runner>,
    scroll: ScrollState,
    verticalScroll: LazyListState,
    cellWidth: Dp
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(data.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "No runners have finished yet",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                )
            }
        }
        else{
            TableHeader(scrollState = scroll, header = data.first().splits, width = cellWidth)
            TableContent(scrollState = scroll, listState = verticalScroll, data = data, width = cellWidth)
        }

        //DynamicTable(data)
    }
}

/**
 * TODO - Review usage
 */
@Composable
fun DynamicTable(
    tableData: List<Runner>
) {
    if(tableData.isEmpty()) {
        // TODO - Display "no splits available"
        return
    }

    val density = LocalDensity.current
    SubcomposeLayout() { constraints ->
        val rowCount = tableData.size
        val columnCount = tableData.first().splits.size

        // Measure all cells to determine max width
        var columnMaxWidth = 0
        var control: ControlItem? = null
        tableData.forEachIndexed { rowIndex, row ->
            var columnIndex = 0
            control = row.splits.first
            while(control != null) {
                val placeable = subcompose("Measure-$rowIndex-$columnIndex"){
                    getDataCell(control!!)
                }[0].measure(Constraints())

                columnMaxWidth = max(columnMaxWidth, placeable.width)

                control = control.next
                columnIndex++
            }
        }

        // Layout the table
        val columnWidthDp = with(density) { columnMaxWidth.toDp() }
        val rowHeights = mutableListOf<Int>()


        val rowPlaceables = tableData.mapIndexed { rowIndex, row ->
            val rowCells = mutableListOf<Placeable>()
            var maxRowHeight = 0
            var columnIndex = 0
            control = row.splits.first

            while(control != null) {
                val placeable = subcompose("Cell-$rowIndex-$columnIndex"){
                    Box(
                        modifier = Modifier
                            .width(columnWidthDp)
                    ){
                        getDataCell(control!!)
                    }
                }[0].measure(Constraints.fixedWidth(columnMaxWidth))

                control = control.next
                columnIndex++

                maxRowHeight = max(maxRowHeight, placeable.height)
                rowCells.add(placeable)
            }
            rowHeights.add(maxRowHeight)
            rowCells
        }

        // Compute total width
        val totalWidth = columnMaxWidth * columnCount
        val totalHeight = rowHeights.sum()

        layout(
            width = min(totalWidth, constraints.maxWidth),
            height = min(totalHeight, constraints.maxHeight)
        ) {
            var yOffset = 0
            rowPlaceables.forEachIndexed { rowIndex, row ->
                var xOffset = 0
                row.forEach { cell ->
                    cell.placeRelative(x = xOffset, y = yOffset)
                    xOffset += columnMaxWidth
                }
                yOffset += rowHeights[rowIndex]
            }
        }
    }
}

@Composable
fun getDataCell(
    control: ControlItem
){
    Column() {
        if(control.splitTime == Duration.INFINITE){
            Text(
                text = "--",
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        else{
            Text(
                text = control.splitTime.toComponents { hrs, min, sec, _ ->
                    if (hrs > 0) {
                        "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                    } else {
                        "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                    }
                }
                        + " (" + control.position + ")",
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        if(control.timeBehind == Duration.INFINITE){
            Text(
                text = "--",
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        else {

            Text(
                text = "+" + control.timeBehind.toComponents { hrs, min, sec, _ ->
                    if (hrs > 0) {
                        "${hrs}:${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                    } else {
                        "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
                    }
                },
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}