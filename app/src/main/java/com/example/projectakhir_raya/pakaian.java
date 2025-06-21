// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

public class pakaian {

    public String id, nama, kategori, jumlah, harga, foto;

    public pakaian(String id, String nama, String kategori, String jumlah, String harga, String foto) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.harga = harga;
        this.foto = foto;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getKategori() { return kategori; }
    public String getJumlah() { return jumlah; }
    public String getHarga() { return harga; }
    public String getFoto() { return foto; }
}
