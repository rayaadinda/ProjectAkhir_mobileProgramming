// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.NumberFormat;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ListView navList;
    private ImageView menuButton;
    private NavigationView navigationView;

    MaterialCardView sensor, map, speech, catalog, addData;
    TextView totalProducts, inventoryValue;
    TextView viewAllProducts;
    DatabaseHelper databaseHelper;

    TextView product1Name, product1Stock, product1Price;
    TextView product2Name, product2Stock, product2Price;
    ImageView product1Image, product2Image;

    private String[] drawerItems = { "Dashboard", "Add Product", "Product Catalog", "Keluar" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        databaseHelper = new DatabaseHelper(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        navList = findViewById(R.id.nav_list);
        menuButton = findViewById(R.id.menu_button);
        navigationView = findViewById(R.id.nav_view);
        sensor = findViewById(R.id.card_sensor);
        map = findViewById(R.id.card_maps);
        speech = findViewById(R.id.card_speech);
        catalog = findViewById(R.id.card_catalog);
        addData = findViewById(R.id.card_add_data);
        totalProducts = findViewById(R.id.total_products);
        inventoryValue = findViewById(R.id.inventory_value);
        viewAllProducts = findViewById(R.id.view_all_products);
        try {
            product1Name = findViewById(R.id.product1_name);
            product1Stock = findViewById(R.id.product1_stock);
            product1Price = findViewById(R.id.product1_price);
            product1Image = findViewById(R.id.product1_image);
            product2Name = findViewById(R.id.product2_name);
            product2Stock = findViewById(R.id.product2_stock);
            product2Price = findViewById(R.id.product2_price);
            product2Image = findViewById(R.id.product2_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupNavigationDrawer();
        loadDashboardData();
        sensor.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, SensorActivity.class));
        });
        map.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, MapActivity.class));
        });
        speech.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, SpeechRecognizer.class));
        });
        catalog.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, LihatDataActivity.class));
        });
        addData.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, InputData.class));
        });
        if (viewAllProducts != null) {
            viewAllProducts.setOnClickListener(v -> {
                startActivity(new Intent(Dashboard.this, LihatDataActivity.class));
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardData();
    }

    private void loadDashboardData() {
        int productCount = 0;
        long totalValue = 0;
        Cursor cursor = databaseHelper.getAllData();
        if (cursor != null && cursor.moveToFirst()) {
            productCount = cursor.getCount();
            do {
                int hargaIndex = cursor.getColumnIndex("harga");
                int jumlahIndex = cursor.getColumnIndex("jumlah");
                if (hargaIndex != -1 && jumlahIndex != -1) {
                    String hargaStr = cursor.getString(hargaIndex);
                    String jumlahStr = cursor.getString(jumlahIndex);
                    try {
                        int harga = Integer.parseInt(hargaStr);
                        int jumlah = Integer.parseInt(jumlahStr);
                        totalValue += (long) harga * jumlah;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } while (cursor.moveToNext());
            if (totalProducts != null) {
                totalProducts.setText(String.valueOf(productCount));
            }
            if (inventoryValue != null) {
                double valueInMillions = totalValue / 1_000_000.0;
                String formattedValue = "Rp " + String.format("%.1f", valueInMillions) + "M";
                inventoryValue.setText(formattedValue);
            }
            displayRecentProducts(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void displayRecentProducts(Cursor cursor) {
        if (product1Name == null || product2Name == null) {
            return;
        }
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                int nameIndex = cursor.getColumnIndex("nama");
                int jumlahIndex = cursor.getColumnIndex("jumlah");
                int hargaIndex = cursor.getColumnIndex("harga");
                int fotoIndex = cursor.getColumnIndex("foto");
                if (nameIndex != -1 && jumlahIndex != -1 && hargaIndex != -1) {
                    product1Name.setText(cursor.getString(nameIndex));
                    product1Stock.setText(cursor.getString(jumlahIndex) + " in stock");
                    try {
                        int harga = Integer.parseInt(cursor.getString(hargaIndex));
                        NumberFormat formatter = NumberFormat.getInstance(new Locale("id"));
                        String formattedPrice = "Rp " + formatter.format(harga);
                        product1Price.setText(formattedPrice);
                    } catch (NumberFormatException e) {
                        product1Price.setText(cursor.getString(hargaIndex));
                    }
                    if (fotoIndex != -1 && product1Image != null) {
                        String fotoPath = cursor.getString(fotoIndex);
                        setProductImage(product1Image, fotoPath);
                    }
                }
                if (cursor.moveToNext()) {
                    product2Name.setText(cursor.getString(nameIndex));
                    product2Stock.setText(cursor.getString(jumlahIndex) + " in stock");
                    try {
                        int harga = Integer.parseInt(cursor.getString(hargaIndex));
                        NumberFormat formatter = NumberFormat.getInstance(new Locale("id"));
                        String formattedPrice = "Rp " + formatter.format(harga);
                        product2Price.setText(formattedPrice);
                    } catch (NumberFormatException e) {
                        product2Price.setText(cursor.getString(hargaIndex));
                    }
                    if (fotoIndex != -1 && product2Image != null) {
                        String fotoPath = cursor.getString(fotoIndex);
                        setProductImage(product2Image, fotoPath);
                    }
                }
            }
        }
    }

    private void setProductImage(ImageView imageView, String fotoPath) {
        if (fotoPath == null || fotoPath.isEmpty()) {
            return;
        }
        int resourceId = getResources().getIdentifier(
                fotoPath, "drawable", getPackageName());
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        } else {
            imageView.setImageResource(R.drawable.product_placeholder);
        }
    }

    private void setupNavigationDrawer() {
        menuButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navList.setAdapter(new ArrayAdapter<>(this,
                R.layout.list_item_navigation, drawerItems));
        navList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 1:
                    startActivity(new Intent(Dashboard.this, InputData.class));
                    break;
                case 2:
                    startActivity(new Intent(Dashboard.this, LihatDataActivity.class));
                    break;
                case 3:
                    databaseHelper.logoutUser(Dashboard.this);
                    Intent intent = new Intent(Dashboard.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    Toast.makeText(this, "Menu tidak dikenali", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
