package com.example.git_repo_app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test

class StartActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<StartActivity>
            = ActivityScenarioRule(StartActivity::class.java)
    @Test
    fun onCreateLanguageButtonsTest() {

        onView(withId(R.id.cplusplus_button)).check(matches(isEnabled()))
        onView(withId(R.id.R_button)).check(matches(isEnabled()))
        onView(withId(R.id.java_button)).check(matches(isEnabled()))
        onView(withId(R.id.kotlin_button)).check(matches(isEnabled()))
        onView(withId(R.id.python_button)).check(matches(isEnabled()))

        onView(withId(R.id.R_button)).perform(click())
        onView(withId(R.id.R_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.cplusplus_button)).check(matches(isEnabled()))
        onView(withId(R.id.cplusplus_button)).perform(click())

        onView(withId(R.id.cplusplus_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.R_button)).check(matches(isEnabled()))
        onView(withId(R.id.python_button)).check(matches(isEnabled()))
        onView(withId(R.id.java_button)).check(matches(isEnabled()))
        onView(withId(R.id.kotlin_button)).check(matches(isEnabled()))

        onView(withId(R.id.python_button)).perform(click())
        onView(withId(R.id.python_button)).perform(click())
        onView(withId(R.id.python_button)).perform(click())

        onView(withId(R.id.python_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.R_button)).check(matches(isEnabled()))
        onView(withId(R.id.cplusplus_button)).check(matches(isEnabled()))
        onView(withId(R.id.java_button)).check(matches(isEnabled()))
        onView(withId(R.id.kotlin_button)).check(matches(isEnabled()))

    }

    @Test
    fun onCreateOtherViews() {

        onView(withId(R.id.choose_lang_string)).check(matches(withText(R.string.language_choice)))
        onView(withId(R.id.edit_search)).check(matches(withHint(R.string.hint_search_string)))
        onView(withId(R.id.edit_search)).perform(typeText("star&!+?***top&**&???"))
        onView(withId(R.id.edit_search)).check(matches(withText("star&!+?***top&**&???")))
        onView(withId(R.id.btn_search)).check(matches(isClickable()))
        onView(withId(R.id.btn_search)).perform(click())
        onView(withId(R.id.repoList)).check(matches(isDisplayed()))
    }
}