package com.fofxlabs.fetchassessment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fofxlabs.fetchassessment.dagger.RepositoryModule
import com.fofxlabs.fetchassessment.data.DataRepository
import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.ui.screens.ListScreenContent
import com.fofxlabs.fetchassessment.ui.screens.ListUiState
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun exampleTest() = runTest {

        composeTestRule.setContent {
            ListScreenContent(
                isLoading = false,
                viewModel = hiltViewModel(),
                uiState = ListUiState(items =  listOf(
                    Item(id = 1, listId = 1, "Item 1"),
                    Item(id = 2, listId = 1, "Item 2"),
                ))
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithText("name: Item 1")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithText("name: Item 2")
            .assertCountEquals(1)
    }
}