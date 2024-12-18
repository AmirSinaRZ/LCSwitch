package com.amirsinarz.lcswitch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.pow

/*
*  cr: Amirsina Razghandi
*  12/18/2024
*/

@Composable
fun SwitcherC(
    modifier: Modifier = Modifier,
    width: Dp = 46.dp,
    height: Dp = 26.dp,
    checked: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    elevation: Dp = 4.dp,
    onColor: Color = Color(0xFF48ea8b),
    offColor: Color = Color(0xFFff4651),
    iconColor: Color = Color.White,
) {
    val density = LocalDensity.current
    val heightPx = with(density) { height.toPx() }
    val elevationPx = with(density) { elevation.toPx() }

    var isChecked by remember { mutableStateOf(checked) }
    var isPressed by remember { mutableStateOf(false) }

    val switcherRadius = heightPx / 2
    val iconRadius = switcherRadius * 0.5f
    val iconClipRadius = iconRadius / 2.25f
    val iconCollapsedWidth = (iconRadius - iconClipRadius) * 1.1f

    val iconProgress by animateFloatAsState(
        targetValue = if (isChecked) 0f else 1f,
        animationSpec = tween(
            durationMillis = 800,
            easing = BounceInterpolator(
                if (isChecked) 0.15 else 0.2,
                if (isChecked) 12.0 else 14.5
            ).toEasing()
        ), label = "IRDK"
    )

    val color by animateColorAsState(
        targetValue = if (isChecked) onColor else offColor,
        animationSpec = tween(300), label = "IDK"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100), label = "IDK2"
    )

    Canvas(
        modifier = modifier
            .scale(scale)
            .size(width, height)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        isChecked = !isChecked
                        onCheckedChange?.invoke(isChecked)
                    }
                )
            }
    ) {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = color.copy(alpha = 0.5f).toArgb()

            frameworkPaint.setShadowLayer(
                elevationPx,
                0f,
                elevationPx / 2,
                color.copy(alpha = 0.3f).toArgb()
            )

            canvas.drawCircle(
                center = Offset(switcherRadius, switcherRadius),
                radius = switcherRadius,
                paint = paint
            )
        }

        drawCircle(
            color = color,
            radius = switcherRadius,
            center = Offset(switcherRadius, switcherRadius)
        )

        val iconOffset = lerp(0f, iconRadius - iconCollapsedWidth / 2, iconProgress)
        val iconLeft = switcherRadius - iconCollapsedWidth / 2 - iconOffset
        val iconRight = switcherRadius + iconCollapsedWidth / 2 + iconOffset
        val iconTop = (switcherRadius * 2f - iconRadius * 2f) / 2f

        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = iconColor.toArgb()

            frameworkPaint.setShadowLayer(
                elevationPx / 2,
                0f,
                elevationPx / 4,
                Color.Black.copy(alpha = 0.2f).toArgb()
            )

            canvas.drawRoundRect(
                left = iconLeft,
                top = iconTop,
                right = iconRight,
                bottom = iconTop + iconRadius * 2f,
                radiusX = switcherRadius,
                radiusY = switcherRadius,
                paint = paint
            )
        }

        if (iconProgress > 0f) {
            val clipOffset = lerp(0f, iconClipRadius, iconProgress)
            val centerX = (iconLeft + iconRight) / 2
            val centerY = iconTop + iconRadius

            drawCircle(
                color = color,
                radius = clipOffset,
                center = Offset(centerX, centerY)
            )
        }
    }
}


@Composable
fun SwitcherL(
    modifier: Modifier = Modifier,
    width: Dp = 46.dp,
    height: Dp = 26.dp,
    checked: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    elevation: Dp = 4.dp,
    onColor: Color = Color(0xFF48ea8b),
    offColor: Color = Color(0xFFff4651),
    iconColor: Color = Color.White,
) {
    val density = LocalDensity.current
    val widthPx = with(density) { width.toPx() }
    val heightPx = with(density) { height.toPx() }
    val elevationPx = with(density) { elevation.toPx() }

    var isChecked by remember { mutableStateOf(checked) }
    var isPressed by remember { mutableStateOf(false) }

    val cornerRadius = heightPx / 2
    val iconSize = heightPx * 0.8f

    val iconProgress by animateFloatAsState(
        targetValue = if (isChecked) 0f else 1f,
        animationSpec = tween(
            durationMillis = 800,
            easing = BounceInterpolator(
                if (isChecked) 0.15 else 0.2,
                if (isChecked) 12.0 else 14.5
            ).toEasing()
        ), label = "bounce"
    )

    val translateX by animateFloatAsState(
        targetValue = if (isChecked) 0f else -(widthPx - heightPx),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ":D"
    )

    val color by animateColorAsState(
        targetValue = if (isChecked) onColor else offColor,
        animationSpec = tween(300), label = "/:"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100), label = "|:"
    )

    Canvas(
        modifier = modifier
            .scale(scale)
            .size(width, height)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        isChecked = !isChecked
                        onCheckedChange?.invoke(isChecked)
                    }
                )
            }
    ) {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = color.copy(alpha = 0.5f).toArgb()

            frameworkPaint.setShadowLayer(
                elevationPx,
                0f,
                elevationPx / 2,
                color.copy(alpha = 0.3f).toArgb()
            )

            canvas.drawRoundRect(
                left = 0f,
                top = 0f,
                right = widthPx,
                bottom = heightPx,
                radiusX = cornerRadius,
                radiusY = cornerRadius,
                paint = paint
            )
        }

        drawRoundRect(
            color = color,
            cornerRadius = CornerRadius(cornerRadius)
        )

        translate(left = translateX) {
            drawIntoCanvas { canvas ->
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()
                frameworkPaint.color = iconColor.toArgb()

                frameworkPaint.setShadowLayer(
                    elevationPx / 2,
                    0f,
                    elevationPx / 4,
                    Color.Black.copy(alpha = 0.2f).toArgb()
                )

                canvas.drawCircle(
                    center = Offset(
                        x = widthPx - heightPx / 2,
                        y = heightPx / 2
                    ),
                    radius = iconSize / 2,
                    paint = paint
                )
            }
        }
    }
}



private fun DrawScope.drawSwitcherXIcon(
    iconColor: Color,
    progress: Float,
    translateX: Float,
    size: Size
) {
    val height = size.height
    val iconSize = height * 0.6f

    translate(left = translateX) {
        drawRoundRect(
            color = iconColor,
            size = Size(iconSize, iconSize),
            topLeft = Offset(size.width - height, (height - iconSize) / 2),
            cornerRadius = CornerRadius(height / 2)
        )
    }
}

private class BounceInterpolator(
    private val amplitude: Double,
    private val frequency: Double
) {
    fun getInterpolation(time: Float): Float =
        (-1 * Math.E.pow(-time / amplitude) * cos(frequency * time) + 1).toFloat()

    fun toEasing() = Easing { fraction -> getInterpolation(fraction) }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}