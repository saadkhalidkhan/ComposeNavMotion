package com.composenavmotion

import org.junit.Assert.assertSame
import org.junit.Test

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
}
