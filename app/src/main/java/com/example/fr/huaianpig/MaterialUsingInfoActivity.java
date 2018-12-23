package com.example.fr.huaianpig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FR on 2017/7/12.
 */
public class MaterialUsingInfoActivity extends Activity {
//    private String path = "http://192.168.43.222:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    String[] data=new String[]{"饲料使用","药品使用","疫苗使用"};
    private Spinner s;
    private int spinnerID;
    private String mResponse;
    private Button chaxun;
    private Button chaxun1;
    private String[] arr;
    private String[] arr1;
    private NfcAdapter nfcAdapter;
    private TextView pigid_tv;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    ArrayList<ItemBeanMaterialTake> list=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.materialusinginfo_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MaterialUsingInfoActivity.this, SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // 获取nfc适配器，判断设备是否支持NFC功能
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, getResources().getString(R.string.no_nfc),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, getResources().getString(R.string.open_nfc),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // 显示结果Text
        pigid_tv = (TextView) findViewById(R.id.pigid_tv);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[] { ndef };// 过滤器
        mTechLists = new String[][] {
                new String[] { MifareClassic.class.getName() },
                new String[] { NfcA.class.getName() } };// 允许扫描的标签类型
        chaxun1 = (Button)findViewById(R.id.chaxun1);
        chaxun1.setOnClickListener(clickListener_chaxun1);
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
//        chaxun = (Button)findViewById(R.id.chaxun);
//        chaxun.setOnClickListener(clickListener_chaxun);
    }
    private View.OnClickListener clickListener_chaxun1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinnerID == 0){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getfeedpiginfo@"+pigid_tv.getText()+"@");
                        handler.sendEmptyMessage(0x456);
                    }else if(spinnerID == 1){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getmedicinepiginfo@"+pigid_tv.getText()+"@");
                        handler.sendEmptyMessage(0x456);
                    }else if (spinnerID == 2){
                        mResponse = GetPostUtil.sendPost(
                                path.getIp(), "getvaccinepiginfo@"+pigid_tv.getText()+"@");
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
                ListView listView = (ListView) findViewById(R.id.list_mainmaterialusing);
                list = new ArrayList<>();
                mResponse = mResponse.trim();
                if (mResponse!=null){
                    arr = mResponse.split(";");
//                Toast.makeText(MaterialUsingInfoActivity.this,mResponse, Toast.LENGTH_SHORT).show();
                    if (arr.length>=1){
                        try {
                            for (int i=0;i<arr.length;i++){
                                arr1 = arr[i].split(",");
                                list.add(new ItemBeanMaterialTake(arr1[0],arr1[1],arr1[2]));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        MyBaseAdapterMaterialTake myBaseAdapter = new MyBaseAdapterMaterialTake(list,MaterialUsingInfoActivity.this);
                        listView.setAdapter(myBaseAdapter);
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MaterialUsingInfoActivity.this,"数据读取错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MaterialUsingInfoActivity.this,"数据库为空", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                MaterialUsingInfoActivity.this.finish();
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
    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
                mTechLists);
        if (isFirst) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                String result = processIntent(getIntent());
                pigid_tv.setText(result);
            }
            isFirst = false;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            String result = processIntent(intent);
            pigid_tv.setText(result);
        }
    }

    /**
     * 获取tab标签中的内容
     *
     * @param intent
     * @return
     */
    @SuppressLint("NewApi")
    private String processIntent(Intent intent) {
        Parcelable[] rawmsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawmsgs[0];
        NdefRecord[] records = msg.getRecords();
        String resultStr = new String(records[0].getPayload());
        return resultStr;
    }
}
