package com.example.session2

import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.filters.LargeTest
import com.example.session2.view.LogInFragment
import com.example.session2.view.SignUpFragment
import org.hamcrest.Matchers.allOf
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    //добавь а то работать не будет
    @JvmField
    @get:Rule
    val scenario = launchFragmentInContainer<SignUpFragment>()
    @Test
    fun logInFragment() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayoutCompat::class.java)) ,
                withText("Create an account")
        )).check(matches(isDisplayed()))
//        onView(
//            withId(R.id.btn_signUp)
//        ).perform(scrollTo(), click())
//        // Create a TestNavHostController
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext())
//        // Create a graphical FragmentScenario for the TitleScreen
//        val titleScenario = launchFragmentInContainer<SignUpFragment>()
    }
}