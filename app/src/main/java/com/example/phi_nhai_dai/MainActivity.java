package com.example.phi_nhai_dai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> openHomePage());
    }

    private void openHomePage() {
        startActivity(new Intent(this, Discover.class));
    }

}


