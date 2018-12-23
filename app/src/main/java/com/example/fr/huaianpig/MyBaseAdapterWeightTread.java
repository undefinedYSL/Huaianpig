package com.example.fr.huaianpig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by FR on 2017/7/13.
 */
public class MyBaseAdapterWeightTread extends BaseAdapter {
    private List<ItemBeanWeightTread> mList;
    private Context context;

    public MyBaseAdapterWeightTread(List<ItemBeanWeightTread> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layoutweighttread, null);
            viewHolder = new ViewHolder();
            viewHolder.weight = (TextView) convertView.findViewById(R.id.weight);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.weight.setText(mList.get(position).getWeight());
        viewHolder.time.setText(mList.get(position).getTime());
        return convertView;
    }

    class ViewHolder {
        TextView weight;
        TextView time;
    }
}