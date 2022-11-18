package com.example.phi_nhai_dai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.phi_nhai_dai.main_page.*;

import com.example.phi_nhai_dai.R;

import java.util.Objects;


public class Description extends AppCompatActivity {

    TextView name;
    TextView location;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.card_description_relative);

        //Set name and location
        String nameMessage = getIntent().getStringExtra("NAME");
        String[] arr = nameMessage.split(",");
        name = findViewById(R.id.title_example);
        name.setText(arr[0]);

        location = findViewById(R.id.location_example);
        location.setText(arr[1]);

        //Set Image
        String img =  getIntent().getStringExtra("IMAGE");
        image = findViewById(R.id.cream_box);
        Glide.with(this).load(img).into(image);

        //Set Rating
//        String rating = getIntent().getStringExtra();
//        rating = findViewById(R.id.);

    }


}