package com.example.phi_nhai_dai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.phi_nhai_dai.main_page.MainPage;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> openHomePage());
    }

    private void openHomePage() {
        startActivity(new Intent(this, MainPage.class));
    }

}


