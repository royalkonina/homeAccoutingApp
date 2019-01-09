package com.royalkonina.homeaccoutingapp;

import com.royalkonina.homeaccoutingapp.entities.OperationType;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class AddRemoveOperationsTest extends BaseNavigationTest {

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
        onView(withText(R.string.add_operation)).perform(click());
        onView(withId(R.id.spinner_operation_type)).perform(click());
        onView(withText(OperationType.INCOMING.name())).perform(click());

        onView(withText(R.string.destination)).check(matches(isDisplayed()));
        onView(withText(R.string.source)).check(matches(not(isDisplayed())));

        onView(withId(R.id.edit_balance)).perform(typeText("50"));
        onView(withId(R.id.btn_add)).perform(click());

        onView(withText(R.string.account_info)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_balance)).check(matches(withText("110")));
        onView(withId(R.id.tv_value_balance)).check(matches(withText("50")));

        onView(withId(R.id.tv_value_balance)).perform(longClick());

        onView(withId(R.id.tv_balance)).check(matches(withText("60")));
        onView(withId(R.id.tv_value_balance)).check(doesNotExist());
    }
}
