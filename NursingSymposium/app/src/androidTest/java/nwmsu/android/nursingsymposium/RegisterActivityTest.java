package nwmsu.android.nursingsymposium;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

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

    private void checkToastDisplayed(View dView, String toastMsg) {
        onView(withText(toastMsg))
                .inRoot(withDecorView(Matchers.not(dView)))
                .check(matches(isDisplayed()));
    }

    private void checkToastNotDisplayed(View dView, String toastMsg) {
        onView(withText(toastMsg))
                .inRoot(withDecorView(Matchers.not(dView)))
                .check(matches(not(isDisplayed())));
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
        checkToastDisplayed(decorView, "Enter name");
    }

    //Test for username validation Toast
    @Test
    public void testUsernameValidationToast() {
        onView(withId(R.id.id_name)).perform(clearText(), typeText("Test Name"));
        onView(withId(R.id.id_username)).perform(clearText());
        onView(withId(R.id.id_password)).perform(clearText(), typeText("myPW"));
        onView(withId(R.id.id_confirmpassword)).perform(clearText(), typeText("myPW"));

        // When "Register" button is clicked check for appropriate toast
        onView(withId(R.id.register_btn)).perform(click());
        checkToastDisplayed(decorView, "Enter Username");
    }

    //Test for password validation Toast
    @Test
    public void testPasswordValidationToast() {
        onView(withId(R.id.id_name)).perform(clearText(), typeText("Test Name"));
        onView(withId(R.id.id_username)).perform(clearText(), typeText("testUser"));
        onView(withId(R.id.id_password)).perform(clearText());
        onView(withId(R.id.id_confirmpassword)).perform(clearText(), typeText("myPW"));

        // When "Register" button is clicked check for appropriate toast
        onView(withId(R.id.register_btn)).perform(click());
        checkToastDisplayed(decorView, "Enter password");
    }

    //Test for password confirmation validation Toast
    @Test
    public void testPasswordConfirmationValidationToast() {
        onView(withId(R.id.id_name)).perform(clearText(), typeText("Test Name"));
        onView(withId(R.id.id_username)).perform(clearText(), typeText("testUser"));
        onView(withId(R.id.id_password)).perform(clearText(), typeText("myPW"));
        onView(withId(R.id.id_confirmpassword)).perform(clearText());

        // When "Register" button is clicked check for appropriate toast
        onView(withId(R.id.register_btn)).perform(click());
        checkToastDisplayed(decorView, "Enter confirmpassword");
    }

    //Test for a mismatch Toast when both passwords are different
    @Test
    public void testPasswordMismatchToast() {
        onView(withId(R.id.id_name)).perform(clearText(), typeText("Test Name"));
        onView(withId(R.id.id_username)).perform(clearText(), typeText("testUser"));
        onView(withId(R.id.id_password)).perform(clearText(), typeText("myPW"));
        onView(withId(R.id.id_confirmpassword)).perform(clearText(), typeText("wrongPassword"));

        // Check that the password mismatch is displayed
        checkToastDisplayed(decorView, "Password Mismatch");
    }

    //Test for all validation checks passed Toast
    // - Might want to refactor to see if we can database assert as well
    @Test
    public void testRegistrationSuccessToast() {
        onView(withId(R.id.id_name)).perform(clearText(), typeText("Test Name"));
        onView(withId(R.id.id_username)).perform(clearText(), typeText("testUser"));
        onView(withId(R.id.id_password)).perform(clearText(), typeText("myPW"));
        onView(withId(R.id.id_confirmpassword)).perform(clearText(), typeText("myPW"));

        onView(withId(R.id.register_btn)).perform(click());
        checkToastDisplayed(decorView, "Registration Succesfull");
        checkToastNotDisplayed(decorView, "Registration UnSuccessfull");
        checkToastNotDisplayed(decorView, "Password Mismatch");
    }
}
