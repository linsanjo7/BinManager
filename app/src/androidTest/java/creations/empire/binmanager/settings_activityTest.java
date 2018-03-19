package creations.empire.binmanager;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class settings_activityTest {

    @Rule
    public ActivityTestRule<settings_activity> mActivityTestRule = new ActivityTestRule<>(settings_activity.class);

    @Test
    public void settings_activityTest() {
        ViewInteraction button = onView(allOf(withId(R.id.button3), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editText3), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.editText3), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.editText3), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        appCompatEditText3.perform(replaceText("103.35.198.94"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(allOf(withId(R.id.editText4), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1), isDisplayed()));
        appCompatEditText4.perform(replaceText("7090"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.button3), withText("Connect"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatAutoCompleteTextView = onView(allOf(withId(R.id.email), childAtPosition(childAtPosition(withClassName(is("android.support.design.widget.TextInputLayout")), 0), 0)));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("madhu@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.password), childAtPosition(childAtPosition(withClassName(is("android.support.design.widget.TextInputLayout")), 0), 0)));
        appCompatEditText5.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.email_sign_in_button), withText("Login"), childAtPosition(allOf(withId(R.id.email_login_form), childAtPosition(withId(R.id.login_form), 0)), 2)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.button2), withText("My tasks"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        appCompatButton3.perform(click());

        DataInteraction appCompatTextView = onData(anything()).inAdapterView(allOf(withId(R.id.listView), childAtPosition(withClassName(is("android.widget.RelativeLayout")), 1))).atPosition(1);
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button2 = onView(allOf(withId(R.id.button8), withText("Navigate"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1), isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(allOf(withId(R.id.button9), withText("mark complete"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()));
        button3.perform(click());

        pressBack();

        pressBack();

        ViewInteraction appCompatButton4 = onView(allOf(withId(R.id.button6), withText("My Areas"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 5), isDisplayed()));
        appCompatButton4.perform(click());

        pressBack();

        ViewInteraction appCompatButton5 = onView(allOf(withId(R.id.button4), withText("My profile"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 4), isDisplayed()));
        appCompatButton5.perform(click());

        pressBack();

        ViewInteraction appCompatButton6 = onView(allOf(withId(R.id.button7), withText("  History  "), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 6), isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();

        pressBack();

        pressBack();

    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
