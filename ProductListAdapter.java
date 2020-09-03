package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    SQLiteDatabase database;
    private ArrayList<Product> productList;

    public ProductListAdapter(Context context, int layout, ArrayList<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView name, price, desc;
        Button add_to_cart;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.name = (TextView) row.findViewById(R.id.product_nameTV);
            holder.desc = (TextView) row.findViewById(R.id.product_descTV);
            holder.price = (TextView) row.findViewById(R.id.product_priceTV);
            holder.imageView = (ImageView) row.findViewById(R.id.product_pic);
            holder.add_to_cart = (Button) row.findViewById(R.id.add_to_cart);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Product product = productList.get(position);
        holder.name.setText(product.getProductName());
        holder.desc.setText(product.getProductDesc());
        holder.price.setText(product.getProductPrice() + "SR");

        byte[] image = product.getProductPic();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddToCart.class);
                intent.putExtra("name", productList.get(position).getProductName());
                intent.putExtra("price", productList.get(position).getProductPrice());
                intent.putExtra("pic", productList.get(position).getProductPic());

                view.getContext().startActivity(intent);
            }
        });

        return row;
    }
}
