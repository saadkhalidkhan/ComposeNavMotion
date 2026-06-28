package com.composenavmotion.shared

import org.junit.Test

/**
 * Compile-time smoke test for the shared-element wrapper API.
 */
class SharedNavElementCompileTest {
    @Test
    fun sharedNavScope_interfaceExists() {
        val scope = SharedNavScopeImpl(isAvailable = false)
        assert(!scope.isAvailable)
    }
}
