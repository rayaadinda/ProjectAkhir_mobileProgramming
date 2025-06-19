package com.example.projectakhir_raya;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ImageButton backArrow = findViewById(R.id.back_arrow);
        TextView loginLink = findViewById(R.id.login_link);

        backArrow.setOnClickListener(v -> finish());
        loginLink.setOnClickListener(v -> finish());
    }
}