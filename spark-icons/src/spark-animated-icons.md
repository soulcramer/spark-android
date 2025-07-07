# Spark Animated Icons

A KMP-compatible animated icon system built using `rememberVectorPainter` and Compose animation APIs, supporting rotation, scaling, translation, and **path morphing** animations.

## Overview

SparkAnimatedIcons provides cross-platform animated icons that work seamlessly across all Kotlin Multiplatform targets including Android, iOS, Desktop, and Web. The implementation uses `rememberVectorPainter` with custom animation logic, avoiding platform-specific dependencies while maintaining full control over animations.

## Key Advantages

✅ **Full KMP Compatibility** - Works on Android, iOS, Desktop, Web  
✅ **Zero Dependencies** - No third-party animation libraries required  
✅ **Vector-Based** - Scalable and lightweight  
✅ **Path Morphing Support** - Animate between different shapes
✅ **Customizable** - Full control over timing, easing, and behavior  
✅ **Integrated** - Works with existing SparkIcon system  
✅ **Performance Optimized** - Leverages Compose's efficient animation system

## Available Animated Icons

### CollapseExpand
A rotating arrow perfect for dropdown menus and expandable sections.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkAnimatedIconsDefaults.CollapseExpand,
    contentDescription = "Expand/Collapse"
)

// With custom parameters
Icon(
    sparkIcon = SparkAnimatedIconsAsSparkIcons.CollapseExpand(
        durationMillis = 1500,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        autoMirror = true
    ),
    contentDescription = "Expand/Collapse"
)
```

### PulsingHeart
A scaling heart for favorites and health features.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkAnimatedIconsDefaults.PulsingHeart,
    contentDescription = "Favorite"
)

// With custom pulse intensity
Icon(
    sparkIcon = SparkAnimatedIconsAsSparkIcons.PulsingHeart(
        durationMillis = 800,
        pulseIntensity = 0.3f  // More intense pulse
    ),
    contentDescription = "Favorite"
)
```

### LoadingSpinner
A continuously rotating spinner for loading states.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkAnimatedIconsDefaults.LoadingSpinner,
    contentDescription = "Loading"
)

// Faster spinning
Icon(
    sparkIcon = SparkAnimatedIconsAsSparkIcons.LoadingSpinner(
        durationMillis = 500,  // Faster rotation
        animationSpec = tween(500, easing = LinearEasing)
    ),
    contentDescription = "Loading"
)
```

### BellShake
A shaking bell for notifications and alerts.
```kotlin
// Default usage  
Icon(
    sparkIcon = SparkAnimatedIconsDefaults.BellShake,
    contentDescription = "Notifications"
)

// More aggressive shake
Icon(
    sparkIcon = SparkAnimatedIconsAsSparkIcons.BellShake(
        durationMillis = 400,
        shakeIntensity = 25f  // Stronger shake
    ),
    contentDescription = "Urgent notification"
)
```

### BouncingArrowDown
A bouncing arrow for scroll hints and call-to-action elements.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkAnimatedIconsDefaults.BouncingArrowDown,
    contentDescription = "Scroll down"
)

// Higher bounce
Icon(
    sparkIcon = SparkAnimatedIconsAsSparkIcons.BouncingArrowDown(
        durationMillis = 2000,
        bounceHeight = 8f  // Higher bounce
    ),
    contentDescription = "Scroll down"
)
```

## Path Morphing Icons

Path morphing allows smooth transitions between different icon shapes, similar to Android's `AnimatedVectorDrawable` but fully KMP compatible.

### PlusToClose
Morphs between a plus (+) and close (×) icon using true path interpolation.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkMorphingIconsDefaults.PlusToClose,
    contentDescription = "Add or Close"
)

// With custom duration and easing
Icon(
    sparkIcon = SparkMorphingIconsAsSparkIcons.PlusToClose(
        durationMillis = 1000,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        autoMirror = false
    ),
    contentDescription = "Add or Close"
)
```

### MenuToArrow
Transitions between hamburger menu and back arrow.
```kotlin
// Default with rotation
Icon(
    sparkIcon = SparkMorphingIconsDefaults.MenuToArrow,
    contentDescription = "Menu or Back"
)

// Without rotation effect
Icon(
    sparkIcon = SparkMorphingIconsAsSparkIcons.MenuToArrow(
        durationMillis = 2000,
        includeRotation = false
    ),
    contentDescription = "Menu or Back"
)
```

### PlayPause
Animates between play and pause states.
```kotlin
// Default usage
Icon(
    sparkIcon = SparkMorphingIconsDefaults.PlayPause,
    contentDescription = "Play or Pause"
)

// Faster morphing  
Icon(
    sparkIcon = SparkMorphingIconsAsSparkIcons.PlayPause(
        durationMillis = 800,
        animationSpec = tween(800, easing = LinearEasing)
    ),
    contentDescription = "Play or Pause"
)
```

### HeartFillMorph
Morphs from outline to filled heart with scaling.
```kotlin
// Default with scaling
Icon(
    sparkIcon = SparkMorphingIconsDefaults.HeartFillMorph,
    contentDescription = "Favorite"
)

