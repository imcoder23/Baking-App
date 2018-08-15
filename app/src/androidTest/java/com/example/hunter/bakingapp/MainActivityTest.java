package com.example.hunter.bakingapp;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> MainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void scrollToPosition() throws Exception {
        onView(ViewMatchers.withId(R.id.rv_mainlayout))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(3, ViewActions.click()));
    }


}
