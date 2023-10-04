package com.example.storyintermediate.view.login

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.storyintermediate.R
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun testLoginSuccess() {
        // Input email dan password
        onView(withId(R.id.emailEditText)).perform(typeText("user@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("password123"), closeSoftKeyboard())

        // Klik tombol login
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.successMessage)).check(matches(isDisplayed()))
    }

    // Anda bisa menambahkan lebih banyak tes untuk skenario lain (misalnya, login gagal atau logout)
}
