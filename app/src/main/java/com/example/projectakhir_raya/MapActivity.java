package com.example.projectakhir_raya;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull; // Import yang benar untuk NonNull
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// Tidak perlu import BottomNavigationView jika tidak digunakan di sini
// import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.api.IMapController;

public class MapActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
    // private BottomNavigationView bottomNavigationView; // Hapus jika tidak ada di layout activity_map.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Load Konfigurasi osmdroid terlebih dahulu
        // Ini adalah praktik yang baik untuk dilakukan sebelum setContentView
        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osmdroid", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());

        // 2. Set layout activity
        setContentView(R.layout.activity_map);

        // 3. Inisialisasi MapView dari layout SETELAH setContentView
        map = findViewById(R.id.map);

        // 4. Lakukan semua konfigurasi pada objek 'map' SETELAH diinisialisasi
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        // Toast.makeText(this, "Checking permissions...", Toast.LENGTH_SHORT).show(); // Opsional

        // 5. Lakukan pengecekan izin
        // Menggunakan array untuk lebih rapi
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE // Izin ini seringkali penting untuk caching tile peta
        };

        // Cek apakah izin sudah diberikan
        if (hasPermissions()) {
            setupMap();
        } else {
            // Jika belum, minta izin
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private boolean hasPermissions() {
        // Cukup cek FINE_LOCATION karena sudah mencakup COARSE_LOCATION.
        // Tambahkan pengecekan WRITE_EXTERNAL_STORAGE yang penting untuk osmdroid.
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void setupMap() {
        // Pastikan map tidak null sebelum digunakan
        if (map == null) return;

        IMapController mapController = map.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(-6.2088, 106.8456); // Jakarta
        mapController.setCenter(startPoint);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            // Cek apakah semua izin yang diminta telah diberikan
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin diberikan!", Toast.LENGTH_SHORT).show();
                setupMap();
            } else {
                Toast.makeText(this, "Izin ditolak. Peta mungkin tidak berfungsi dengan baik.", Toast.LENGTH_LONG).show();
                // Anda bisa menonaktifkan fitur lokasi atau menampilkan pesan lain
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Pastikan map tidak null sebelum memanggil onResume-nya
        if (map != null) {
            map.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pastikan map tidak null sebelum memanggil onPause-nya
        if (map != null) {
            map.onPause();
        }
    }
}