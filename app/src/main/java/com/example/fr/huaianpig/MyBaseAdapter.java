package com.example.fr.huaianpig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class MyBaseAdapter extends BaseAdapter {
    private List<ItemBean> mList;
    private Context context;

    public MyBaseAdapter(List<ItemBean> mList, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.id);
            viewHolder.money = (TextView) convertView.findViewById(R.id.money);
            viewHolder.data = (TextView) convertView.findViewById(R.id.time);
            viewHolder.state = (TextView) convertView.findViewById(R.id.state);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText(mList.get(position).getId());
        viewHolder.money.setText(mList.get(position).getMoney());
        viewHolder.data.setText(mList.get(position).getTime());
        viewHolder.state.setText(mList.get(position).getState());
        return convertView;
    }

    class ViewHolder{
        TextView id;
        TextView money;
        TextView data;
        TextView state;
    }
}
