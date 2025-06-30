// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView register;
    TextView forgotPassword;
    View parentView;

    private DatabaseHelper databaseHelper;

    private static boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Check if user is already logged in
        if (databaseHelper.isLoggedIn(this)) {
            // User is already logged in, go directly to Dashboard
            startActivity(new Intent(MainActivity.this, Dashboard.class));
            finish(); // Close MainActivity
            return;
        }

        setContentView(R.layout.activity_main);

        parentView = findViewById(android.R.id.content);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.forgot_password);

        login.setOnClickListener(v -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (email.isEmpty()) {
                username.setError("Email is required");
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Password is required");
                return;
            }

            boolean isValid = databaseHelper.checkUser(email, pass);

            if (isValid) {
        
                int userId = databaseHelper.getUserId(email);

                databaseHelper.createLoginSession(MainActivity.this, email, userId);

                Snackbar.make(parentView, "Login successful", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(R.color.accent))
                        .setTextColor(getResources().getColor(R.color.button_text))
                        .show();

                showLoginNotification();
                startActivity(new Intent(MainActivity.this, Dashboard.class));
                finish(); 
            } else {
                Snackbar.make(parentView, "Invalid email or password", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getResources().getColor(R.color.accent))
                        .setTextColor(getResources().getColor(R.color.button_text))
                        .show();
            }
        });

        register.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Create Account")
                    .setMessage("Would you like to create a new account?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        });

        forgotPassword.setOnClickListener(v -> {
            View forgotPasswordView = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
            EditText emailInput = forgotPasswordView.findViewById(R.id.forgot_email);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Reset Password")
                    .setView(forgotPasswordView)
                    .setPositiveButton("Reset", (dialog, which) -> {
                        String email = emailInput.getText().toString().trim();
                        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Snackbar.make(parentView, "Please enter a valid email address", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(getResources().getColor(R.color.accent))
                                    .setTextColor(getResources().getColor(R.color.button_text))
                                    .show();
                        } else {
                            
                            Snackbar.make(parentView, "Password reset instruction sent to your email",
                                    Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(getResources().getColor(R.color.accent))
                                    .setTextColor(getResources().getColor(R.color.button_text))
                                    .show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        });
    }

    private void showLoginNotification() {
        String channelId = "login_channel";
        String channelName = "Login Notification";

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }


        String userEmail = databaseHelper.getLoggedInEmail(this);
        String displayEmail = userEmail != null ? userEmail : "user";

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Login Successful")
                .setContentText("Welcome back, " + displayEmail)
                .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        notificationManager.notify(1, builder.build());
    }
}