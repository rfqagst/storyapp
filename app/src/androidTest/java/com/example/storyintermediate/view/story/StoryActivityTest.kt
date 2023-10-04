package com.example.storyintermediate.view.story

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.storyintermediate.EspressoIdlingResource
import com.example.storyintermediate.R
import com.example.storyintermediate.factory.StoryModelFactory
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoryActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(StoryActivity::class.java)
    private lateinit var viewModel: StoryViewModel

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        val scenario = activityRule.scenario
        scenario.onActivity { activity ->
            viewModel = ViewModelProvider(
                activity,
                StoryModelFactory.getInstance(activity)
            ).get(StoryViewModel::class.java)
        }
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }


    @Test
    fun testLogout() {
//        Espresso.openActionBarOverflowOrOptionsMenu(activityRule.scenario.state)
        Espresso.onView(ViewMatchers.withId(R.id.menu_logout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
    }
}