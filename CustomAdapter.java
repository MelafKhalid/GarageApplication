package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    Controllerdb controldb;
    SQLiteDatabase db;

    private ArrayList<String> name =new ArrayList<String>();
    private ArrayList<String> email =new ArrayList<String>();
    private ArrayList<String> phone =new ArrayList<String>();

    public CustomAdapter(Context context,ArrayList<String> name, ArrayList<String> email, ArrayList<String> phone){
        this.mContext = context;
        this.name=name;
        this.email=email;
        this.phone=phone;

    }
    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final viewHolder holder;
        controldb = new Controllerdb(mContext);
        LayoutInflater layoutInflater;

        if(convertView == null){

            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.email = (TextView) convertView.findViewById(R.id.tvemail);
            holder.phone = (TextView) convertView.findViewById(R.id.tvphone);
            convertView.setTag(holder);
        }

        else
        {
            holder = (viewHolder) convertView.getTag(); }

        holder.name.setText(name.get(position));
        holder.email.setText(email.get(position));
        holder.phone.setText(phone.get(position));
        return convertView;
    }

    public class viewHolder {

        TextView name;
        TextView email;
        TextView phone;

    }
}