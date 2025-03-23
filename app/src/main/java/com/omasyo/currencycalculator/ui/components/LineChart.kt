package com.omasyo.currencycalculator.ui.components

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme
import java.util.Locale
import kotlin.math.abs

data class YLabel(
    val title: String,
    val magnitude: Double,
)

data class ToolTipAnchor(
    val anchorLeft: Boolean = true,
    val anchorBottom: Boolean = true,
) {
    val topStart = anchorLeft && !anchorBottom
    val topEnd = !anchorLeft && !anchorBottom
    val bottomStart = anchorLeft && anchorBottom
    val bottomEnd = !anchorLeft && anchorBottom
}

@Composable
fun LineChart(
    modifier: Modifier,
    data: Map<String, Double>,
    noOfIntervals: Int = 8,
    max: Double = remember(data) { data.values.max() },
    yInterval: Double = max / noOfIntervals,
    strokeWidth: Dp = 1f.dp,
    color: Color = Color(0xFF28ACFF),
    dotColor: Color = MaterialTheme.colorScheme.secondary,
    dotRadius: Dp = 2f.dp,
    selectedDotBorderColor: Color = MaterialTheme.colorScheme.onPrimary,
    selectedDotRadius: Dp = 3f.dp,
    showDots: Boolean = false,
    toolTipContent: @Composable (index: Int, anchor: ToolTipAnchor) -> Unit,
) {
    val min = remember(data) { data.values.min() }

    LineChart(
        modifier = modifier,
        data = remember(data) { data.mapValues { (_, value) -> value - min } },
        yLabel = List(noOfIntervals) {
            val value = it * yInterval
            YLabel("-", value)
        },
        max = max - min,
        strokeWidth = strokeWidth,
        color = color,
        dotColor = dotColor,
        dotRadius = dotRadius,
        selectedDotBorderColor = selectedDotBorderColor,
        selectedDotRadius = selectedDotRadius,
        showDots = showDots,
        toolTipContent = toolTipContent,
    )
}

@Composable
private fun LineChart(
    modifier: Modifier,
    data: Map<String, Double>,
    yLabel: List<YLabel>,
    max: Double = yLabel.lastOrNull()?.magnitude ?: 0.0,
    strokeWidth: Dp = 4f.dp,
    color: Color = Color(0xFF4AA5FF),
    dotRadius: Dp = 2f.dp,
    dotColor: Color = MaterialTheme.colorScheme.secondary,
    selectedDotBorderColor: Color = MaterialTheme.colorScheme.onPrimary,
    selectedDotRadius: Dp = 3f.dp,
    showDots: Boolean,
    toolTipContent: @Composable (index: Int, anchor: ToolTipAnchor) -> Unit,
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 32f.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (yLabel.isNotEmpty()) {
                for (label in yLabel.asReversed()) {
                    Text(
                        label.title, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        Column {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 10f.dp, vertical = 10f.dp),
                data = remember(data) {
                    data.values.map {
                        val ratio = it.toFloat() / max
                        if (ratio.isNaN()) 0f else ratio.toFloat()
                    }
                },
                lineColor = color,
                dotColor = dotColor,
                dotRadius = dotRadius,
                selectedDotBorderColor = selectedDotBorderColor,
                selectedDotRadius = selectedDotRadius,
                showDots = showDots,
                strokeWidth = strokeWidth,
                toolTipContent = toolTipContent,
            )
        }
    }
}

