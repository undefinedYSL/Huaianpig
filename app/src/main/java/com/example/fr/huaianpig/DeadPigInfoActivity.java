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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FR on 2017/7/12.
 */
public class DeadPigInfoActivity extends Activity {
    private String mResponse;
    private String[] arr;
    private String[] arr1;
    ArrayList<ItemBeansick> list=null;
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.deadpig_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeadPigInfoActivity.this, SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
        new Thread() {
            @Override
            public void run() {
                mResponse = GetPostUtil.sendPost(path.getIp(), "getdeadpiginfo@");
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x123) {
                ListView listView = (ListView) findViewById(R.id.list_mainsick);
                list = new ArrayList<>();
                    mResponse = mResponse.trim();
                    arr = mResponse.split(";");
                    if (arr.length>=1){
                        try {
                            for (int i=0;i<arr.length;i++){
                                arr1 = arr[i].split(",");
                                list.add(new ItemBeansick(arr1[0],arr1[1],arr1[2],arr1[3]));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        MyBaseAdaptersick myBaseAdapter = new MyBaseAdaptersick(list,DeadPigInfoActivity.this);
                        listView.setAdapter(myBaseAdapter);
                    }else {
                        Toast.makeText(DeadPigInfoActivity.this,"数据库为空",Toast.LENGTH_SHORT).show();
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
                DeadPigInfoActivity.this.finish();
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
