package com.example.kotlinpractical15

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.GestureScope
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.center
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTextExactly
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.unit.Dp
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.runBlocking

//Exercise 1: Testing Text display
class TextDisplayTest {

    @get:Rule
    val activityRule = ActivityTestRule(TextView::class.java, false, false)

    @Test
    fun textIsCorrectlyDisplayed() {

        activityRule.launchActivity(Intent())

        onView(withId(R.id.text_view))
            .check(matches(withText("Hello, Espresso!")))

    }

}


//Exercise 2: Testing Button Click Behaviour
class ButtonClickTest {
    @get:Rule
    val activityRule = ActivityTestRule(ClickableButton::class.java, false, false)

    @Test
    fun buttonClickChangesText() {

        activityRule.launchActivity(Intent())

        onView(withId(R.id.button_click))
            .perform(click())

        onView(withId(R.id.text_result))
            .check(matches(withText("Button Clicked")))

    }
}


//Exercise 3: Verifying Text Display
class WelcomeTextComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textCorrectlyDisplayed() {
        composeTestRule.setContent {
            Text("Welcome to Compose!")
        }
        composeTestRule
            .onNodeWithText("Welcome to Compose!")
            .assertIsDisplayed()
    }

}

//Exercise 4: Testing button click
class ClickableTextComposableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textChangeOnClick() {
        composeTestRule.setContent {
            var text by remember {mutableStateOf("Not Clicked")}

            Column {
                Button(onClick = { text = "Clicked" }) {
                    Text(text = "Click me!")
                }

                Text(text = text)
            }
        }
        composeTestRule
            .onNodeWithText("Click me!")
            .performClick()

        composeTestRule
            .onNodeWithText("Clicked")
            .assertIsDisplayed()
    }
}

//Exercise 5: Testing TextField Input
class InputFieldComposableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textFieldCorrectlyDisplayed(){
        composeTestRule.setContent {
            var text by remember { mutableStateOf("") }
            Column {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter text") }
                )
                Text("You entered: $text")
            }
        }

        composeTestRule
            .onNodeWithText("Enter text")
            .performTextInput("example text!@#Y")

        composeTestRule
            .onNodeWithText("You entered: example text!@#Y")
            .assertIsDisplayed()

    }

}

//Exercise 6: Testing LazyColumn Scroll
class LazyColumnScrollTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun setUpLazyColumn () {
        composeTestRule.setContent {
            LazyColumn {
                items(100) { index ->
                    Text("Item $index")
                } }
        }
    }

    @Test
    fun item50VisibleAfterScrolling() {

        setUpLazyColumn()

        composeTestRule
            .onNode(hasScrollAction())
            .performScrollToIndex(50)

        composeTestRule
            .onNodeWithText("Item 50")
            .assertIsDisplayed()

    }

    @Test
    fun item99VisibleAfterScrolling() {

        // edge case

        setUpLazyColumn()

        composeTestRule
            .onNode(hasScrollAction())
            .performScrollToIndex(99)

        composeTestRule
            .onNodeWithText("Item 99")
            .assertIsDisplayed()

    }

}



//Exercise 7: Testing toggle state

class ToggleSwitchComposableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textChangeOnSwitchToggle() {
        composeTestRule.setContent {
            var isChecked by remember { mutableStateOf(false) }
            Column {
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                Text(if (isChecked) "ON" else "OFF")
            }
        }

        composeTestRule
            .onNode(hasClickAction())
            .performClick()

        composeTestRule
            .onNodeWithText("ON")
            .assertIsDisplayed()

    }

}
