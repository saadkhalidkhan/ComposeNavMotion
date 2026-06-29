# ComposeNavMotion

**ComposeNavMotion** is a reusable open-source library for **animated Jetpack Compose navigation**. It builds on Compose Animation and Navigation Compose with type-safe routes, global and per-screen animation control, Material motion presets, shared-element transitions, nested graphs, and accessibility-aware defaults.

**[Report a bug](https://github.com/saadkhalidkhan/ComposeNavMotion/issues)** · **[Contributing](CONTRIBUTING.md)** · **[License](LICENSE)**

![ComposeNavMotion demo](docs/images/demo_nav_motion.gif)

---

## Table of contents

* Preview
* Features
* Installation
* Basic usage
* Type-safe routes
* Global default animation
* Per-screen override
* Nested graphs
* Material motion presets
* Shared elements
* Accessibility
* Sample app
* Project structure
* Contributing
* License

## Features

| Feature | Description |
| --- | --- |
| **AnimatedNavHost** | Host-level default animation and accessibility config |
| **Type-safe routes** | `@Serializable` routes with `animatedComposable<T>` |
| **Animation resolution** | Per-screen → nested graph → global default → fade |
| **Preset transitions** | `fade`, `slideLeft`, `slideRight`, `slideUp`, `scale`, `none` |
| **Custom builder** | Compose transitions with shared duration and easing |
| **Direction-aware** | `directionAware()` for forward push / backward pop |
| **Material motion** | `MaterialNavMotion` shared axis, fade through, container transform, modal |
| **Shared elements** | `SharedNavElement` via Compose `SharedTransitionLayout` |
| **Nested graphs** | `animatedNavigation<T>` with graph-level animation |
| **Accessibility** | Disable animations or respect system animator duration scale |
| **Backward compatible** | MVP 1–4 string-route `NavAnimation` API still works |

---

## Preview

The demo above (400×889) was recorded from `:navmotion-sample` and shows:

- **Home hub** — presets, Material motion, and MVP 5 entry points
- **List screen** — type-safe route navigation
- **Details** — per-screen `containerTransform` override
- **Animation selector** — switch global default animation at runtime
- **Shared elements** — `SharedNavElement` profile avatar transitions
- **Nested graph** — `animatedNavigation<MainGraph>` with graph-level animation
- **Direction-aware checkout** — `slideLeft` forward / `slideRight` back
- **Profile, Settings, Modal, Sheet** — custom and Material presets

---

## Installation

### Maven Central

```kotlin
dependencies {
    implementation("io.github.saadkhalidkhan:composenavmotion:1.0.0")
}
```

The published `:nav-animation` artifact re-exports `:navmotion-core`, `:navmotion-material`, and `:navmotion-shared`.

### Modular dependencies (development)

```kotlin
implementation(project(":navmotion-core"))
implementation(project(":navmotion-material"))   // Material presets
implementation(project(":navmotion-shared"))     // SharedNavElement
```

Apply the Kotlin Serialization plugin in your app module for type-safe routes.

---

## Basic usage

```kotlin
import com.composenavmotion.AnimatedNavHost
import com.composenavmotion.animatedComposable
import com.composenavmotion.material.MaterialNavMotion
import kotlinx.serialization.Serializable

@Serializable object Home
@Serializable data class Details(val id: String)

AnimatedNavHost(
    navController = navController,
    startDestination = Home,
    defaultAnimation = MaterialNavMotion.sharedAxisX(),
) {
    animatedComposable<Home> {
        HomeScreen()
    }

    animatedComposable<Details>(
        animation = MaterialNavMotion.containerTransform(),
    ) { entry ->
        val details = entry.toRoute<Details>()
        DetailsScreen(details.id)
    }
}
```
---

## Type-safe routes

Annotate routes with `@Serializable` and register them with reified `animatedComposable`:

```kotlin
@Serializable object Home
@Serializable data class Details(val id: String)

animatedComposable<Home> { HomeScreen() }

animatedComposable<Details> { entry ->
    val args = entry.toRoute<Details>()
    DetailsScreen(args.id)
}
```

Navigate with route instances:

```kotlin
navController.navigate(Details("item-42"))
```

---

## Global default animation

Set one animation for the entire host:

```kotlin
AnimatedNavHost(
    navController = navController,
    startDestination = Home,
    defaultAnimation = MaterialNavMotion.sharedAxisX(),
) {
    animatedComposable<Home> { HomeScreen() }
    animatedComposable<Settings> { SettingsScreen() } // inherits sharedAxisX
}
```

---

## Per-screen override

Pass `animation` on a single destination to override the global default:

```kotlin
animatedComposable<Details>(
    animation = MaterialNavMotion.containerTransform(),
) { entry ->
    DetailsScreen(entry.toRoute<Details>().id)
}
```

**Resolution priority:** per-screen → nested graph → global default → `NavMotion.fade()`.

---

## Nested graphs

```kotlin
@Serializable object MainGraph
@Serializable object NestedHome
@Serializable data class NestedDetails(val id: String)

AnimatedNavHost(navController, startDestination = Home) {
    animatedNavigation<MainGraph>(
        startDestination = NestedHome,
        animation = MaterialNavMotion.sharedAxisY(),
    ) {
        animatedComposable<NestedHome> { NestedHomeScreen() }
        animatedComposable<NestedDetails> { entry ->
            NestedDetailsScreen(entry.toRoute<NestedDetails>().id)
        }
    }
}
```

Destinations inside the nested graph inherit the graph animation unless they override it.

---

## Material motion presets

```kotlin
import com.composenavmotion.material.MaterialNavMotion

MaterialNavMotion.sharedAxisX()
MaterialNavMotion.sharedAxisY()
MaterialNavMotion.fadeThrough()
MaterialNavMotion.containerTransform()
MaterialNavMotion.modal()
```

| Preset | Use case |
| --- | --- |
| `sharedAxisX()` | Lateral peer navigation |
| `sharedAxisY()` | Vertical parent/child flows |
| `fadeThrough()` | Unrelated destinations at the same level |
| `containerTransform()` | Detail expansion style (scale + fade) |
| `modal()` | Bottom sheet / modal presentation |

---

## Shared elements

Wrap matching content on source and target screens with the same `key`:

```kotlin
import com.composenavmotion.shared.AnimatedNavHostWithSharedTransitions
import com.composenavmotion.shared.SharedNavElement

AnimatedNavHostWithSharedTransitions(
    navController = navController,
    startDestination = Home,
) {
    animatedComposable<ProfileList> {
        SharedNavElement(key = "profile-image-$userId") {
            ProfileAvatar(userId)
        }
    }
    animatedComposable<ProfileDetail> { entry ->
        val id = entry.toRoute<ProfileDetail>().userId
        SharedNavElement(key = "profile-image-$id") {
            ProfileAvatar(id, large = true)
        }
    }
}
```

Uses Compose **`ExperimentalSharedTransitionApi`** (`SharedTransitionLayout` + `Modifier.sharedElement`). When shared transitions are unavailable, `SharedNavElement` renders content without shared-element behavior.

---

## Accessibility

### Disable all animations

```kotlin
AnimatedNavHost(
    navController = navController,
    startDestination = Home,
    animationsEnabled = false,
) { /* ... */ }
```

Or with full config:

```kotlin
AnimatedNavHost(
    navController = navController,
    startDestination = Home,
    config = NavMotionConfig(
        animationsEnabled = true,
        respectSystemAnimatorScale = true,
        defaultDuration = 300,
    ),
) { /* ... */ }
```

When animations are disabled, all destinations use `NavMotion.none()` (`EnterTransition.None` / `ExitTransition.None`).

`respectSystemAnimatorScale` disables transitions when the system **Animator duration scale** is `0` (Remove animations accessibility setting).

---

## Sample app

```bash
./gradlew :navmotion-sample:installDebug
```

The sample demonstrates:

- **Home** — hub for all demos
- **List screen** — browse items with type-safe routes
- **Details** — per-screen `containerTransform` override
- **Profile** — classic custom slide preset
- **Settings** & **Modal** — Material presets
- **Sheet** — custom mixed slide/fade
- **Animation selector** — change global default at runtime
- **Shared elements** — `SharedNavElement` profile avatars
- **Nested graph** — `animatedNavigation<MainGraph>`
- **Direction-aware checkout** — `NavMotion.directionAware()`
- **MVP 1–4 compatibility** — `NavAnimation` string-route API

---

## Project structure

| Module | Description |
| --- | --- |
| `:navmotion-core` | `AnimatedNavHost`, `NavMotion`, type-safe routes, config |
| `:navmotion-material` | `MaterialNavMotion` presets |
| `:navmotion-shared` | `SharedNavElement`, `AnimatedNavHostWithSharedTransitions` |
| `:nav-animation` | Published aggregator (Maven Central) |
| `:navmotion-sample` | Demo application |

### Backward compatibility (MVP 1–4)

The original API remains available:

```kotlin
import com.composenavmotion.NavAnimation
import com.composenavmotion.animatedComposable

NavHost(navController, startDestination = "home") {
    animatedComposable(route = "details", animation = NavAnimation.slideLeft()) {
        DetailsScreen()
    }
}
```

`NavAnimation` and `NavAnimationSpec` are deprecated aliases for `NavMotion` and `NavMotionSpec`.

---

## Contributing

Run tests and assemble the sample before opening a PR:

```bash
./gradlew :navmotion-core:testDebugUnitTest :navmotion-material:testDebugUnitTest :navmotion-shared:testDebugUnitTest :navmotion-sample:assembleDebug
```

See [CONTRIBUTING.md](CONTRIBUTING.md).

---

## License

Apache License 2.0 — see [LICENSE](LICENSE).

```
Copyright 2026 Saad Khan
```

## Author

**Saad Khan** — [GitHub](https://github.com/saadkhalidkhan) · ranasaad0799@gmail.com
