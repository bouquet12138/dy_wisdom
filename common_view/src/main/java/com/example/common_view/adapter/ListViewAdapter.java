package com.example.common_view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.common_view.R;

import androidx.annotation.NonNull;


public class ListViewAdapter extends ArrayAdapter<Integer> {


    public ListViewAdapter(@NonNull Context context, int resource, @NonNull Integer[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Integer data = getItem(position);
        String str;
        if (data < 10)
            str = "0" + data;
        else
            str = "" + data;
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_string, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(str);//设置文本
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }

}
