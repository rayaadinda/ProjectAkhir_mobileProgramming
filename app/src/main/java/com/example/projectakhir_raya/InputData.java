// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class InputData extends AppCompatActivity {

    private EditText etNama, etKategori, etHarga, etJumlah;
    private Button btnSimpan, btnSelectImage;
    private ImageView imgProductPreview;
    private TextView tvTitle;
    private DatabaseHelper dbHelper;

    private Uri selectedImageUri;
    private String currentProductId;
    private boolean isEditMode = false;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    try {
                        getContentResolver().takePersistableUriPermission(
                            selectedImageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to secure image permissions", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "Selected image: " + selectedImageUri, Toast.LENGTH_SHORT).show();
                    Glide.with(this)
                            .load(selectedImageUri)
                            .placeholder(R.drawable.product_placeholder)
                            .error(R.drawable.product_placeholder)
                            .into(imgProductPreview);
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Permission denied to read storage", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        etNama = findViewById(R.id.etNama);
        etKategori = findViewById(R.id.etKategori);
        etHarga = findViewById(R.id.etHarga);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imgProductPreview = findViewById(R.id.imgProductPreview);
        tvTitle = findViewById(R.id.tvTitle);

        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("id")) {
            isEditMode = true;
            currentProductId = getIntent().getStringExtra("id");
            setupEditMode();
        } else {
            isEditMode = false;
            tvTitle.setText("Add New Item");
            btnSimpan.setText("Save Product");
        }

        btnSelectImage.setOnClickListener(v -> checkPermissionAndOpenGallery());
        btnSimpan.setOnClickListener(v -> saveData());
    }

    private void setupEditMode() {
        tvTitle.setText("Edit Item");
        btnSimpan.setText("Update Product");

        etNama.setText(getIntent().getStringExtra("nama"));
        etKategori.setText(getIntent().getStringExtra("kategori"));
        etHarga.setText(getIntent().getStringExtra("harga"));
        etJumlah.setText(getIntent().getStringExtra("jumlah"));

        String imageUriString = getIntent().getStringExtra("foto");
        if (imageUriString != null && !imageUriString.isEmpty()) {
            selectedImageUri = Uri.parse(imageUriString);
            Glide.with(this)
                    .load(selectedImageUri)
                    .placeholder(R.drawable.product_placeholder)
                    .error(R.drawable.product_placeholder)
                    .into(imgProductPreview);
        }
    }

    private void checkPermissionAndOpenGallery() {
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(permission);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryLauncher.launch(intent);
    }

    private void saveData() {
        String nama = etNama.getText().toString().trim();
        String kategori = etKategori.getText().toString().trim();
        String harga = etHarga.getText().toString().trim();
        String jumlah = etJumlah.getText().toString().trim();
        String fotoUriString = (selectedImageUri != null) ? selectedImageUri.toString() : "";

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(kategori) || TextUtils.isEmpty(harga) || TextUtils.isEmpty(jumlah)) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (isEditMode) {
            boolean updated = dbHelper.updateData(currentProductId, nama, harga, fotoUriString, jumlah, kategori);
            if (updated) {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show();
            }
        } else {
            boolean inserted = dbHelper.insertData(nama, kategori, harga, jumlah, fotoUriString);
            if (inserted) {
                Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save product", Toast.LENGTH_SHORT).show();
            }
        }
    }
}