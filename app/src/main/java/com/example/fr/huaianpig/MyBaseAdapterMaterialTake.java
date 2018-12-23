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
public class MyBaseAdapterMaterialTake extends BaseAdapter {
    private List<ItemBeanMaterialTake> mList;
    private Context context;

    public MyBaseAdapterMaterialTake(List<ItemBeanMaterialTake> mList, Context context) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listmaterialtake_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mList.get(position).getName());
        viewHolder.count.setText(mList.get(position).getCount());
        viewHolder.time.setText(mList.get(position).getTime());
        return convertView;
    }

    class ViewHolder{
        TextView name;
        TextView count;
        TextView time;
    }
}
