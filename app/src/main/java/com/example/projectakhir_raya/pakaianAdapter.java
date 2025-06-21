// Raya Adinda Jayadi Ahmad
package com.example.projectakhir_raya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
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

    public void updateData(ArrayList<pakaian> newList) {
        this.pakaianList = newList;
        notifyDataSetChanged();
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
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductCategory;
        TextView txtCategory;
        TextView txtStock;
        TextView txtPrice;
        TextView txtQuantity;
        Button btnEdit;
        Button btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_pakaian, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.txtProductName = convertView.findViewById(R.id.txtProductName);
            holder.txtProductCategory = convertView.findViewById(R.id.txtProductCategory);
            holder.txtCategory = convertView.findViewById(R.id.txtCategory);
            holder.txtStock = convertView.findViewById(R.id.txtStock);
            holder.txtPrice = convertView.findViewById(R.id.txtPrice);
            holder.txtQuantity = convertView.findViewById(R.id.txtQuantity);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        pakaian item = pakaianList.get(position);
        holder.txtProductName.setText(item.getNama());
        holder.txtProductCategory.setText(item.getKategori());
        holder.txtCategory.setText(item.getKategori());

        String price = item.getHarga();
        if (price != null && !price.isEmpty()) {
            try {
                long priceValue = Long.parseLong(price);
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String formattedPrice = formatter.format(priceValue);
                holder.txtPrice.setText("Rp " + formattedPrice.replace(",", "."));
            } catch (NumberFormatException e) {
                holder.txtPrice.setText("Rp " + price); // Fallback to raw value
            }
        } else {
            holder.txtPrice.setText("Rp 0");
        }

        String quantity = item.getJumlah();
        if (quantity != null && !quantity.isEmpty()) {
            int qty = Integer.parseInt(quantity);
            holder.txtQuantity.setText(qty + " items");

            if (qty > 0) {
                holder.txtStock.setText("In Stock");
                holder.txtStock.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF10B981));
            } else {
                holder.txtStock.setText("Out of Stock");
                holder.txtStock.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFEF4444));
            }
        } else {
            holder.txtQuantity.setText("0 items");
            holder.txtStock.setText("Out of Stock");
            holder.txtStock.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFEF4444));
        }

        String imageUrl = item.getFoto();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.product_placeholder)
                    .error(R.drawable.product_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            
            try {
                if (imageUrl.startsWith("drawable://") || imageUrl.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                    try {
                        int resourceId = context.getResources().getIdentifier(
                            imageUrl.replace("drawable://", ""), 
                            "drawable", 
                            context.getPackageName()
                        );
                        if (resourceId != 0) {
                            Glide.with(context)
                                    .load(resourceId)
                                    .apply(requestOptions)
                                    .into(holder.imgProduct);
                        } else {
                            holder.imgProduct.setImageResource(R.drawable.product_placeholder);
                        }
                    } catch (Exception e) {
                        holder.imgProduct.setImageResource(R.drawable.product_placeholder);
                    }
                } else if (imageUrl.startsWith("content://")) {
                    try {
                        Uri contentUri = Uri.parse(imageUrl);
                        context.getContentResolver().takePersistableUriPermission(
                            contentUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                        Glide.with(context)
                                .load(contentUri)
                                .apply(requestOptions)
                                .into(holder.imgProduct);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Failed to load gallery image", Toast.LENGTH_SHORT).show();
                        holder.imgProduct.setImageResource(R.drawable.product_placeholder);
                    }
                } else {
                    Glide.with(context)
                            .load(imageUrl)
                            .apply(requestOptions)
                            .into(holder.imgProduct);
                }
            } catch (Exception e) {
                e.printStackTrace();
                holder.imgProduct.setImageResource(R.drawable.product_placeholder);
            }
        } else {
            holder.imgProduct.setImageResource(R.drawable.product_placeholder);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, InputData.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("nama", item.getNama());
            intent.putExtra("kategori", item.getKategori());
            intent.putExtra("harga", item.getHarga());
            intent.putExtra("jumlah", item.getJumlah());
            intent.putExtra("foto", item.getFoto());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Konfirmasi")
                    .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        db.deletData(item.getId());
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