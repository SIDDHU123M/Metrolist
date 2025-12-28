/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun PlayerBackground(
    gradientColors: List<Color>,
    alpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = gradientColors,
            transitionSpec = {
                fadeIn(tween(800)).togetherWith(fadeOut(tween(800)))
            },
            label = "player_bg"
        ) { colors ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = if (colors.isNotEmpty() && colors.size >= 2) {
                            Brush.verticalGradient(
                                colors = listOf(
                                    colors[0].copy(alpha = 0.9f * alpha),
                                    colors.getOrElse(1) { colors[0] }.copy(alpha = 0.95f * alpha),
                                    MaterialTheme.colorScheme.surface.copy(alpha = alpha)
                                )
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha),
                                    MaterialTheme.colorScheme.surface.copy(alpha = alpha)
                                )
                            )
                        }
                    )
            )
        }
    }
}
