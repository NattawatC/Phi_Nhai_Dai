package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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
import java.util.Objects;


public class MainPage extends AppCompatActivity {

    public static Boolean eventFilter;
    @SuppressLint("StaticFieldLeak")
    public static Context context;


    BottomNavigationView bottomNavigationView;

    //    New Add
    // Category Selector Box
    boolean categoryBoxState = true;
    boolean categoryLoaded = false;
    GridLayout categorySelectorBox;
    TextView chooseCategoryTitle;
    ImageButton categoryDropdownArrow;
    ArrayList<TextView> categoryTextViews;
    ArrayList<AppCompatCheckBox> categoryCheckBoxes;
    TextView northern_text ;
    TextView central_text;
    TextView eastern_text;
    TextView southern_text;

    AppCompatCheckBox northern_checkbox;
    AppCompatCheckBox central_checkbox;
    AppCompatCheckBox eastern_checkbox;
    AppCompatCheckBox southern_checkbox;

    RecyclerView recyclerView;
    SQLiteDatabase db1;

    ArrayList<Filter> filter = new ArrayList<>();

    // Main Grid Layout
    GridLayout parentGridLayout;
    //    -------------
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.new_discover_page);
        initializeInstances();
        initializeBottomNavigation();
        eventFilter = false;
        context =  MainPage.this;

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        Database db = new Database(context);
        if (firstStart) {
           OpenOrCreateDataBase();
        }

        else {
            db.openDB();
        }
        db1 = openOrCreateDatabase("place", Context.MODE_PRIVATE, null);
        loadActivity();


        Filter north = new Filter("region", "Northern");
        Filter south = new Filter("region", "Southern");
        Filter east = new Filter("region", "Eastern");
        Filter central = new Filter("region", "Central");

        northern_checkbox.setOnCheckedChangeListener(new check_change(northern_checkbox, north));
        southern_checkbox.setOnCheckedChangeListener(new check_change(southern_checkbox, south));
        central_checkbox.setOnCheckedChangeListener(new check_change(central_checkbox, central));
        eastern_checkbox.setOnCheckedChangeListener(new check_change(eastern_checkbox, east));

        //New Add
        // Category Dropdown Settings
        chooseCategoryTitle.setOnClickListener(this::toggleCategoryBox);
        categoryDropdownArrow.setOnClickListener(this::toggleCategoryBox);

