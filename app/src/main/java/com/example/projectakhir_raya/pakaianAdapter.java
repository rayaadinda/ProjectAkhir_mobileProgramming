package com.example.projectakhir_raya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class pakaianAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<pakaian> pakaianList;
    private DatabaseHelper db;

    public pakaianAdapter(Context context, ArrayList<pakaian> pakaianList) {
        this.context = context;
        this.pakaianList = pakaianList;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return pakaianList.size();
    }

    @Override
    public Object getItem(int position) {
        return pakaianList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtInfo;
        Button btnEdit;
        Button btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_pakaian, parent, false);
            holder = new ViewHolder();
            holder.txtInfo = convertView.findViewById(R.id.txtInfo);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        pakaian item = pakaianList.get(position);
        holder.txtInfo.setText(
                item.nama + " - " + item.kategori + " - " + item.harga + " - " + item.jumlah + " - " + item.foto);

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, InputData.class);
            intent.putExtra("id", item.id);
            intent.putExtra("nama", item.nama);
            intent.putExtra("kategori", item.kategori);
            intent.putExtra("harga", item.harga);
            intent.putExtra("jumlah", item.jumlah);
            intent.putExtra("foto", item.foto);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        db.deletData(item.id);
                        pakaianList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        });

        return convertView;
    }
}