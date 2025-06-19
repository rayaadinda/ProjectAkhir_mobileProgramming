package com.example.projectakhir_raya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InputData extends AppCompatActivity {

    private EditText etNama, etKategori, etHarga, etJumlah, etFoto;
    private Button btnSimpan;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        etNama = findViewById(R.id.etNama);
        etKategori = findViewById(R.id.etKategori);
        etHarga = findViewById(R.id.etHarga);
        etJumlah = findViewById(R.id.etJumlah);
        etFoto = findViewById(R.id.etFoto);
        btnSimpan = findViewById(R.id.btnSimpan);

        dbHelper = new DatabaseHelper(this);

        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String kategori = etKategori.getText().toString().trim();
            String harga = etHarga.getText().toString().trim();
            String jumlah = etJumlah.getText().toString().trim();
            String foto = etFoto.getText().toString().trim();

            if (nama.isEmpty() || kategori.isEmpty() || harga.isEmpty() || jumlah.isEmpty() || foto.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.insertData(nama, kategori, harga, jumlah, foto);
                if (inserted) {
                    Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    etNama.setText("");
                    etKategori.setText("");
                    etHarga.setText("");
                    etJumlah.setText("");
                    etFoto.setText("");
                } else {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}