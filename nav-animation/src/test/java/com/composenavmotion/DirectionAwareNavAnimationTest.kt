package com.composenavmotion

import org.junit.Assert.assertSame
import org.junit.Test
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

class DirectionAwareNavAnimationTest {
  @Test
  fun directionAware_forwardEnter_usesForwardEnter() {
    val forward = NavAnimation.slideLeft()
    val backward = NavAnimation.slideRight()
    val spec = NavAnimation.directionAware(forward, backward)
    assertSame(forward.enterTransition, spec.enterTransition)
  }

  @Test
  fun directionAware_forwardExit_usesForwardExit() {
    val forward = NavAnimation.slideLeft()
    val backward = NavAnimation.slideRight()
    val spec = NavAnimation.directionAware(forward, backward)
    assertSame(forward.exitTransition, spec.exitTransition)
  }

  @Test
  fun directionAware_popEnter_usesBackwardEnter() {
    val forward = NavAnimation.slideLeft()
    val backward = NavAnimation.slideRight()
    val spec = NavAnimation.directionAware(forward, backward)
    assertSame(backward.enterTransition, spec.popEnterTransition)
  }

  @Test
  fun directionAware_popExit_usesBackwardExit() {
    val forward = NavAnimation.slideLeft()
    val backward = NavAnimation.slideRight()
    val spec = NavAnimation.directionAware(forward, backward)
    assertSame(backward.exitTransition, spec.popExitTransition)
  }

  @Test
  fun directionAware_worksWithPresetCombinations() {
    val presets = listOf(
      NavAnimation.fade() to NavAnimation.fade(),
      NavAnimation.slideLeft() to NavAnimation.slideRight(),
      NavAnimation.slideUp() to NavAnimation.slideUp(),
      NavAnimation.scale() to NavAnimation.scale(),
    )
  presets.forEach { (forward, backward) ->
      val spec = NavAnimation.directionAware(forward, backward)
      assertSame(forward.enterTransition, spec.enterTransition)
      assertSame(backward.enterTransition, spec.popEnterTransition)
    }
  }

  @Test
  fun directionAware_worksWithCustomAnimations() {
    val forward = NavAnimation.custom(
      enter = { fadeIn(animationSpec = tweenSpec()) },
      exit = { fadeOut(animationSpec = tweenSpec()) },
    )
    val backward = NavAnimation.custom(
      enter = { slideInHorizontally(animationSpec = tweenSpec()) },
      exit = { slideOutHorizontally(animationSpec = tweenSpec()) },
    )
    val spec = NavAnimation.directionAware(forward, backward)
    assertSame(forward.enterTransition, spec.enterTransition)
    assertSame(forward.exitTransition, spec.exitTransition)
    assertSame(backward.enterTransition, spec.popEnterTransition)
    assertSame(backward.exitTransition, spec.popExitTransition)
  }
}
