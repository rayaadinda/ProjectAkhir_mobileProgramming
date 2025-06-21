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
    
    private static boolean isFirstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        parentView = findViewById(android.R.id.content);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.forgot_password);

        login.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty()) {
                username.setError("Email is required");
                return;
            }
            
            if (pass.isEmpty()) {
                password.setError("Password is required");
                return;
            }

            if (user.equals("admin") && pass.equals("1234")) {
                Snackbar.make(parentView, "Login successful", Snackbar.LENGTH_SHORT).show();
                showLoginNotification();
                startActivity(new Intent(MainActivity.this, Dashboard.class));
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
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Reset Password")
                    .setMessage("Would you like to reset your password?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Snackbar.make(parentView, "Password reset email sent", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getResources().getColor(R.color.accent))
                                .setTextColor(getResources().getColor(R.color.button_text))
                                .show();
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        });
    }

    private void showLoginNotification() {
        String channelId = "login_channel";
        String channelName = "Login Notification";

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel
                    (channelId, channelName,
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Login Successful")
                .setContentText("Welcome back, admin!")
                .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        notificationManager.notify(1, builder.build());
    }
    

}