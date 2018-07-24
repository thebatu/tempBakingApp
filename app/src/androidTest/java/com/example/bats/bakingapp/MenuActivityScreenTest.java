package com.example.bats.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bats.bakingapp.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MenuActivityScreenTest {

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerView_OpensDetailsActivity(){

        //check if recipe Nutella Pie is present
        onView(withId(R.id.recipe_card_view))
                .check(matches(hasDescendant(withText("Nutella Pie"))));

        //perform click on nutella pie
        onView(withId(R.id.recipe_card_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //check if tabs view exists(tabs view consists of ingredient list tab and recipe tab list)
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));

    }









}
