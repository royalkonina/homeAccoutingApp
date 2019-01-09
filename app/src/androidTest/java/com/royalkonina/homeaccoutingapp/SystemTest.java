package com.royalkonina.homeaccoutingapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SystemTest extends BaseNavigationTest {
    private static final String APPLICATION_PACKAGE = "com.royalkonina.homeaccoutingapp";
    private static final int LAUNCH_TIMEOUT = 5000;

    @Override
    public void navigateToTestStartDestination() {
        onView(withId(R.id.fab_add)).perform(click());
    }

    @Override
    public void testDestination() throws RemoteException {
        onView(withId(R.id.edit_name)).perform(typeText("first"));
        onView(withId(R.id.edit_description)).perform(typeText("firstDesc"));
        onView(withId(R.id.edit_balance)).perform(typeText("60"));

        onView(withId(R.id.btn_add)).perform(click());

        onView(withText(R.string.accounts)).check(matches(isDisplayed()));

        UiDevice device = UiDevice.getInstance(getInstrumentation());

        device.setOrientationLeft();

        onView(withText("first")).check(matches(isDisplayed()));
        onView(withText("firstDesc")).check(matches(isDisplayed()));
        onView(withText("60")).check(matches(isDisplayed()));

        device.setOrientationRight();

        onView(withText("first")).check(matches(isDisplayed()));
        onView(withText("firstDesc")).check(matches(isDisplayed()));
        onView(withText("60")).check(matches(isDisplayed()));

        device.setOrientationNatural();

        onView(withText("first")).check(matches(isDisplayed()));
        onView(withText("firstDesc")).check(matches(isDisplayed()));
        onView(withText("60")).check(matches(isDisplayed()));

        pressHome(device);

        onView(withText("first")).check(matches(isDisplayed()));
        onView(withText("firstDesc")).check(matches(isDisplayed()));
        onView(withText("60")).check(matches(isDisplayed()));


    }

    public void pressHome(UiDevice device) {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(APPLICATION_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(APPLICATION_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
