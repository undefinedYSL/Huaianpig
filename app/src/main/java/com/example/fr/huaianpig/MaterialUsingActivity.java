package com.example.fr.huaianpig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FR on 2017/5/26.
 */
public class MaterialUsingActivity extends Activity {

    private Spinner s,s1;
    private int spinnerID,spinnerID1;
    String[] data=new String[]{"饲料领用","药品领用","疫苗领用"};
//    private String path = "http://192.168.43.235:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    private String[] data1 = new String[]{""};
    private EditText count;
    private Button chaxun;
    private Button query;
    private String mResponse;
    private String mResponse1;
    private String[] arr;
    private TextView pigid_tv;
    ArrayList<String> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.materialusing_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MaterialUsingActivity.this, PigplaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        chaxun = (Button)findViewById(R.id.chaxun);
        chaxun.setOnClickListener(clickListener_chaxun);
        query = (Button)findViewById(R.id.query);
        query.setOnClickListener(clickListener_query);
        count = (EditText)findViewById(R.id.usingcount_et);
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
        s1 = (Spinner) this.findViewById(R.id.choosetype);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerID1 = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//是下拉列表的监听
    }

    private View.OnClickListener clickListener_chaxun = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinnerID == 0){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getfeedlist"+"@");
                        handler.sendEmptyMessage(0x456);
                    }else if(spinnerID == 1){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getmedicinelist"+"@");
                        handler.sendEmptyMessage(0x456);
                    }else if (spinnerID == 2){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getvaccinelist"+"@");
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
                // Toast出服务器响应的字符串
                mResponse = mResponse.trim();
                arr=mResponse.split(",");
                if (arr.length>=1){
                    list = new ArrayList<>();
                    for (int i = 0;i<arr.length;i++){
                        list.add(arr[i]);
                    }
                    s1.setAdapter(new ArrayAdapter<String>(MaterialUsingActivity.this, android.R.layout.simple_list_item_1, list));
                }else {
                    Toast.makeText(MaterialUsingActivity.this,"数据库为空！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnClickListener clickListener_query = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinnerID==0){
                        mResponse1 = GetPostUtil.sendPost(path.getIp(), "savefeedtake@"+arr[spinnerID1]+"@"+count.getText()+"@");
                    }else if (spinnerID==1){
                        mResponse1 = GetPostUtil.sendPost(path.getIp(), "savemedicinetake@"+arr[spinnerID1]+"@"+count.getText()+"@");
                    }else if (spinnerID==2){
                        mResponse1 = GetPostUtil.sendPost(path.getIp(), "savevaccinetake@"+arr[spinnerID1]+"@"+count.getText()+"@");
                    }
                    Log.d("1111",mResponse1);
                    handler2.sendEmptyMessage(0x456);

                }
            }.start();
        }
    };
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                mResponse1 = mResponse1.trim();
                if (mResponse1.equals("success")){
                    Toast.makeText(MaterialUsingActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MaterialUsingActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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
                MaterialUsingActivity.this.finish();
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
