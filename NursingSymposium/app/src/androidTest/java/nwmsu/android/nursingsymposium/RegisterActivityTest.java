package nwmsu.android.nursingsymposium;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterActivityTest {

    @Rule
    public ActivityScenarioRule<com.example.nursingsymposium.RegisterActivity> activityRule =
            new ActivityScenarioRule<>(com.example.nursingsymposium.RegisterActivity.class);
    private View decorView;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<com.example.nursingsymposium.RegisterActivity>() {
            @Override
            public void perform(com.example.nursingsymposium.RegisterActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });
    }

    //Test for name validation Toast
    @Test
    public void testNameValidationToast() {
        // The user has provided everything except name to registerActivity

        // Need to simulate input into the activity here
        // - Enter everything EXCEPT the name. We'll clear it in case
        onView(withId(R.id.id_name)).perform(clearText());
        onView(withId(R.id.id_username)).perform(clearText(), typeText("testUser"));
        onView(withId(R.id.id_password)).perform(clearText(), typeText("myPW"));
        onView(withId(R.id.id_confirmpassword)).perform(clearText(), typeText("myPW"));

        // When "Register" button is clicked
        onView(withId(R.id.register_btn)).perform(click());
        // Check to see if Toast is displayed after the click action
        // - Should we define these toasts as constants in the activity?
        onView(withText("Enter name"))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }
}
