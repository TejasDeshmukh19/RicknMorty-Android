package com.ricknmorty.characters.presentation.character_details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlin.math.roundToInt

@Composable
fun AnimatedAlignLayout(
    fraction: Float,
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    val spacingPx = with(LocalDensity.current) { spacing.toPx() }

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        // This layout requires exactly two children: the main text and the secondary text.
        require(measurables.size == 2) { "AnimatedAlignLayout requires exactly two children." }

        // Measure children without incoming constraints for flexible sizing.
        val mainTextPlaceable = measurables[0].measure(constraints.copy(minWidth = 0, minHeight = 0))
        val secondaryTextPlaceable = measurables[1].measure(constraints.copy(minWidth = 0, minHeight = 0))

        // --- DEFINE START (fraction=0) & END (fraction=1) POSITIONS ---

        // Start position (Stacked): Center the secondary text horizontally below the main one.
        val secondaryTextStartX = (mainTextPlaceable.width - secondaryTextPlaceable.width) / 2f
        val secondaryTextStartY = mainTextPlaceable.height.toFloat()

        // End position (Horizontal): Place secondary text to the right of the main one, aligning their baselines.
        val secondaryTextEndX = mainTextPlaceable.width + spacingPx
        val secondaryTextEndY = 14F //(mainTextBaseline - (mainTextPlaceable.height * 0.5f) - 8).toFloat()

        // --- INTERPOLATE ---

        // Linearly interpolate the position of the secondary text based on the fraction.
        val secondaryTextX = lerp(secondaryTextStartX.dp, secondaryTextEndX.dp, fraction)
        val secondaryTextY = lerp(secondaryTextStartY.dp, secondaryTextEndY.dp, fraction)

        // --- CALCULATE LAYOUT SIZE ---

        // Also interpolate the total size of the layout to ensure it resizes smoothly.
        val startWidth = maxOf(mainTextPlaceable.width, secondaryTextPlaceable.width)
        val endWidth = (mainTextPlaceable.width + spacingPx + secondaryTextPlaceable.width)
        val layoutWidth = lerp(startWidth.dp, endWidth.dp, fraction)

        val startHeight = mainTextPlaceable.height + secondaryTextPlaceable.height
        val endHeight = maxOf(mainTextPlaceable.height, secondaryTextEndY.roundToInt() + secondaryTextPlaceable.height)
        val layoutHeight = lerp(startHeight.dp, endHeight.dp, fraction)

        // --- PLACE CHILDREN ---

        layout(layoutWidth.value.toInt(), layoutHeight.value.toInt()) {
            // Main text is always placed at the origin.
            mainTextPlaceable.placeRelative(0, 0)
            // Secondary text is placed at its animated position.
            secondaryTextPlaceable.placeRelative(secondaryTextX.value.toInt(), secondaryTextY.value.toInt())
        }
    }
}