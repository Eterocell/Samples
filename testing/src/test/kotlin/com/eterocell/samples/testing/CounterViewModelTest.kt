package com.eterocell.samples.testing

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CounterViewModelTest {

    private lateinit var viewModel: CounterViewModel

    @Before
    fun setup() {
        viewModel = CounterViewModel()
    }

    @Test
    fun testIncrement() = runTest {
        viewModel.increment()
        val result = viewModel.count.first()
        assertEquals(1, result)
    }
}
