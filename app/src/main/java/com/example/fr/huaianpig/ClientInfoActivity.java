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
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by FR on 2017/5/25.
 */
public class ClientInfoActivity extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private RadioButton radio0, radio1, radio2;
    private RadioGroup radio;
    private int ID;
    private TextView haveordernumber;
    private int[] result;
    private Button ordernumber;
    private Spinner s;
    private int spinnerID;
    String[] data=new String[]{"猪只购入","猪只销售"};
    private String[] arr;
    private Button query;
    private EditText clientname,emails,phonenumber,time,money;
    private String order1;
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    /**
     * 服务器响应的字符串
     */
    private String mResponse;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clientinfo_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ClientInfoActivity.this, PigmanageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        clientname = (EditText) findViewById(R.id.ClientName_et);
        emails = (EditText) findViewById(R.id.Emails_et);
        phonenumber = (EditText) findViewById(R.id.PhoneNumber_et);
        time = (EditText) findViewById(R.id.Time_et);
        money = (EditText) findViewById(R.id.Money_et);
        radio0 = (RadioButton) findViewById(R.id.nothave);
        radio1 = (RadioButton) findViewById(R.id.havenotpay);
        radio2 = (RadioButton) findViewById(R.id.havepay);
        radio = (RadioGroup) findViewById(R.id.radioGroup_1);
        haveordernumber = (TextView) findViewById(R.id.haveNumber);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == radio0.getId())   /*当选择的是第1个单选按钮*/ {
                    ID = 0;
                } else if (checkedId == radio1.getId()) {
                    ID = 1;
                } else if (checkedId == radio1.getId()) {
                    ID = 2;
                }
            }
        });
        ordernumber = (Button) findViewById(R.id.orderNumber);
        ordernumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String order2 = java.util.UUID.randomUUID().toString();;
                order1 = order2.replaceAll("-", "");
                haveordernumber.setText(order1);
            }

        });

        final String sendMessage="orderin"+"@"+clientname.getText()+"@"+emails.getText()+"@"+phonenumber.getText()+"@"+time.getText()+"@"+ID+"@"+money.getText()+"@"+order1;
        Button query = (Button)findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        mResponse = GetPostUtil.sendPost(
                                path.getIp()
                                , "orderin"+"@"+clientname.getText()+"@"+emails.getText()+"@"+phonenumber.getText()+"@"+time.getText()+"@"+ID+"@"+money.getText()+"@"+order1);
                        Log.d("1111",mResponse);
                        // 发送消息通知UI线程更新UI组件
                        handler.sendEmptyMessage(0x456);
                    }
                }.start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x456) {
                // Toast出服务器响应的字符串
                Toast.makeText(ClientInfoActivity.this, "已发送", Toast.LENGTH_SHORT).show();
//                arr=mResponse.split(",");
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
               ClientInfoActivity.this.finish();
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    public int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}

