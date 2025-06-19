package com.example.projectakhir_raya;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView navList;
    private ImageView menuButton;

    ImageView sensor, map;

    private String[] drawerItems = { "Dashboard", "Tambah Data", "Lihat Data", "Keluar" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navList = findViewById(R.id.nav_list);
        menuButton = findViewById(R.id.menu_button);

        sensor = findViewById(R.id.card_sensor);
        map = findViewById(R.id.card_maps);

        navList.setAdapter(new ArrayAdapter<>(this,
                R.layout.list_item_navigation, drawerItems));

        // list view pada nav bar
        navList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0: // Beranda
                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                    break;
                case 1: // Profil
                    startActivity(new Intent(Dashboard.this, InputData.class));
                    break;
                case 2: // Lihat Data
                    startActivity(new Intent(Dashboard.this, LihatDataActivity.class));
                    break;
                case 3: // Keluar
                    finish(); // atau tampilkan dialog keluar/log out
                    break;
                default:
                    Toast.makeText(this, "Menu tidak dikenali", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawer(navList); // Tutup drawer setelah memilih item
        });

        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(navList));
        onFeature1Click(sensor);

    }

    public void onFeature1Click(View view) {
        Toast.makeText(this, "Fitur Info diklik", Toast.LENGTH_SHORT).show();

        // pindah ke activit sensor
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,
                        SensorActivity.class));
            }
        });

        // pindah ke activity maps
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,
                        MapActivity.class));
            }
        });
    }
}
