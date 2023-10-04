package com.example.storyintermediate.view.login

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.storyintermediate.BuildConfig
import com.example.storyintermediate.EspressoIdlingResource
import com.example.storyintermediate.R
import com.example.storyintermediate.factory.UserModelFactory
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private val EMAILTEST = BuildConfig.TEST_EMAIL
    private val PASSTEST = BuildConfig.TEST_PASS

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        val scenario = activityRule.scenario
        scenario.onActivity { activity ->
            viewModel = ViewModelProvider(
                activity,
                UserModelFactory.getInstance(activity)
            ).get(LoginViewModel::class.java)
            viewModel.isLoading.observeForever { isLoading ->
                if (isLoading) {
                    EspressoIdlingResource.increment()
                } else {
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun testLoginSuccess() {
        onView(withId(R.id.emailEditText)).perform(
            typeText(EMAILTEST),
            closeSoftKeyboard()
        )
        onView(withId(R.id.passwordEditText)).perform(typeText(PASSTEST), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun testLoginFailure() {
        onView(withId(R.id.emailEditText)).perform(
            typeText("emailsalah@gmail.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.passwordEditText)).perform(
            typeText("passwordsalah"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.loginButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.warningText)).check(matches(isDisplayed()))
    }


    @Test
    fun testWrongEmailFormat() {
        onView(withId(R.id.emailEditText)).perform(
            typeText("emailsalah@g"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.passwordEditText)).perform(
            typeText("passwordsalah"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.loginButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.warningText)).check(matches(isDisplayed()))
    }


    @Test
    fun testWrongPasswordFormat() {
        onView(withId(R.id.emailEditText)).perform(
            typeText("emailsalah@gmail.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.passwordEditText)).perform(
            typeText("pas"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.loginButton)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.warningText)).check(matches(isDisplayed()))

    }
}
