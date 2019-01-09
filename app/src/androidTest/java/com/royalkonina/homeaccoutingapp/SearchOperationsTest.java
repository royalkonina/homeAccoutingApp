package com.royalkonina.homeaccoutingapp;

import com.royalkonina.homeaccoutingapp.entities.OperationType;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SearchOperationsTest extends BaseNavigationTest {

    private static final String FIRST_OPERATION_DESCRIPTION = "first operation";
    private static final String SECOND_OPERATION_DESCRIPTION = "second operation";

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

        onView(withText("first")).perform(click());
        onView(withText(R.string.add_operation)).perform(click());
        onView(withId(R.id.spinner_operation_type)).perform(click());
        onView(withText(OperationType.INCOMING.name())).perform(click());
        onView(withId(R.id.edit_balance)).perform(typeText("50"));
        onView(withId(R.id.edit_description)).perform(typeText(FIRST_OPERATION_DESCRIPTION));
        onView(withId(R.id.btn_add)).perform(click());

        onView(withText(R.string.add_operation)).perform(click());
        onView(withId(R.id.spinner_operation_type)).perform(click());
        onView(withText(OperationType.INCOMING.name())).perform(click());
        onView(withId(R.id.edit_balance)).perform(typeText("60"));
        onView(withId(R.id.edit_description)).perform(typeText(SECOND_OPERATION_DESCRIPTION));
        onView(withId(R.id.btn_add)).perform(click());


        onView(withId(R.id.edit_search)).perform(typeText("rst"));
        onView(withId(R.id.edit_search)).perform(closeSoftKeyboard());
        onView(withText(FIRST_OPERATION_DESCRIPTION)).check(matches(isDisplayed()));
        onView(withText(SECOND_OPERATION_DESCRIPTION)).check(doesNotExist());

        onView(withId(R.id.edit_search)).perform(clearText());

        onView(withId(R.id.edit_search)).perform(typeText("eco"));
        onView(withId(R.id.edit_search)).perform(closeSoftKeyboard());
        onView(withText(FIRST_OPERATION_DESCRIPTION)).check(doesNotExist());
        onView(withText(SECOND_OPERATION_DESCRIPTION)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_search)).perform(clearText());

        onView(withId(R.id.edit_search)).perform(typeText("oper"));
        onView(withId(R.id.edit_search)).perform(closeSoftKeyboard());
        onView(withText(FIRST_OPERATION_DESCRIPTION)).check(matches(isDisplayed()));
        onView(withText(SECOND_OPERATION_DESCRIPTION)).check(matches(isDisplayed()));

    }
}
