/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun GlassPlayerBlurBackground(
    thumbnailUrl: String?,
    alpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_glass")

    val shimmer1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer1"
    )

    val shimmer2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer2"
    )

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = thumbnailUrl,
            transitionSpec = {
                fadeIn(tween(1000)).togetherWith(fadeOut(tween(1000)))
            },
            label = "glass_blur_bg"
        ) { url ->
            if (url != null) {
                Box {
                    // Base blurred image
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(url)
                            .size(200, 200)
                            .allowHardware(false)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(80.dp)
                    )

                    // Dark overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.65f * alpha))
                    )

                    // Ambient color layers
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                drawContent()

                                // First ambient orb
                                drawRect(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.35f * alpha),
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f * alpha),
                                            Color.Transparent
                                        ),
                                        center = Offset(
                                            size.width * (0.2f + shimmer1 * 0.3f),
                                            size.height * (0.3f + shimmer2 * 0.2f)
                                        ),
                                        radius = size.maxDimension * 0.6f
                                    )
                                )

                                // Second ambient orb
                                drawRect(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f * alpha),
                                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f * alpha),
                                            Color.Transparent
                                        ),
                                        center = Offset(
                                            size.width * (0.8f - shimmer2 * 0.3f),
                                            size.height * (0.7f - shimmer1 * 0.2f)
                                        ),
                                        radius = size.maxDimension * 0.5f
                                    )
                                )

                                // Glass shimmer overlay
                                drawRect(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.05f * alpha),
                                            Color.Transparent,
                                            Color.White.copy(alpha = 0.03f * alpha)
                                        ),
                                        start = Offset.Zero,
                                        end = Offset(size.width, size.height)
                                    )
                                )
                            }
                    )
                }
            } else {
                // Fallback ambient background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .drawWithContent {
                            drawContent()

                            drawRect(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f * alpha),
                                        Color.Transparent
                                    ),
                                    center = Offset(size.width * 0.3f, size.height * 0.4f),
                                    radius = size.maxDimension * 0.6f
                                )
                            )
                        }
                )
            }
        }
    }
}

@Composable
fun GlassPlayerGradientBackground(
    thumbnailUrl: String?,
    gradientColors: List<Color>,
    alpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = Pair(thumbnailUrl, gradientColors),
            transitionSpec = {
                fadeIn(tween(1000)).togetherWith(fadeOut(tween(1000)))
            },
            label = "glass_gradient_bg"
        ) { (url, colors) ->
            Box {
                if (colors.isNotEmpty() && colors.size >= 2) {
                    // Gradient background from extracted colors
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colors[0].copy(alpha = 0.95f * alpha),
                                        colors.getOrElse(1) { colors[0] }.copy(alpha = 0.85f * alpha),
                                        colors.getOrElse(2) { Color.Black }.copy(alpha = 0.9f * alpha),
                                        Color.Black.copy(alpha = 0.95f * alpha)
                                    )
                                )
                            )
                            .drawWithContent {
                                drawContent()

                                // Subtle ambient enhancement
                                drawRect(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.08f * alpha),
                                            Color.Transparent
                                        ),
                                        center = Offset(size.width * 0.5f, size.height * 0.2f),
                                        radius = size.maxDimension * 0.5f
                                    )
                                )
                            }
                    )
                } else {
                    // Fallback
                    GlassPlayerBlurBackground(
                        thumbnailUrl = url,
                        alpha = alpha
                    )
                }
            }
        }
    }
}
