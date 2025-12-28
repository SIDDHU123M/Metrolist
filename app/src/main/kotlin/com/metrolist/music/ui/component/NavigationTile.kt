/**
 * Metrolist Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.metrolist.music.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import android.os.Build
import com.metrolist.music.ui.utils.GlassLevel
import com.metrolist.music.ui.utils.glassEffect
import com.metrolist.music.ui.utils.glassGlow

@Composable
fun NavigationTile(
    title: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(6.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier
                            .glassEffect(
                                level = GlassLevel.MEDIUM,
                                cornerRadius = 28.dp,
                                tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                                blurRadius = 12f
                            )
                            .glassGlow(
                                glowColor = MaterialTheme.colorScheme.primary,
                                intensity = 0.12f,
                                cornerRadius = 28.dp
                            )
                    } else Modifier
                )
                .clickable(onClick = onClick),
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
