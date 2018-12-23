package com.example.fr.huaianpig;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by FR on 2017/5/26.
 */

//猪只信息
public class PigWeightActivity extends Activity {
    private TextView showDate = null;
    private Button pickDate = null;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_DATAPICK = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private NfcAdapter nfcAdapter;
    private TextView pigid_tv;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;
    private Button query;
    private String mResponse;
//    private String path = "http://192.168.43.235:8888/myApps";
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    private EditText pigweight;
    private EditText pigconvsty;
    private String[]arr;
    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pigweight_layout);
        path = (QuanjubianliangActivity) getApplication();
        showDate = (TextView) findViewById(R.id.showDate);
        pickDate = (Button) findViewById(R.id.but_showDate);
        pickDate.setOnClickListener(new DateButtonOnClickListener());
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
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
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PigWeightActivity.this, PigmanageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        query = (Button) findViewById(R.id.query);
        query.setOnClickListener(clickListener_query);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[] { ndef };// 过滤器
        mTechLists = new String[][] {
                new String[] { MifareClassic.class.getName() },
                new String[] { NfcA.class.getName() } };// 允许扫描的标签类型
        pigweight = (EditText) findViewById(R.id.pigweight_et);
        pigconvsty = (EditText) findViewById(R.id.pigconvsty_et);
    }

    private View.OnClickListener clickListener_query = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    mMonth = mMonth+1;
                    mResponse = GetPostUtil.sendPost(
                            path.getIp(), "piginfoin"+"@"+mYear+"-"+mMonth+"-"+mDay+"@"+pigconvsty.getText()+"@"+pigweight.getText()+"@"+pigid_tv.getText()+"@");
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
                // Toast出服务器响应的字符串
                mResponse = mResponse.trim();
//                Toast.makeText(PigWeightActivity.this,mResponse,Toast.LENGTH_SHORT).show();
                if (mResponse.equals("success")){
                    Toast.makeText(PigWeightActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PigWeightActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.N)
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
    }

    /**
     * 更新日期
     */

    private void updateDisplay() {
        showDate.setText(new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay));
    }
    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
    /**
     * 选择日期Button的事件处理
     *
     * @author Raul
     *
     */
    class DateButtonOnClickListener implements
android.view.View.OnClickListener {

        @Override

        public void onClick(View v) {

            Message msg = new Message();
            if (pickDate.equals((Button) v)) {
                msg.what = PigWeightActivity.SHOW_DATAPICK;
            }
            PigWeightActivity.this.saleHandler.sendMessage(msg);
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }
    /**
     * 处理日期控件的Handler
     */
    Handler saleHandler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case PigWeightActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
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
                PigWeightActivity.this.finish();
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
