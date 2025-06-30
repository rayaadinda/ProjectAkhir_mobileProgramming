# Aplikasi Toko Peralatan Outdoor - Android

Aplikasi Android untuk mengelola inventaris peralatan outdoor dengan fitur seperti manajemen produk, integrasi sensor, peta, dan pengenalan suara.

## Tangkapan Layar

*[Tangkapan layar akan ditambahkan]*

## Fitur

- **Autentikasi Pengguna**: Sistem login dan registrasi yang aman
- **Manajemen Produk**: Tambah, lihat, edit, dan hapus produk peralatan outdoor
- **Pelacakan Inventaris**: Pantau tingkat stok dan nilai inventaris
- **Integrasi Perangkat Keras**: 
  - Sensor perangkat (akselerometer, proximity, giroskop)
  - Integrasi peta dengan OpenStreetMap
  - Pengenalan suara ke teks

## Instalasi

### Prasyarat
- Android Studio (versi terbaru direkomendasikan)
- Android SDK dengan level API minimum 21 (Android 5.0 Lollipop)
- Java Development Kit (JDK)

### Langkah-langkah Instalasi

#### Opsi 1: Clone Repository
1. Buka terminal/command prompt
2. Clone repository:
   ```bash
   git clone https://github.com/yourusername/outdoor-gear-store.git
   ```
3. Buka Android Studio
4. Pilih "Open an Existing Project"
5. Navigasi ke folder repository yang telah di-clone dan klik "Open"

#### Opsi 2: Unduh File ZIP
1. Unduh file ZIP dari repository GitHub
2. Ekstrak file ZIP ke lokasi yang diinginkan
3. Buka Android Studio
4. Pilih "Open an Existing Project"
5. Navigasi ke folder yang telah diekstrak dan klik "Open"

### Membangun Aplikasi
1. Setelah proyek terbuka di Android Studio, tunggu Gradle melakukan sinkronisasi
2. Hubungkan perangkat Android melalui USB atau siapkan emulator
3. Klik tombol "Run" (segitiga hijau) atau tekan Shift+F10
4. Pilih perangkat/emulator Anda dari dialog target deployment
5. Tunggu aplikasi dibangun dan diinstal di perangkat Anda

## Panduan Penggunaan

### Registrasi
1. Jalankan aplikasi
2. Klik tombol "Register" pada layar login
3. Isi formulir registrasi:
   - Masukkan alamat email yang valid
   - Buat kata sandi (minimal 6 karakter)
   - Konfirmasi kata sandi Anda
4. Klik "Register"
5. Anda akan melihat pesan sukses dan diarahkan kembali ke layar login

### Login
1. Masukkan alamat email terdaftar Anda
2. Masukkan kata sandi Anda
3. Klik "Login"
4. Kredensial admin default:
   - Email: admin@example.com
   - Kata sandi: 1234

### Dasbor
Setelah login, Anda akan diarahkan ke dasbor di mana Anda dapat:
- Melihat statistik inventaris
- Mengakses fitur melalui kartu
- Melihat produk unggulan
- Menggunakan navigation drawer untuk opsi tambahan

### Menambahkan Produk
1. Dari dasbor, ketuk "Add Product" atau buka navigation drawer dan pilih "Add Data"
2. Isi detail produk:
   - Nama
   - Kategori
   - Harga
   - Jumlah
   - Pilih gambar (opsional)
3. Klik "Simpan"

### Melihat Produk
1. Dari dasbor, ketuk "View All Products" atau buka navigation drawer dan pilih "View Data"
2. Jelajahi daftar produk
3. Gunakan kotak pencarian untuk memfilter produk berdasarkan nama

### Menggunakan Fitur Khusus
- **Sensor**: Ketuk kartu "Sensors" untuk mengakses data sensor perangkat
- **Peta**: Ketuk kartu "Maps" untuk melihat lokasi di OpenStreetMap
- **Pengenalan Suara**: Ketuk kartu "Speech" untuk mengubah kata-kata yang diucapkan menjadi teks

## Pemecahan Masalah

### Masalah Login
- Pastikan Anda menggunakan email dan kata sandi yang benar
- Jika Anda baru saja menginstal aplikasi, coba gunakan kredensial admin default:
  - Email: admin@example.com
  - Kata sandi: 1234

### Kesalahan Database
Jika Anda mengalami kesalahan terkait database:
1. Buka pengaturan perangkat Anda
2. Navigasi ke Aplikasi > Outdoor Gear Store > Penyimpanan
3. Hapus data dan cache
4. Mulai ulang aplikasi

### Masalah Izin
Aplikasi memerlukan beberapa izin untuk berfungsi dengan benar:
- Izin penyimpanan untuk gambar produk
- Izin lokasi untuk peta
- Izin mikrofon untuk pengenalan suara

