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

public class Favorite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_aboutus){
            Intent myIntent = new Intent(Favorite.this, MainActivity.class);
            startActivity(myIntent);
            return true;
        }

        else if (id == R.id.nav_discover){
            Intent myIntent = new Intent(Favorite.this, MainPage.class);
            startActivity(myIntent);
            return true;
        }

        else if (id == R.id.nav_favourite){
            Intent myIntent = new Intent(Favorite.this, Favorite.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}