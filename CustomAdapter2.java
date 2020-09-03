package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter2 extends BaseAdapter {
    private Context mContext;
    SalesTableDatabaseController controldb;
    SQLiteDatabase db;

    private ArrayList<String> user_id = new ArrayList<String>();
    private ArrayList<String> product_id = new ArrayList<String>();
    private ArrayList<String> price = new ArrayList<String>();

    public CustomAdapter2(Context context, ArrayList<String> user_id, ArrayList<String> product_id, ArrayList<String> price) {
        this.mContext = context;

        this.user_id = user_id;
        this.product_id = product_id;
        this.price = price;
    }

    @Override
    public int getCount() {
        return user_id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CustomAdapter2.viewHolder holder;
        controldb = new SalesTableDatabaseController(mContext);
        LayoutInflater layoutInflater;

        if (convertView == null) {

            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout2, null);
            holder = new CustomAdapter2.viewHolder();

            holder.user_id = (TextView) convertView.findViewById(R.id.tvuser_id);
            holder.product_id = (TextView) convertView.findViewById(R.id.tvproduct_id);
            holder.price = (TextView) convertView.findViewById(R.id.tvprice);
            convertView.setTag(holder);
        } else {
            holder = (CustomAdapter2.viewHolder) convertView.getTag();
        }

        holder.user_id.setText(user_id.get(position));
        holder.product_id.setText(product_id.get(position));
        holder.price.setText(price.get(position));
        return convertView;
    }

    public class viewHolder {

        TextView user_id;
        TextView product_id;
        TextView price;
    }
}