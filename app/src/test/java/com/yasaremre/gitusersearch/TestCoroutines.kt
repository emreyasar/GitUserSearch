package com.yasaremre.gitusersearch

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

object TestCoroutines {

    @ExperimentalCoroutinesApi
    private val testIODispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testMainDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    fun setup() {
        Dispatchers.setMain(testMainDispatcher)
        mockkObject(com.yasaremre.gitusersearch.core.Dispatchers)
        every { com.yasaremre.gitusersearch.core.Dispatchers.main() } returns testMainDispatcher
        every { com.yasaremre.gitusersearch.core.Dispatchers.io() } returns testIODispatcher
    }

    @ExperimentalCoroutinesApi
    fun teardown() {
        Dispatchers.resetMain()
        testIODispatcher.cleanupTestCoroutines()
        testMainDispatcher.cleanupTestCoroutines()
        unmockkObject(com.yasaremre.gitusersearch.core.Dispatchers)
    }
}