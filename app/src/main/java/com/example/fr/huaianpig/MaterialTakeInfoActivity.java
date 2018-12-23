package com.example.fr.huaianpig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FR on 2017/7/12.
 */
public class MaterialTakeInfoActivity extends Activity {
//    private String path = "http://192.168.43.235:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    String[] data=new String[]{"饲料领用","药品领用","疫苗领用"};
    private Spinner s;
    private int spinnerID;
    private String mResponse;
    private Button query;
    private String[] arr;
    private String[] arr1;
    ArrayList<ItemBeanMaterialTake> list=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.materialtakeinfo_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MaterialTakeInfoActivity.this, SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
        s = (Spinner) this.findViewById(R.id.choose);
        s.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));//android.R.layout.simple_list_item_1是指安卓自带的下拉列表格式，data是数据源；
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerID = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//是下拉列表的监听
        query = (Button)findViewById(R.id.query);
        query.setOnClickListener(clickListener_query);
    }
    private View.OnClickListener clickListener_query = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinnerID == 0){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getfeedtakeinfo@");
                        handler.sendEmptyMessage(0x456);
                    }else if(spinnerID == 1){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getmedicinetakeinfo@");
                        handler.sendEmptyMessage(0x456);
                    }else if (spinnerID == 2){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getvaccinetakeinfo@");
                        // 发送消息通知UI线程更新UI组件
                        handler.sendEmptyMessage(0x456);
                    }
                }
            }.start();
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                ListView listView = (ListView) findViewById(R.id.list_mainmaterialtake);
                list = new ArrayList<>();
                mResponse = mResponse.trim();
                arr = mResponse.split(";");
                if (arr.length>1){
                    for (int i=0;i<arr.length;i++){
                        arr1 = arr[i].split(",");
                        list.add(new ItemBeanMaterialTake(arr1[0],arr1[1],arr1[2]));
                    }
                    MyBaseAdapterMaterialTake myBaseAdapter = new MyBaseAdapterMaterialTake(list,MaterialTakeInfoActivity.this);
                    listView.setAdapter(myBaseAdapter);
                }else {
                    Toast.makeText(MaterialTakeInfoActivity.this,"数据库为空",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            dialog();
            return true;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }
    protected void dialog()
    {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("智能猪场管理终端").setMessage(
                "确认退出应用程序？").setPositiveButton("退出",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                MaterialTakeInfoActivity.this.finish();
            }
        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        }).create();
        dialog.show();
    }
}
