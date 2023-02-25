package nwmsu.android.nursingsymposium;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;

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
}
