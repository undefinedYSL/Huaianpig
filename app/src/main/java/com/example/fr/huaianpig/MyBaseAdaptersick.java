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
public class MyBaseAdaptersick extends BaseAdapter {
    private List<ItemBeansick> mList;
    private Context context;

    public MyBaseAdaptersick(List<ItemBeansick> mList, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listsick_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.data = (TextView) convertView.findViewById(R.id.data);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.weight = (TextView) convertView.findViewById(R.id.weight);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText(mList.get(position).getId());
        viewHolder.data.setText(mList.get(position).getData());
        viewHolder.location.setText(mList.get(position).getLocation());
        viewHolder.weight.setText(mList.get(position).getWeight());
        return convertView;
    }

    class ViewHolder{
        TextView id;
        TextView data;
        TextView location;
        TextView weight;
    }
}
