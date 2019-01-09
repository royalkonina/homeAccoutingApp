package com.royalkonina.homeaccoutingapp;

import android.os.RemoteException;

import com.royalkonina.homeaccoutingapp.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


@RunWith(AndroidJUnit4.class)
abstract class BaseNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        navigateToTestStartDestination();
    }

    public abstract void navigateToTestStartDestination();

    @Test
    public abstract void testDestination() throws RemoteException;
}
