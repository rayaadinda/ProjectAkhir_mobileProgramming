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


public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView register;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (user.equals("admin") && pass.equals("1234")) {
                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();

                    showLoginNotification();

                    startActivity(new Intent(MainActivity.this, Dashboard.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Username atau password salah",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder
                        (MainActivity.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah Anda ingin mendaftar akun baru");
                builder.setPositiveButton
                        ("Ya", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this,
                                        RegisterActivity.class));
                            }
                        });
                builder.setNegativeButton("Batal", null);
                builder.show();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Lupa Password");
                builder.setMessage("Apakah anda ingin mereset password?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                          Toast.makeText(MainActivity.this, "Password telah direset", Toast.LENGTH_SHORT).show();
                     }
                });
                builder.setNegativeButton("Batal", null);
                builder.show();
           }
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
                .setContentTitle("Login Berhasil")
                .setContentText("Selamat Datang, admin!")
                .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        notificationManager.notify(1, builder.build());
    }



}