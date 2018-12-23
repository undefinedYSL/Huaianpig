package com.example.fr.huaianpig;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
 * Created by FR on 2017/5/26.
 */
public class TradeRecordActivity extends AppCompatActivity {
    String[] data=new String[]{};
    private String[] arr;
    private String[] arr1;
    private String[] arr2;
    private Spinner s;
    private String mResponse;
    private String mResponse1;
    private Handler handler1;
    private Button chaxun;
    private int spinner = -1;
    private Spinner mSpinner;
    private ListView mListView;
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
//    ArrayList<ItemBean> list=null;
    ArrayList<ItemBean> list;
    ArrayList<String> list1;
    private String tradestate = "";
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.traderecord_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TradeRecordActivity.this, PigmanageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        chaxun = (Button)findViewById(R.id.chaxun);
        chaxun.setOnClickListener(clickListener_chaxun);
        Looper looper = Looper.myLooper();//
//        mHandler1 = new MyHandler(looper);//

        HandlerThread handlerThread1 = new HandlerThread("myHandlerThread1");//����scoket�����߳�
        handlerThread1.start();
        handler1 = new Handler(handlerThread1.getLooper());
        handler1.post(new MyRunnable());
        s = (Spinner) this.findViewById(R.id.choose);
      //android.R.layout.simple_list_item_1是指安卓自带的下拉列表格式，data是数据源；
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//是下拉列表的监听
        //创建一个SimpleAdapter对象


    }


    private View.OnClickListener clickListener_chaxun = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinner>=0) {
                        mResponse1 = GetPostUtil.sendPost(path.getIp(), "findorder@" + list1.get(spinner));
                        handler2.sendEmptyMessage(0x123);
                    }else {
                        Toast.makeText(TradeRecordActivity.this,"数据库为空！",Toast.LENGTH_SHORT).show();
                    }
                }
            }.start();
        }
    };
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x123) {
                ListView listView = (ListView) findViewById(R.id.list_main);
                list = new ArrayList<>();
                mResponse1 = mResponse1.trim();
                arr1 = mResponse1.split(",");
                if (arr1.length>1){
                    for (int i=0;i<arr1.length;i++){
                        arr2 = arr1[i].split(" ");
                        if (arr2[3].equals("0")){
                            tradestate = "未创建订单";
                        }else if (arr2[3].equals("1")){
                            tradestate = "未付款";
                        }else if (arr2[3].equals("2")){
                            tradestate = "已付款";
                        }
                        list.add(new ItemBean(arr2[0],arr2[4],arr2[1],tradestate));
                    }
                    MyBaseAdapter myBaseAdapter = new MyBaseAdapter(list,TradeRecordActivity.this);
                    listView.setAdapter(myBaseAdapter);
                }else {
                    Toast.makeText(TradeRecordActivity.this,"数据库为空！",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


        class MyRunnable implements Runnable
    {
        @Override
        public void run() {
            mResponse = GetPostUtil.sendPost(path.getIp(), "findorderusername@");
            handler.sendEmptyMessage(0x456);

}
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                mResponse = mResponse.trim();
                arr=mResponse.split(",");
                if (arr.length>=1){
                    list1 = new ArrayList<>();
                    for (int i = 0;i<arr.length;i++){
                        list1.add(arr[i]);
                    }
                    s.setAdapter(new ArrayAdapter<String>(TradeRecordActivity.this, android.R.layout.simple_list_item_1, list1));
                }else {
                    Toast.makeText(TradeRecordActivity.this,"数据库为空！",Toast.LENGTH_SHORT).show();
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
                TradeRecordActivity.this.finish();
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
