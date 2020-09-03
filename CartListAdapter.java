package com.example.project;

import android.content.Context;
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

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Cart> productList;

    public CartListAdapter(Context context, int layout, ArrayList<Cart> productList) {
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
        TextView name, price;
        Button bill;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cart_item, null);

            holder.name = (TextView) row.findViewById(R.id.product_name);
            holder.price = (TextView) row.findViewById(R.id.product_price);
            holder.imageView = (ImageView) row.findViewById(R.id.product_picImg);
            row.setTag(holder);
        } else {
            holder = (CartListAdapter.ViewHolder)row.getTag();
        }

        Cart product = productList.get(position);
        holder.name.setText(product.getProductName());
        holder.price.setText(product.getProductPrice() + "SR");

        byte[] image = product.getProductPic();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(bitmap);


        return row;


    }}