package com.royalkonina.homeaccoutingapp.ui;


import android.os.Bundle;

import com.royalkonina.homeaccoutingapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavController navController = findNavController(this, R.id.nav_host_fragment);
        setupActionBarWithNavController(
                this,
                navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

}