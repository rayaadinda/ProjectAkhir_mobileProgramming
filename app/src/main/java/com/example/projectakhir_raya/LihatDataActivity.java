package com.example.projectakhir_raya;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LihatDataActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    ArrayList<pakaian> studentList = new ArrayList<>();
    pakaianAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        studentList.clear();
        Cursor cursor = db.getAllData();
        if (cursor.moveToFirst()) {
            do {
                pakaian s = new pakaian(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                studentList.add(s);
            } while (cursor.moveToNext());
        }
        adapter = new pakaianAdapter(this,
                studentList);
        listView.setAdapter(adapter);
    }
}
