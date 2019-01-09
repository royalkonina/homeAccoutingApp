package com.royalkonina.homeaccoutingapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class AddRemoveAccountTest extends BaseNavigationTest {

    @Override
    public void navigateToTestStartDestination() {
        onView(withId(R.id.fab_add)).perform(click());
    }

    @Override
    public void testDestination() {
        onView(withId(R.id.edit_name)).perform(typeText("first"));
        onView(withId(R.id.edit_description)).perform(typeText("firstDesc"));
        onView(withId(R.id.edit_balance)).perform(typeText("60"));

        onView(withId(R.id.btn_add)).perform(click());

        onView(withText(R.string.accounts)).check(matches(isDisplayed()));
        onView(withText("first")).check(matches(isDisplayed()));
        onView(withText("firstDesc")).check(matches(isDisplayed()));
        onView(withText("60")).check(matches(isDisplayed()));

        onView(withText("first")).perform(click());
        onView(withText(R.string.remove_account)).perform(click());

        onView(withText(R.string.accounts)).check(matches(isDisplayed()));

        onView(withText("first")).check(doesNotExist());
        onView(withText("firstDesc")).check(doesNotExist());
        onView(withText("60")).check(doesNotExist());
    }
}
