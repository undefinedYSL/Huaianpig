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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FR on 2017/5/26.
 */
public class PigCureActivity extends Activity {

    private String mResponse;
    private Button query;
//    private String path = "http://192.168.43.222:8888/myApps";
    private NfcAdapter nfcAdapter;
    private TextView pigid_tv;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    private EditText count_et;
    private RadioButton cure = null;
    private RadioButton sick = null;
    private int state;
//    String path="http://192.168.43.235:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    URL url=null;
    HttpURLConnection urlConn=null;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pigsick_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PigCureActivity.this, PigmanageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        count_et = (EditText)findViewById(R.id.count_et);
        query = (Button)findViewById(R.id.query);
        query.setOnClickListener(clickListener_query);
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
        sick = (RadioButton)findViewById(R.id.sick);
        cure = (RadioButton)findViewById(R.id.cure);
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
                sick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // TODO Auto-generated method stub
                        if (buttonView.isChecked()) {
                            state = 1;
                        }
                    }
                });
                cure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // TODO Auto-generated method stub
                        if (buttonView.isChecked()) {
                            state = 0;
                        }
                    }
                });
    }
    private View.OnClickListener clickListener_query = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run(){
                if (state == 0){
                    mResponse = GetPostUtil.sendPost(path.getIp(), "savepigishealth@"+pigid_tv.getText()+"@");
                    Log.d("1111",mResponse);
                    handler2.sendEmptyMessage(0x456);
                }else if (state == 1){
                    mResponse = GetPostUtil.sendPost(path.getIp(), "savepigillinfo@"+pigid_tv.getText()+"@"+count_et.getText()+"@");
                    Log.d("1111",mResponse);
                    handler2.sendEmptyMessage(0x456);
                }
                }
            }.start();
        }
    };
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                mResponse = mResponse.trim();
                if (mResponse.equals("success")){
                    Toast.makeText(PigCureActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PigCureActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void setonchecked1() {


    }

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
    public String readInputStream(InputStream is) throws IOException
    {
        String temp = null;

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        int len=0;
        byte []buffer =new byte[1024];
        if((len=is.read(buffer))!=-1)
        {
            baos.write(buffer,0,len);
        }
        is.close();
        baos.close();
        byte[]result=baos.toByteArray();
        temp=new String(result);
        return temp;

    }
    public void  sendMessage_toServer(String str){
        final String command_String =str;
        Thread myThread=new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    url =new URL(path.getIp());

                    urlConn=(HttpURLConnection) url.openConnection();
                    urlConn.setConnectTimeout(5000);
                    urlConn.setDoOutput(true);
                    urlConn.setDoInput(true);
                    urlConn.setRequestMethod("GET");
                    // TODO Auto-generated method stub
                    OutputStream out =urlConn.getOutputStream();
                    out.write(command_String.getBytes());
                    //count--;
                    out.flush();
                    while(urlConn.getContentLength()!=-1){
                        int code=urlConn.getResponseCode();
                        if(code==200)
                        {
                            Toast.makeText(PigCureActivity.this, "控制指令已发送",Toast.LENGTH_LONG ).show();
                            urlConn.disconnect();
                            break;
                        }
                    }
                }catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }catch(Exception e2)
                {
                    e2.printStackTrace();
                }

            }
        });
        myThread.start();

    }
    protected void dialog()
    {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("智能猪场管理终端").setMessage(
                "确认退出应用程序？").setPositiveButton("退出",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                PigCureActivity.this.finish();
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
