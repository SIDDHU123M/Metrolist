/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.utils

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class GlassLevel {
    SUBTLE,
    MEDIUM,
    STRONG,
    ULTRA
}

fun Modifier.glassEffect(
    level: GlassLevel = GlassLevel.MEDIUM,
    cornerRadius: Dp = 16.dp,
    borderColor: Color = Color.White.copy(alpha = 0.2f),
    tint: Color = Color.White.copy(alpha = 0.1f),
    blurRadius: Float = when (level) {
        GlassLevel.SUBTLE -> 2f
        GlassLevel.MEDIUM -> 4f
        GlassLevel.STRONG -> 6f
        GlassLevel.ULTRA -> 8f
    },
    animated: Boolean = false
) = composed {
    val shape = RoundedCornerShape(cornerRadius)

    // DO NOT apply blur to content - only use semi-transparent backgrounds
    this
        .clip(shape)
        .background(tint, shape)
        .border(0.5.dp, borderColor, shape)
}

@RequiresApi(Build.VERSION_CODES.S)
private fun createBlurEffect(radius: Float) = RenderEffect
    .createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
    .asComposeRenderEffect()

fun Modifier.glassOverlay(level: GlassLevel = GlassLevel.MEDIUM) = composed {
    val overlayAlpha = when (level) {
        GlassLevel.SUBTLE -> 0.02f
        GlassLevel.MEDIUM -> 0.04f
        GlassLevel.STRONG -> 0.06f
        GlassLevel.ULTRA -> 0.08f
    }

    this.drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = overlayAlpha),
                    Color.White.copy(alpha = overlayAlpha * 0.3f),
                    Color.Transparent
                ),
                center = Offset(size.width * 0.3f, size.height * 0.2f),
                radius = size.maxDimension * 1.2f
            )
        )
    }
}

fun Modifier.glassElevation(
    level: Int = 1,
    cornerRadius: Dp = 16.dp
) = composed {
    val shadowColor = Color.Black.copy(alpha = 0.1f * level)
    val shape = RoundedCornerShape(cornerRadius)

    this.graphicsLayer {
        shadowElevation = (4 * level).toFloat()
        this.shape = shape
        clip = false
        ambientShadowColor = shadowColor
        spotShadowColor = shadowColor
    }
}

fun Modifier.glassGlow(
    glowColor: Color = Color.White,
    intensity: Float = 0.2f,
    cornerRadius: Dp = 16.dp
) = composed {
    val shape = RoundedCornerShape(cornerRadius)

    this.border(
        width = 0.5.dp,
        brush = Brush.linearGradient(
            colors = listOf(
                glowColor.copy(alpha = intensity * 0.6f),
                glowColor.copy(alpha = intensity * 0.4f),
                glowColor.copy(alpha = intensity * 0.2f),
                glowColor.copy(alpha = intensity * 0.4f),
                glowColor.copy(alpha = intensity * 0.6f)
            )
        ),
        shape = shape
    )
}

fun Modifier.shimmerGlass(
    baseColor: Color = Color.White.copy(alpha = 0.03f),
    highlightColor: Color = Color.White.copy(alpha = 0.08f)
) = composed {
    this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                baseColor,
                highlightColor,
                baseColor
            )
        )
    )
}
