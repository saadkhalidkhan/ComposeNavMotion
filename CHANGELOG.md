# Changelog

## [Unreleased]

## [1.1.0] - 2026-06-29

### Added
- MVP 5: `AnimatedNavHost`, type-safe `animatedComposable<T>`, and `animatedNavigation<T>`
- `NavMotion` / `NavMotionSpec` APIs with `NavMotionConfig` accessibility support
- Modular artifacts: `navmotion-core`, `navmotion-material`, `navmotion-shared`
- `SharedNavElement` and `AnimatedNavHostWithSharedTransitions` (Compose shared transition API)
- `navmotion-sample` demo app replacing `:app`
- Global default animation, per-screen override, and nested graph animation resolution

### Changed
- `:nav-animation` is now a publish aggregator over core, material, and shared modules
- `NavAnimation` / `NavAnimationSpec` deprecated in favor of `NavMotion` / `NavMotionSpec`

## [1.0.0] - 2026-06-14

### Added
- Maven Central and JitPack publishing
- Custom animation builder via `NavAnimation.custom()`
- `NavAnimation.directionAware()` for forward/backward navigation transitions
- Material motion presets via `MaterialNavMotion`

## [0.1.0] - 2026-04-20

### Added
- Preset animations and `animatedComposable()` extension
