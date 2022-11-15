//package com.example.phi_nhai_dai;
//
//import androidx.annotation.NonNull;
//        import androidx.appcompat.app.ActionBarDrawerToggle;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.drawerlayout.widget.DrawerLayout;
//        import android.os.Bundle;
//        import android.view.MenuItem;
//        import android.content.Intent;
//
//public class Discover extends AppCompatActivity {
//
//    public DrawerLayout drawerLayout;
//    public ActionBarDrawerToggle actionBarDrawerToggle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.card_description_relative);
//
//        // drawer layout instance to toggle the menu icon to open
//        // drawer and back button to close drawer
//        drawerLayout = findViewById(R.id.my_drawer_layout);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
//
//        // pass the Open and Close toggle for the drawer layout listener
//        // to toggle the button
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        // to make the Navigation drawer icon always appear on the action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//
//    // override the onOptionsItemSelected()
//    // function to implement
//    // the item click listener callback
//    // to open and close the navigation
//    // drawer when the icon is clicked
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.nav_aboutus){
//            Intent myIntent = new Intent(Discover.this, Favorite.class);
//            startActivity(myIntent);
//            return true;
//        }
//
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
package com.example.phi_nhai_dai;

import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.phi_nhai_dai.Fav.Favorite;

public class Discover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_description_relative);
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
            Intent myIntent = new Intent(Discover.this, MainActivity.class);
            startActivity(myIntent);
            return true;
        }

        else if (id == R.id.nav_discover){
            Intent myIntent = new Intent(Discover.this, Discover.class);
            startActivity(myIntent);
            return true;
        }

        else if (id == R.id.nav_favourite){
            Intent myIntent = new Intent(Discover.this, Favorite.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}