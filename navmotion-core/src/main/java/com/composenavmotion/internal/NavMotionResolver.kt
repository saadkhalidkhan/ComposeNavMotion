package com.composenavmotion.internal

import android.content.ContentResolver
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.composenavmotion.NavMotionConfig
import com.composenavmotion.NavMotionSpec

@Composable
internal fun SyncNavMotionAnimationState(
    defaultAnimation: NavMotionSpec,
    config: NavMotionConfig,
) {
    val context = LocalContext.current
    val animatorScale = remember(context) {
        readAnimatorDurationScale(context.contentResolver)
    }

    SideEffect {
        NavMotionAnimationState.hostDefaultAnimation = defaultAnimation
        NavMotionAnimationState.config = config
        NavMotionAnimationState.animatorDurationScale = animatorScale
    }
}

internal fun readAnimatorDurationScale(contentResolver: ContentResolver): Float =
    Settings.Global.getFloat(
        contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE,
        1f,
    )