//        ----------


    }

    public void loadActivity() {
        if (!eventFilter) {
            ArrayList<Place> PlaceArrayList = new ArrayList<>();
            recyclerView = findViewById(R.id.recyclerView);
            Adapter adapter = new Adapter(context, PlaceArrayList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ReadData(PlaceArrayList, db1);
        }
        else {
            ArrayList<Place> p = new ArrayList<>();
            String filterStatement = ImplementFilterStatement(filter);
            FilterData(p, db1, filterStatement);
            Adapter a = new Adapter(context, p);
            recyclerView.setAdapter(a);
        }
    }

    public void initializeBottomNavigation(){
        bottomNavigationView = findViewById(R.id.dock_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_discover);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int currentItem = item.getItemId();
            if (currentItem == R.id.nav_discover) {
                return true;
            }
            else if (currentItem == R.id.nav_aboutus) {
                Intent myIntent = new Intent(MainPage.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
            else if (currentItem == R.id.nav_favourite) {
                Intent myIntent = new Intent(MainPage.this, Favorite.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    }

    public void OpenOrCreateDataBase() {
        Database db = new Database(this);
        try {
            db.getReadableDatabase();
            db.copyDB();
        } catch (IOException ioe) {

            throw new Error("Database not created");
        }

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    public void ReadData(ArrayList<Place> p, SQLiteDatabase db1) {
        @SuppressLint("Recycle") Cursor c = db1.rawQuery("SELECT * FROM Places ", null);
        c.moveToFirst();
        do {
            p.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3), c.getFloat(4), c.getString(5)));
        } while (c.moveToNext());
        Collections.shuffle(p);
        c.close();
    }


    public String ImplementFilterStatement(ArrayList<Filter> filterArrayList){
        StringBuilder filterStatement = new StringBuilder("WHERE ");

        for (int i = 0; i < filterArrayList.size(); i++) {
            if (i == filterArrayList.size()-1) {
                filterStatement.append(filterArrayList.get(i).getCategory()).append("= \"").append(filterArrayList.get(i).getValue()).append("\"");
            }
            else {
                filterStatement.append(filterArrayList.get(i).getCategory()).append("= \"").append(filterArrayList.get(i).getValue()).append("\" OR ");
            }
        }
        return filterStatement.toString();
    }

    public void FilterData(ArrayList<Place> p, SQLiteDatabase db1, String filterStatement) {
        @SuppressLint("Recycle") Cursor c = db1.rawQuery("SELECT * FROM Places " +
                filterStatement, null);
        c.moveToFirst();
        do {
            p.add(new Place(c.getInt(0), c.getString(1)
                    , c.getString(2), c.getString(3), c.getFloat(4), c.getString(5)));
        } while (c.moveToNext());
        Collections.shuffle(p);
        c.close();
    }


    public class check_change implements CompoundButton.OnCheckedChangeListener {

        private final AppCompatCheckBox b;
        private final Filter f;

        public check_change(AppCompatCheckBox b, Filter f) {
            this.b = b;
            this.f = f;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (b.isChecked()) {
                    filter.add(f);
                    eventFilter = true;

                } else {
                    if (filter.size() > 1) {
                        filter.remove(f);
                        eventFilter = true;
                    }

                    else if (filter.size() == 1) {
                        filter.remove(f);
                        eventFilter = false;
                    }
                }
                loadActivity();
        }
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
        northern_text      = new TextView(this);
        central_text      = new TextView(this);
        eastern_text       = new TextView(this);
        southern_text         = new TextView(this);

        categoryCheckBoxes  = new ArrayList<>();
        northern_checkbox  = new AppCompatCheckBox(this);
        central_checkbox  = new AppCompatCheckBox(this);
        eastern_checkbox  = new AppCompatCheckBox(this);
        southern_checkbox     = new AppCompatCheckBox(this);
    }
    //    -------------

//    New Add
    // Expand and minimize category selector box
    public void toggleCategoryBox(View view) {
        if (!categoryLoaded) {
            // Loads TextViews into ArrayList
            categoryTextViews.add(northern_text);
            categoryTextViews.add(central_text);
            categoryTextViews.add(eastern_text);
            categoryTextViews.add(southern_text);

            // Loads Checkboxes to ArrayList
            categoryCheckBoxes.add(northern_checkbox);
            categoryCheckBoxes.add(central_checkbox);
            categoryCheckBoxes.add(eastern_checkbox);
            categoryCheckBoxes.add(southern_checkbox);

            // Category UI Elements Setup
            northern_text .setText(R.string.northern);
            central_text.setText(R.string.central);
            eastern_text.setText(R.string.eastern);
            southern_text.setText(R.string.southern);

            Typeface poppins_bold = ResourcesCompat.getFont(this, R.font.poppins_bold);

            for (TextView t : categoryTextViews) {
                t.setTextSize(18);
                t.setTextColor(getResources().getColor(R.color.secondary));
                t.setPadding(getValueInDp(20), 0, getValueInDp(220), 0);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_aboutus){
            Intent myIntent = new Intent(MainPage.this, MainActivity.class);
            startActivity(myIntent);
            finish();
            return true;
        }

        else if (id == R.id.nav_discover){
            Intent myIntent = new Intent(MainPage.this, MainPage.class);
            startActivity(myIntent);
            finish();
            return true;
        }

        else if (id == R.id.nav_favourite){
            Intent myIntent = new Intent(MainPage.this, Favorite.class);
            startActivity(myIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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