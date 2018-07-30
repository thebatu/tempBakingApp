package com.example.bats.bakingapp;


import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.bats.bakingapp.Activities.MainActivity;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.exoplayer2.ExoPlayer.STATE_BUFFERING;
import static com.google.android.exoplayer2.ExoPlayer.STATE_READY;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class Tablet_RecipeStepsTest {

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
    public void checkIfRecipeExistsAndVideoPlays() {

        //perform click on nutella pie
        onView(withId(R.id.recipe_card_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        SystemClock.sleep(800); // Wait a little until the content is loaded

        //check if recipe instructions exists
        Espresso.onView(ViewMatchers.withId(R.id.recipe_ingredients_step_desc))
                .check(matches(hasSibling(withId(R.id.recipe_step_instructions))));

        //click on recipe.
        onView(withId(R.id.recipe_step_instructions)).perform(scrollTo(), click());

        //check if video is playing
        onView(allOf(instanceOf(SimpleExoPlayerView.class), withId(R.id.simple_exo_player))).check(new VideoPlaybackAssertion(true));

    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


    /**
     * class to assert if video is playing.
     */
    class VideoPlaybackAssertion implements ViewAssertion {

        private final Matcher<Boolean> matcher;

        //Constructor
        public VideoPlaybackAssertion(Matcher<Boolean> matcher) {
            this.matcher = matcher;
        }

        //Sets the Assertion's matcher to the expected playbck state.
        public VideoPlaybackAssertion(Boolean expectedState) {
            this.matcher = is(expectedState);
        }

        //Method to check if the video is playing.
        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) view;
            SimpleExoPlayer exoPlayer = exoPlayerView.getPlayer();
            int state = exoPlayer.getPlaybackState();
            Boolean isPlaying;
            isPlaying = (state == STATE_BUFFERING) || (state == STATE_READY);
            assertThat(isPlaying, matcher);
        }
    }


}