package com.yasaremre.gitusersearch

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class TestCoroutinesRule: TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description?) {
        super.starting(description)
        TestCoroutines.setup()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        TestCoroutines.teardown()
    }
}