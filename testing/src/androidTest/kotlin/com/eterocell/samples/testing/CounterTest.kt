package com.eterocell.samples.testing

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonClickIncrementsCount() {
        composeTestRule.setContent {
            CounterScreen()
        }

        composeTestRule.onNodeWithTag("incrementButton").performClick()
        composeTestRule.onNodeWithTag("countText").assertTextContains("Count: 1")
    }
}
