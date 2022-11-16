package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phi_nhai_dai.Fav.Favorite;
import com.example.phi_nhai_dai.MainActivity;
import com.example.phi_nhai_dai.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MainPage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ImageButton highlightsButton;
    ImageButton recipesButton;
    ImageButton todaySMealButton;

//    New Add
    // Category Selector Box
    boolean categoryBoxState = true;
    boolean categoryLoaded = false;
    GridLayout categorySelectorBox;
    TextView chooseCategoryTitle;
    ImageButton categoryDropdownArrow;
    ArrayList<TextView> categoryTextViews;
    ArrayList<AppCompatCheckBox> categoryCheckBoxes;
    TextView main_dish_text;
    TextView appetizer_text;
    TextView desserts_text;
    TextView snacks_text;
    TextView beverages_text;
    AppCompatCheckBox main_dish_checkbox;
    AppCompatCheckBox appetizer_checkbox;
    AppCompatCheckBox desserts_checkbox;
    AppCompatCheckBox snacks_checkbox;
    AppCompatCheckBox beverages_checkbox;

    // Main Grid Layout
    GridLayout parentGridLayout;
//    -------------

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_discover_page);
        getSupportActionBar().hide();
        initializeInstances();
        Database db = new Database(this);

        try {
            db.getReadableDatabase();
            db.copyDB();
        } catch (IOException ioe) {

            throw new Error("Database not created");
        }

        db.openDB();

        SQLiteDatabase db1;
        db1 = openOrCreateDatabase("place", Context.MODE_PRIVATE, null);
        Cursor c = db1.rawQuery("SELECT * FROM Places", null);
        c.moveToFirst();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Place> PlaceArrayList = new ArrayList<>();
        ArrayList<String> id, name, location, img_link;

        id = new ArrayList<>();
        name = new ArrayList<>();
        location = new ArrayList<>();
        img_link = new ArrayList<>();
        Adapter adapter = new Adapter(this, id, name, location, img_link);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        do {
            PlaceArrayList.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3)));
        } while (c.moveToNext());

        Collections.shuffle(PlaceArrayList);

        for (int i = 0; i < PlaceArrayList.size(); i++) {
            id.add(Integer.toString(PlaceArrayList.get(i).getId()));
            name.add(PlaceArrayList.get(i).getName());
            location.add(PlaceArrayList.get(i).getLocation());
            img_link.add(PlaceArrayList.get(i).getImg_link());
        }
//New Add
        // Category Dropdown Settings
        chooseCategoryTitle.setOnClickListener(this::toggleCategoryBox);
        categoryDropdownArrow.setOnClickListener(this::toggleCategoryBox);

        // Navigation Settings
        bottomNavigationView = findViewById(R.id.dock_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_discover);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int currentItem = item.getItemId();
            if (currentItem == R.id.nav_discover) {
                return true;
            }
            else if (currentItem == R.id.nav_aboutus) {
                Intent myIntent = new Intent(MainPage.this, MainActivity.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
            else if (currentItem == R.id.nav_favourite) {
                Intent myIntent = new Intent(MainPage.this, Favorite.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
            else {
                System.out.println("Not implemented");
                return false;
            }
        });
//        ----------
    }

//New Add
    private int getValueInDp(int value) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }
//    ---------

//    New Add

    private void initializeInstances() {

        // Initializing page elements and objects
        parentGridLayout            = findViewById(R.id.main_grid_layout);
        chooseCategoryTitle         = findViewById(R.id.choose_category_title);
        categorySelectorBox         = findViewById(R.id.category_selector_box);
        categoryDropdownArrow       = findViewById(R.id.category_arrow);

        // Category Dropdown Box Elements
        categoryTextViews   = new ArrayList<>();
        main_dish_text      = new TextView(this);
        appetizer_text      = new TextView(this);
        desserts_text       = new TextView(this);
        snacks_text         = new TextView(this);
        beverages_text      = new TextView(this);

        categoryCheckBoxes  = new ArrayList<>();
        main_dish_checkbox  = new AppCompatCheckBox(this);
        appetizer_checkbox  = new AppCompatCheckBox(this);
        desserts_checkbox   = new AppCompatCheckBox(this);
        snacks_checkbox     = new AppCompatCheckBox(this);
        beverages_checkbox  = new AppCompatCheckBox(this);
    }
    //    -------------

//    New Add
    // Expand and minimize category selector box
    public void toggleCategoryBox(View view) {
        if (!categoryLoaded) {
            // Loads TextViews into ArrayList
            categoryTextViews.add(main_dish_text);
            categoryTextViews.add(appetizer_text);
            categoryTextViews.add(desserts_text);
            categoryTextViews.add(snacks_text);
            categoryTextViews.add(beverages_text);

            // Loads Checkboxes to ArrayList
            categoryCheckBoxes.add(main_dish_checkbox);
            categoryCheckBoxes.add(appetizer_checkbox);
            categoryCheckBoxes.add(desserts_checkbox);
            categoryCheckBoxes.add(snacks_checkbox);
            categoryCheckBoxes.add(beverages_checkbox);

            // Category UI Elements Setup
            main_dish_text.setText(R.string.main_dish);
            appetizer_text.setText(R.string.appetizers);
            desserts_text.setText(R.string.desserts);
            snacks_text.setText(R.string.snacks);
            beverages_text.setText(R.string.beverages);

            Typeface poppins_bold = ResourcesCompat.getFont(this, R.font.poppins_bold);

            for (TextView t : categoryTextViews) {
                t.setTextSize(18);
                t.setTextColor(getResources().getColor(R.color.secondary));
                t.setPadding(getValueInDp(20), 0, getValueInDp(200), 0);
                t.setTypeface(poppins_bold);
            }

            for (AppCompatCheckBox c : categoryCheckBoxes) {
                c.setGravity(Gravity.END);
                c.setPadding(0, 0, 0, 0);
                setCheckBoxColor(c, 0xFFF3F7F0, 0xFFF3F7F0);
            }

            categoryTextViews.get(categoryTextViews.size()-1)
                    .setPadding(getValueInDp(20), 0, 0, getValueInDp(8));
            categoryCheckBoxes.get(categoryCheckBoxes.size()-1)
                    .setPadding(0, 0, getValueInDp(5), getValueInDp(8));

            categoryLoaded = true;
        }

        // Performing tasks based on CategoryBoxState boolean
        if (categoryBoxState) {
            categoryDropdownArrow.animate().rotation(90).setDuration(300);

            for (int i = 0; i < categoryTextViews.size(); i++) {
                categorySelectorBox.addView(categoryTextViews.get(i));
                categorySelectorBox.addView(categoryCheckBoxes.get(i));
            }
        }
        else {
            categoryDropdownArrow.animate().rotation(0).setDuration(300);
            for (int i = 0; i < categoryTextViews.size() * 2; i++)
                categorySelectorBox.removeViewAt(2);
        }
        categoryBoxState = !categoryBoxState;
    }
//    -------------

//    Change checkbox color
    public static void setCheckBoxColor(AppCompatCheckBox checkBox, int uncheckedColor, int checkedColor) {
        ColorStateList colorStateList = new ColorStateList(
                new int[][] {
                        new int[] { -android.R.attr.state_checked }, // unchecked
                        new int[] {  android.R.attr.state_checked }  // checked
                },
                new int[] {
                        uncheckedColor,
                        checkedColor
                }
        );
        CompoundButtonCompat.setButtonTintList(checkBox, colorStateList);
    }

}