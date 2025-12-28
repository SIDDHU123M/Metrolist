/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.component

import android.os.Build
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AmbientBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary,
    tertiaryColor: Color = MaterialTheme.colorScheme.tertiary,
    baseColor: Color = MaterialTheme.colorScheme.surface,
    animated: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_transition")

    val animatedOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient_offset_1"
    )

    val animatedOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient_offset_2"
    )

    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient_scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(baseColor)
            .drawWithContent {
                if (animated) {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.25f * animatedScale),
                                secondaryColor.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            center = Offset(
                                size.width * (0.2f + animatedOffset1 * 0.3f),
                                size.height * (0.3f + animatedOffset2 * 0.2f)
                            ),
                            radius = size.maxDimension * (0.8f + animatedScale * 0.2f)
                        )
                    )

                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                tertiaryColor.copy(alpha = 0.2f),
                                secondaryColor.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            center = Offset(
                                size.width * (0.8f - animatedOffset2 * 0.3f),
                                size.height * (0.7f - animatedOffset1 * 0.2f)
                            ),
                            radius = size.maxDimension * 0.6f
                        )
                    )

                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.03f),
                                Color.Transparent,
                                Color.White.copy(alpha = 0.02f)
                            ),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                } else {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.2f),
                                secondaryColor.copy(alpha = 0.1f),
                                Color.Transparent
                            ),
                            center = Offset(size.width * 0.3f, size.height * 0.4f),
                            radius = size.maxDimension * 0.8f
                        )
                    )
                }

                drawContent()
            }
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.blur(80.dp)
                } else Modifier
            )
    ) {
        content()
    }
}

@Composable
fun ParticleBackground(
    modifier: Modifier = Modifier,
    particleColor: Color = Color.White.copy(alpha = 0.05f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                repeat(30) { index ->
                    val x = (size.width * ((index * 37) % 100)) / 100f
                    val y = (size.height * ((index * 73) % 100)) / 100f
                    val radius = (3 + (index % 5)).toFloat()

                    drawCircle(
                        color = particleColor,
                        radius = radius,
                        center = Offset(x, y)
                    )
                }
            }
    ) {
        content()
    }
}
