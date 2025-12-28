# Metrolist Glass Design System

## Overview

Metrolist now features a premium liquid glass design system with glassmorphism effects throughout the app. This guide explains how to use the glass components and utilities.

## Requirements

- **Android 12 (API 31) or higher** for full blur effects
- For devices below Android 12, the system gracefully falls back to semi-transparent designs

## Core Components

### 1. Glass Effects (Modifiers)

Located in: `app/src/main/kotlin/com/metrolist/music/ui/utils/GlassEffects.kt`

#### `Modifier.glassEffect()`

Applies glassmorphism with blur, transparency, and borders.

```kotlin
Modifier.glassEffect(
    level = GlassLevel.MEDIUM,  // SUBTLE, MEDIUM, STRONG, ULTRA
    cornerRadius = 16.dp,
    borderColor = Color.White.copy(alpha = 0.2f),
    tint = Color.White.copy(alpha = 0.1f),
    blurRadius = 25f,
    animated = false
)
```

#### `Modifier.glassGlow()`

Adds a glowing border effect.

```kotlin
Modifier.glassGlow(
    glowColor = MaterialTheme.colorScheme.primary,
    intensity = 0.3f,
    cornerRadius = 16.dp
)
```

#### `Modifier.glassElevation()`

Adds depth with shadows.

```kotlin
Modifier.glassElevation(
    level = 1,  // 1-5
    cornerRadius = 16.dp
)
```

### 2. Glass Components

Located in: `app/src/main/kotlin/com/metrolist/music/ui/component/GlassComponents.kt`

#### `GlassSurface`

Basic glass container.

```kotlin
GlassSurface(
    level = GlassLevel.MEDIUM,
    cornerRadius = 16.dp,
    tint = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
) {
    // Content
}
```

#### `GlassCard`

Card with glass effects and optional glow.

```kotlin
GlassCard(
    level = GlassLevel.MEDIUM,
    cornerRadius = 20.dp,
    glowColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    showGlow = true
) {
    // Content
}
```

#### `GlassDialog`

Full-screen dialog with glass backdrop.

```kotlin
GlassDialog(
    onDismissRequest = { /* dismiss */ }
) {
    // Dialog content
}
```

#### `PremiumGlassBackground`

Ambient gradient background.

```kotlin
PremiumGlassBackground(
    baseColor = MaterialTheme.colorScheme.surface,
    accentColor = MaterialTheme.colorScheme.primary
) {
    // Content
}
```

### 3. Ambient Backgrounds

Located in: `app/src/main/kotlin/com/metrolist/music/ui/component/AmbientBackground.kt`

#### `AmbientBackground`

Animated gradient background that shifts colors.

```kotlin
AmbientBackground(
    primaryColor = MaterialTheme.colorScheme.primary,
    secondaryColor = MaterialTheme.colorScheme.secondary,
    tertiaryColor = MaterialTheme.colorScheme.tertiary,
    baseColor = MaterialTheme.colorScheme.surface,
    animated = true
) {
    // Content
}
```

#### `ParticleBackground`

Static particle decoration background.

```kotlin
ParticleBackground(
    particleColor = Color.White.copy(alpha = 0.05f)
) {
    // Content
}
```

## Glass Levels

Four intensity levels are available:

- **SUBTLE**: Light blur (15px), minimal transparency - for subtle accents
- **MEDIUM**: Moderate blur (25px), balanced transparency - default for most components
- **STRONG**: Heavy blur (40px), more opaque - for prominent surfaces
- **ULTRA**: Maximum blur (60px), high opacity - for immersive overlays

## Applied Transformations

### Completed

1. **Mini Player** - Floating glass bar with blur, glow, and depth
2. **Home Screen** - Ambient animated background with color morphing
3. **Filter Chips** - Glass styling with subtle blur and glow on selection
4. **Foundation System** - Complete glass utilities and components library

### Usage Examples

#### Transform a simple Box into glass

Before:
```kotlin
Box(
    modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surface)
)
```

After:
```kotlin
Box(
    modifier = Modifier.glassEffect(
        level = GlassLevel.MEDIUM,
        cornerRadius = 16.dp
    )
)
```

#### Add glass to a Card

```kotlin
GlassCard(
    level = GlassLevel.MEDIUM,
    showGlow = true
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Title", style = MaterialTheme.typography.titleLarge)
        Text("Subtitle", style = MaterialTheme.typography.bodyMedium)
    }
}
```

#### Wrap a screen with ambient background

```kotlin
AmbientBackground(
    animated = true
) {
    LazyColumn {
        // Your content
    }
}
```

## Design Principles

1. **Layering** - Use different glass levels to create depth hierarchy
2. **Consistency** - Apply similar glass levels to similar components
3. **Performance** - Use SUBTLE level for frequently scrolling items
4. **Glow** - Add glows to interactive or important elements
5. **Contrast** - Ensure text remains readable on glass surfaces

## Color Recommendations

- **Tint Colors**: Use surface colors with 60-80% opacity
- **Border Colors**: Use outline colors with 10-30% opacity
- **Glow Colors**: Use primary/accent colors with 20-40% opacity

## Animation Tips

- Enable `animated = true` on `glassEffect()` for smooth transitions
- Use `AnimatedGlassCard` for show/hide animations
- Combine with Compose animations for smooth state changes

## Performance

- Glass effects use native RenderEffect API (Android 12+)
- Blur operations are GPU-accelerated
- Use lower glass levels for better performance on complex screens
- Consider disabling animations on low-end devices

## Future Enhancements

To apply glass throughout the app:

1. **Dialogs** - Replace all dialogs with `GlassDialog`
2. **Bottom Sheets** - Add glass backdrop and content styling
3. **List Items** - Add subtle glass to song/album/artist items
4. **Player Screen** - Full immersive glass experience
5. **Navigation** - Floating glass navigation bar
6. **Settings** - Glass preference cards
7. **Search** - Glass input field and results

## Troubleshooting

**Blur not showing?**
- Check Android version (requires API 31+)
- Verify `Build.VERSION.SDK_INT >= Build.VERSION_CODES.S`

**Performance issues?**
- Reduce glass level to SUBTLE
- Decrease blur radius
- Disable animations
- Use glass sparingly on complex screens

**Text not readable?**
- Increase tint opacity
- Add text shadows
- Use higher contrast colors
- Reduce glass transparency

## Contact

For questions or improvements to the glass system, refer to the main project documentation.

---

**Made with premium glass by the Metrolist team**
