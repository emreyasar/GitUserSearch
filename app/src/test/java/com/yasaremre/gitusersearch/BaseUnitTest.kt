package com.yasaremre.gitusersearch

import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutinesRule = TestCoroutinesRule()

    @Before
    open fun setUp() {

    }

    @After
    open fun tearDown() {
        unmockkAll()
    }
}