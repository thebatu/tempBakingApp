package com.example.bats.bakingapp;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.bats.bakingapp.Activities.MainActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class Phone_RecipeStepsTest {

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }


    @Test
    public void clickRecyclerViewMainActivity_OpensDetailsActivityTabs() {

        //check if recipe Nutella Pie is present
        onView(withId(R.id.recipe_card_view))
                .check(matches(hasDescendant(withText("Nutella Pie"))));

    }
    @Test
    public void checkIfRecipeExistsAndPerformClickOnIt() {

        //perform click on nutella pie
        onView(withId(R.id.recipe_card_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //check if tabs view exists(tabs view consists of ingredient list tab and recipe tab list)
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));


                //click on STEPS tab
        Matcher<View> matcher = allOf(withText("STEPS"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800); // Wait a little until the content is loaded


        //check if steps are displayed
        onView(withId(R.id.steps_recycler)).check(matches(isDisplayed()));

        //click on the first step
        onView(withId(R.id.steps_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(1200); // Wait a little until the content is loaded

        //check if exoplayer exists
        onView(allOf(instanceOf(SimpleExoPlayerView.class), withId(R.id.simple_exo_player))).check(matches(isDisplayed()));

    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }




}
