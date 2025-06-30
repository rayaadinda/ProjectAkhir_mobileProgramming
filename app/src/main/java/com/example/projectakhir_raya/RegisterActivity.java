// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private View parentView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        parentView = findViewById(android.R.id.content);
        emailInput = findViewById(R.id.reg_email);
        passwordInput = findViewById(R.id.reg_password);
        confirmPasswordInput = findViewById(R.id.reg_confirm_password);
        registerButton = findViewById(R.id.reg_button);

        ImageButton backArrow = findViewById(R.id.back_arrow);
        TextView loginLink = findViewById(R.id.login_link);

        backArrow.setOnClickListener(v -> finish());
        loginLink.setOnClickListener(v -> finish());

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if (validateInputs(email, password, confirmPassword)) {
                boolean isRegistered = databaseHelper.registerUser(email, password);

                if (isRegistered) {
                    Snackbar.make(parentView, "Account created successfully", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.accent))
                            .setTextColor(getResources().getColor(R.color.button_text))
                            .show();

                    parentView.postDelayed(() -> finish(), 1500);
                } else {
                    Snackbar.make(parentView, "Email already exists", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.accent))
                            .setTextColor(getResources().getColor(R.color.button_text))
                            .show();
                }
            }
        });
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordInput.setError("Please confirm your password");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email address");
            return false;
        }

        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return false;
        }

        return true;
    }
}