@Composable
private fun LineChart(
    modifier: Modifier = Modifier,
    data: List<Float>,
    lineColor: Color,
    strokeWidth: Dp,
    dotColor: Color,
    dotRadius: Dp,
    selectedDotBorderColor: Color,
    selectedDotRadius: Dp,
    showDots: Boolean,
    toolTipContent: @Composable (index: Int, anchor: ToolTipAnchor) -> Unit,
) {
    var touchOffset by remember { mutableStateOf(Offset.Unspecified) }

    var graphSize by remember { mutableStateOf(IntSize.Zero) }

    val offsets: List<Offset> = remember(graphSize) {

        if (data.isEmpty()) return@remember emptyList()

        val offset = (graphSize.width) / (data.size - 1)

        buildList {
            val tempOffset = Offset(0f, graphSize.height - (graphSize.height * data.first()))
            add(tempOffset)

            (1..data.lastIndex).fold(
                tempOffset
            ) { (_, _), currIdx ->

                val x = offset * currIdx
                val y = graphSize.height - (graphSize.height * data[currIdx])
                add(Offset(x.toFloat(), y))
                Offset(x.toFloat(), y)
            }

        }
    }

    val nearestIndex = offsets.getNearestIndex(touchOffset)


    Box(modifier) {
//        val baselineColor = MaterialTheme.colorScheme.outline
        Canvas(
            Modifier

                .fillMaxSize()
                .onSizeChanged { graphSize = it }
                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = {
//                            touchOffset = it
//                            awaitRelease()
//                            touchOffset = Offset.Unspecified
//                        }
//                    )
                    detectDragGestures(
                        onDragStart = { touchOffset = it },
                        onDragEnd = { touchOffset = Offset.Unspecified },
                        onDrag = { _, dragAmount -> touchOffset += dragAmount }
                    )
                }
        ) {
            if (data.isEmpty()) return@Canvas

//            drawLine(baselineColor, Offset(0f, size.height), Offset(size.width, size.height))

            val distance = (size.width) / (data.size - 1)

            val path = Path().apply {
                val diff = distance / 2
                moveTo(offsets.first().x, offsets.first().y)

                for (index in 1..offsets.lastIndex) {
                    val oldOffset = offsets[index - 1]

                    val x = offsets[index].x
                    val y = offsets[index].y

                    cubicTo(oldOffset.x + diff, oldOffset.y, offsets[index].x - diff, y, x, y)
                }
            }


            drawPath(path, lineColor, style = Stroke(width = strokeWidth.toPx()))
            val containerPath = Path().apply {
                addPath(path)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = containerPath,
                brush = Brush.linearGradient(
                    colors = listOf(lineColor.copy(1f), lineColor.copy(0.1f)),
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height)
                ),
                style = Fill
            )

            for (index in offsets.indices) {
                if (index == nearestIndex) {
                    drawCircle(
                        selectedDotBorderColor,
                        radius = selectedDotRadius.toPx(),
                        center = offsets[index]
                    )
                }
                if (showDots || index == nearestIndex) {
                    drawCircle(dotColor, radius = dotRadius.toPx(), center = offsets[index])
                }
            }
        }


        val tooltipOffset by animateOffsetAsState(nearestIndex?.let { offsets[it] }
            ?: Offset.Unspecified)
        var anchor by remember { mutableStateOf(ToolTipAnchor()) }
        if (!touchOffset.isUnspecified) {
            Box(
                modifier = Modifier
                    .offset { tooltipOffset.round() }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        val maxWidth = constraints.maxWidth

                        val top = tooltipOffset.y - placeable.height
                        val right = tooltipOffset.x + placeable.width

                        anchor = ToolTipAnchor((right < maxWidth), top > 0)

                        layout(placeable.width, placeable.height) {
                            val x = if (!anchor.anchorLeft) -placeable.width else 0
                            val y = if (!anchor.anchorBottom) 0 else -placeable.height

                            placeable.place(x, y) // Shift up by its own height
                        }
                    }
            ) {
                toolTipContent(nearestIndex ?: 0, anchor)
            }
        }
    }
}

internal fun List<Offset>.getNearestIndex(target: Offset): Int? {
    if (target.isUnspecified) return null

    var smallest = 0
    forEachIndexed { index, _ ->
        smallest =
            if (abs(target.x - this[index].x) < abs(target.x - this[smallest].x)) index else smallest
    }
    return smallest
}

@Preview(fontScale = 0.85f)
@Composable
private fun Preview() {
    CurrencyCalculatorTheme {
        val data = mapOf(
            "Jan" to 10.0,
            "Feb" to 23.0,
            "Mar" to 7.0,
            "Apr" to 42.0,
            "May" to 34.0,
            "Jun" to 52.0,
            "Jul" to 12.0,
            "Aug" to 6.0,
            "Sep" to 0.0,
            "Oct" to 0.0,
            "Nov" to 0.0,
            "Dec" to 0.0,
        )
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {

            LineChart(
                data = data,
                modifier = Modifier
                    .height(400f.dp)
                    .fillMaxWidth()
                    .padding(8f.dp)
            ) { it, anchor ->
                ToolTip(
                    currency = data.keys.toList()[it].uppercase(Locale.getDefault()),
                    conversionRate = "4.242",
                    date = "15 Jun",
                    anchor = anchor,
                    modifier = Modifier.padding(
                        top = if (!anchor.anchorBottom) 8f.dp else 0f.dp,
                        bottom = if (anchor.anchorBottom) 8f.dp else 0f.dp,
                    )
                )
            }
        }
    }
}