// Without scaling effect
Icon(
    sparkIcon = SparkMorphingIconsAsSparkIcons.HeartFillMorph(
        durationMillis = 3000,
        includeScaling = false
    ),
    contentDescription = "Favorite"
)
```

### Direct Painter Usage (Alternative)
```kotlin
// If you need direct painter access
Image(
    painter = SparkMorphingIcons.PlusToClose(
        durationMillis = 1500,
        autoMirror = true
    ),
    contentDescription = "Add or Close"
)
```

## Usage Patterns

### 1. Default Instances (Quick & Easy)
```kotlin
@Composable
fun MyComponent() {
    Icon(
        sparkIcon = SparkAnimatedIconsDefaults.LoadingSpinner,
        contentDescription = "Loading",
        modifier = Modifier.size(24.dp)
    )
}
```

### 2. Customized Parameters (Recommended for specific needs)
```kotlin
@Composable  
fun MyComponent() {
    Icon(
        sparkIcon = SparkAnimatedIconsAsSparkIcons.LoadingSpinner(
            durationMillis = 800,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        ),
        contentDescription = "Loading"
    )
}
```

### 3. Direct Painter Usage (Alternative approach)
```kotlin
@Composable
fun MyComponent() {
    Image(
        painter = SparkAnimatedIcons.LoadingSpinner(
            durationMillis = 1500
        ),
        contentDescription = "Loading",
        modifier = Modifier.size(24.dp)
    )
}
```

### Custom Animation Parameters

Each animated icon uses carefully tuned parameters, but you can create custom versions by following the same pattern:

```kotlin
@Composable
val MyCustomSpinner: Painter
    get() = rememberVectorPainter(
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ) { _, _ ->
        val infiniteTransition = rememberInfiniteTransition(label = "CustomSpinner")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing) // Custom duration
            ),
            label = "Rotation"
        )
        
        Group(
            name = "CustomSpinner",
            pivotX = 12f,
            pivotY = 12f,
            rotation = rotation,
        ) {
            Path(
                pathData = PathParser().parsePathString("Your SVG path").toNodes(),
                fill = SolidColor(LocalContentColor.current),
            )
        }
    }
```

## Implementation Details

The animated icons are built using:

- **`rememberVectorPainter`** - Creates scalable vector graphics
- **`rememberInfiniteTransition`** - Manages continuous animations  
- **`animateFloat`** - Animates properties like rotation, scale, translation
- **`Group`** - Applies transformations to vector paths
- **`Path`** - Defines the icon shape using SVG path data

This approach follows the official Android documentation recommendations for [animated vector images in Compose](https://developer.android.com/develop/ui/compose/animation/vectors) while extending compatibility to all KMP platforms.

## Best Practices

1. **Use appropriate animation duration** - Keep animations smooth but not distracting
2. **Consider accessibility** - Provide meaningful content descriptions
3. **Respect system preferences** - Consider reduced motion settings when available
4. **Size appropriately** - Use `Modifier.size()` for consistent sizing
5. **Color integration** - Icons automatically use `LocalContentColor.current`

## Migration from Static Icons

Replacing static icons with animated versions is straightforward:

```kotlin
// Before (static)
Icon(
    sparkIcon = SparkIcons.ExpandMore,
    contentDescription = "Expand"
)

// After (animated)  
Icon(
    painter = SparkAnimatedIcons.CollapseExpand,
    contentDescription = "Expand"
)
```

## Performance Considerations

- Animations are GPU-accelerated through Compose's rendering pipeline
- Icons automatically pause when not visible (following Compose lifecycle)
- Vector-based rendering ensures minimal memory usage
- Animation states are efficiently managed by `rememberInfiniteTransition`

## Morphing vs Traditional AnimatedVectorDrawable

| Feature | Our KMP Approach | AnimatedVectorDrawable |
|---------|------------------|----------------------|
| **Platform Support** | ✅ Android, iOS, Desktop, Web | ❌ Android only |
| **Dependencies** | ✅ Pure Compose | ❌ Platform-specific resources |
| **Path Morphing** | ⚠️ Manual switching/interpolation | ✅ Built-in interpolation |
| **Animation Integration** | ✅ Full Compose animation system | ❌ Limited integration |
| **Performance** | ✅ GPU-accelerated via Compose | ✅ GPU-accelerated |
| **Resource Management** | ✅ Kotlin objects | ❌ XML resources |
| **Runtime Control** | ✅ Full programmatic control | ❌ Limited runtime control |

### Path Morphing Implementation

Our current morphing implementation uses path switching rather than true interpolation:

```kotlin
// Current approach (switching)
path(
    pathData = if (morphProgress < 0.5f) pathA else pathB
)

// Future enhancement: True interpolation
path(
    pathData = interpolatePaths(pathA, pathB, morphProgress)
)
```

For true path interpolation (like AnimatedVectorDrawable), paths must have:
- Same number of commands
- Same number of parameters per command  
- Compatible path structure

## Animation Controls Available

All animated icons now support customizable parameters:

- **durationMillis**: Control animation speed (default varies by icon)
- **animationSpec**: Use any Compose AnimationSpec (tween, spring, etc.)
- **Custom Parameters**: Each icon has specific parameters:
  - `autoMirror`: Enable/disable RTL mirroring (CollapseExpand)
  - `pulseIntensity`: Control scaling amount (PulsingHeart)
  - `shakeIntensity`: Control shake angle (BellShake)
  - `bounceHeight`: Control bounce distance (BouncingArrowDown)
  - `includeRotation`: Enable/disable rotation effects (morphing icons)
  - `includeScaling`: Enable/disable scaling effects (morphing icons)

## API Integration

The system provides three levels of API integration:

1. **SparkIcon.AnimatedVector**: Full integration with SparkIcon system
2. **Default Instances**: Quick access with sensible defaults  
3. **Direct Painters**: Direct `rememberVectorPainter` access for custom usage

## True Path Morphing ✨

The morphing icons now use your custom `animatePathAsState` function for smooth interpolation between compatible paths, giving results similar to Android's `AnimatedVectorDrawable` but cross-platform compatible!

## Future Enhancements

Planned additions include:
- State-based animations (play/pause control)
- Animation completion callbacks
- Additional icon variants
- Theme-aware animation speeds
- **Enhanced path compatibility validation** - Automatic detection of morphable paths
- Spring-based animation presets 