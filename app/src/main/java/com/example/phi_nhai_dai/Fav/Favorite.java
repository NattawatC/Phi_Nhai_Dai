package com.example.phi_nhai_dai.Fav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.phi_nhai_dai.Discover;
import com.example.phi_nhai_dai.MainActivity;
import com.example.phi_nhai_dai.R;
import com.example.phi_nhai_dai.main_page.MainPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Favorite extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().hide();

        // Navigation Settings
        bottomNavigationView = findViewById(R.id.dock_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_favourite);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int currentItem = item.getItemId();
            if (currentItem == R.id.nav_favourite) {
                return true;
            } else if (currentItem == R.id.nav_aboutus) {
                Intent myIntent = new Intent(Favorite.this, MainActivity.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (currentItem == R.id.nav_discover) {
                Intent myIntent = new Intent(Favorite.this, MainPage.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else {
                System.out.println("Not implemented");
                return false;
            }
        });
    }

}