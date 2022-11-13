package com.example.phi_nhai_dai.main_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.phi_nhai_dai.R;


public class MainPage extends AppCompatActivity {

    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tv1= (TextView) findViewById(R.id.textView);
        tv1.setText("dfdfd");
    }
}