package com.nwmissouri.edu.conferencescheduler;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.nwmissouri.edu.conferencescheduler.activities.LoginActivity;

public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);
    private View decorView;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<LoginActivity>() {
            @Override
            public void perform(LoginActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    private void checkToastDisplayed(View dView, String toastMsg) {
        onView(withText(toastMsg))
                .inRoot(withDecorView(Matchers.not(dView)))
                .check(matches(isDisplayed()));
    }

    //Test for name validation Toast
    @Test
    public void testLoginWelcomeToast() {
        String welcomeMsg = "";
        // When "Login" button is clicked
        onView(withId(R.id.login_btn)).perform(click());
        // Check to see if Toast is displayed after the click action
        checkToastDisplayed(decorView, welcomeMsg);
    }

    //Test for invalid username Toast
    @Test
    public void testInvalidUsernameToast() {
        String userErrorString = "Invalid Username";
        // When "Login" button is clicked
        onView(withId(R.id.login_btn)).perform(click());
        // Check to see if Toast is displayed after the click action
        checkToastDisplayed(decorView, userErrorString);
    }

    //Test for invalid username Toast
    @Test
    public void testInvalidPasswordToast() {
        String userErrorString = "Invalid Password";
        // When "Login" button is clicked
        onView(withId(R.id.login_btn)).perform(click());
        // Check to see if Toast is displayed after the click action
        checkToastDisplayed(decorView, userErrorString);
    }
}
