/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.component

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metrolist.music.ui.utils.GlassLevel
import com.metrolist.music.ui.utils.glassEffect
import com.metrolist.music.ui.utils.glassGlow

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.MEDIUM,
    cornerRadius: Dp = 16.dp,
    tint: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
    borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    elevation: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .glassEffect(
                level = level,
                cornerRadius = cornerRadius,
                tint = tint,
                borderColor = borderColor
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.MEDIUM,
    cornerRadius: Dp = 20.dp,
    glowColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    showGlow: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val baseModifier = if (onClick != null) {
        modifier.then(
            Modifier.graphicsLayer {
                clip = true
                shape = RoundedCornerShape(cornerRadius)
            }
        )
    } else {
        modifier
    }

    Box(
        modifier = baseModifier
            .then(
                if (showGlow) {
                    Modifier.glassGlow(
                        glowColor = glowColor,
                        intensity = 0.4f,
                        cornerRadius = cornerRadius
                    )
                } else Modifier
            )
            .glassEffect(
                level = level,
                cornerRadius = cornerRadius,
                tint = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
    ) {
        content()
    }
}

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    blurBackground: Boolean = true,
    backgroundAlpha: Float = 0.85f,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && blurBackground) {
                    Modifier.blur(25.dp)
                } else Modifier
            )
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = backgroundAlpha)
            )
    ) {
        content()
    }
}

@Composable
fun GlassDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.blur(15.dp)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        GlassCard(
            modifier = modifier,
            level = GlassLevel.STRONG,
            cornerRadius = 24.dp,
            showGlow = true,
            glowColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            content = content
        )
    }
}

@Composable
fun AnimatedGlassCard(
    visible: Boolean,
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.MEDIUM,
    content: @Composable BoxScope.() -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = tween(300),
        label = "glass_scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(300),
        label = "glass_alpha"
    )

    AnimatedVisibility(visible = visible) {
        GlassCard(
            modifier = modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            },
            level = level,
            content = content
        )
    }
}

@Composable
fun PremiumGlassBackground(
    modifier: Modifier = Modifier,
    baseColor: Color = MaterialTheme.colorScheme.surface,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accentColor.copy(alpha = 0.15f),
                        baseColor.copy(alpha = 0.95f),
                        baseColor
                    ),
                    center = Offset.Zero,
                    radius = 1500f
                )
            )
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.05f),
                            Color.Transparent,
                            Color.White.copy(alpha = 0.03f)
                        ),
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    )
                )
            }
    ) {
        content()
    }
}
