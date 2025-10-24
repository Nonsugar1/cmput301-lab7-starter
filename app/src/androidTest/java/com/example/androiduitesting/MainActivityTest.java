package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI tests for CMPUT 301 Lab 7.
 * Notes:
 * - Use onData(...) for ListView items (adapter-backed).
 * - Always closeSoftKeyboard() after typeText(...) for stability.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // -----------------------------
    // Original 3 tests (lightly fixed)
    // -----------------------------

    /** Add one city and verify it appears in the ListView (adapter data). */
    @Test
    public void testAddCity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Use onData to assert adapter contains "Edmonton"
        onData(is("Edmonton"))
                .inAdapterView(withId(R.id.city_list))
                .check(matches(isDisplayed()));
    }

    /** Add two cities, clear all, and verify both texts are gone. */
    @Test
    public void testClearCity() {
        // Add "Edmonton"
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Add "Vancouver"
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear all
        onView(withId(R.id.button_clear)).perform(click());

        // After clearing, no view with those texts should exist
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    /** Add one city and verify the first row text equals that city. */
    @Test
    public void testListView() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        onData(instanceOf(String.class))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText("Edmonton")));
    }

    // -----------------------------
    // New 3 tests for ShowActivity
    // -----------------------------

    /** Check whether tapping a list item switches to ShowActivity. */
    @Test
    public void testSwitchToShowActivity() {
        // Prepare data
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Start intercepting intents BEFORE clicking list item
        init();
        onData(is("Edmonton")).inAdapterView(withId(R.id.city_list)).perform(click());
        intended(hasComponent(ShowActivity.class.getName()));
        release();
    }

    /** Test whether the city name shown in ShowActivity is consistent with the clicked one. */
    @Test
    public void testCityNameConsistent() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        onData(is("Vancouver")).inAdapterView(withId(R.id.city_list)).perform(click());
        onView(withId(R.id.text_city))
                .check(matches(isDisplayed()))
                .check(matches(withText("Vancouver")));
    }

    /** Test the BACK button in ShowActivity returns to MainActivity. */
    @Test
    public void testBackButton() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        onData(is("Calgary")).inAdapterView(withId(R.id.city_list)).perform(click());
        onView(withId(R.id.button_back)).perform(click());

        // Should be back in MainActivity
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
