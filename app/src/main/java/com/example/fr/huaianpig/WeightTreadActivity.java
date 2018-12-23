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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FR on 2017/7/12.
 */
public class WeightTreadActivity extends Activity {
    private NfcAdapter nfcAdapter;
    private TextView pigid_tv;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    private Button chaxun;
//    private String path = "http://192.168.43.235:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    private String mResponse;
    private String[] arr;
    private String[] arr1;
    ArrayList<ItemBeanWeightTread> list=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weighttread_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(WeightTreadActivity.this, SystemActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
        chaxun = (Button) findViewById(R.id.chaxun);
        chaxun.setOnClickListener(clickListener_chaxun);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[] { ndef };// 过滤器
        mTechLists = new String[][] {
                new String[] { MifareClassic.class.getName() },
                new String[] { NfcA.class.getName() } };// 允许扫描的标签类型
    }

    private View.OnClickListener clickListener_chaxun = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    mResponse = GetPostUtil.sendPost(path.getIp(), "getpigweightchange@"+pigid_tv.getText()+"@");
                    Log.d("1111",mResponse);
                    // 发送消息通知UI线程更新UI组件
                    handler.sendEmptyMessage(0x456);
                }
            }.start();
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                ListView listView = (ListView) findViewById(R.id.list_weighttread);
                list = new ArrayList<>();
                mResponse = mResponse.trim();
                arr = mResponse.split(";");
                if (arr.length>=1){
                    for (int i=0;i<arr.length;i++){
                        arr1 = arr[i].split(",");
                        list.add(new ItemBeanWeightTread(arr1[0],arr1[1]));
                    }
                    MyBaseAdapterWeightTread myBaseAdapter = new MyBaseAdapterWeightTread(list,WeightTreadActivity.this);
                    listView.setAdapter(myBaseAdapter);
                }else {
                    Toast.makeText(WeightTreadActivity.this,"数据库为空",Toast.LENGTH_SHORT).show();
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
                WeightTreadActivity.this.finish();
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
