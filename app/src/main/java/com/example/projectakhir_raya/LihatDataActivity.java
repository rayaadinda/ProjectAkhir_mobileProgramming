// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LihatDataActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    EditText etSearch;
    ArrayList<pakaian> productList = new ArrayList<>();
    ArrayList<pakaian> filteredList = new ArrayList<>();
    pakaianAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);
        
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        etSearch = findViewById(R.id.etSearch);
        
        setupSearch();
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterProducts(String query) {
        filteredList.clear();
        
        if (query.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (pakaian product : productList) {
                if (product.getNama().toLowerCase().contains(lowerCaseQuery) ||
                    product.getKategori().toLowerCase().contains(lowerCaseQuery) ||
                    product.getHarga().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(product);
                }
            }
        }
        
        adapter.updateData(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        productList.clear();
        Cursor cursor = db.getAllData();
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String id = cursor.getString(0);
                    String nama = cursor.getString(1);
                    String kategori = cursor.getString(2);
                    String harga = cursor.getString(3);
                    String jumlah = cursor.getString(4);
                    String foto = cursor.getString(5);
                    
                    pakaian product = new pakaian(
                            id,
                            nama,
                            kategori,
                            jumlah,
                            harga,
                            foto
                    );
                    productList.add(product);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        filteredList.clear();
        filteredList.addAll(productList);
        adapter = new pakaianAdapter(this, filteredList);
        listView.setAdapter(adapter);
        if (productList.isEmpty()) {
            Toast.makeText(this,
                "No products found. Click '+ Add' button to add products, or restart the app if you just installed it.", 
                Toast.LENGTH_LONG).show();
        }
    }
}
