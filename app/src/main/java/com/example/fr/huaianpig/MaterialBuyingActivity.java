package com.example.fr.huaianpig;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FR on 2017/5/26.
 */
public class MaterialBuyingActivity extends Activity {

    private Spinner s,s1;
    private int spinnerID=-1,spinnerID1=-1;
    private Handler handler1;
    private Button query;
    private Button query2;
    private String mResponse;
    private String mResponse1;
//    private String path = "http://192.168.43.222:9999/myApps";
    private QuanjubianliangActivity path;
    String[] data=new String[]{"饲料购入","药品购入","疫苗购入"};
    private String[] data1 = new String[]{""};//错在这里,这句是干什么的。
    private List<String> data2 = new ArrayList<String>();
    private EditText price;
    private EditText sumprice;
    private EditText count;
    private EditText des;
    private String[] arr;
    private TextView showDate = null;
    private Button pickDate = null;
    private static final int DATE_DIALOG_ID = 0;
    private static final int SHOW_DATAPICK = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView showDate1 = null;
    private Button pickDate1 = null;
    private static final int DATE_DIALOG_ID1 = 2;
    private static final int SHOW_DATAPICK1 = 3;
    private int mYear1;
    private int mMonth1;
    private int mDay1;
    ArrayList<String> list;

    //编码问题
    private String str111;
//    饲料购入药品购入疫苗购入
    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.materialbuying_layout);
        path = (QuanjubianliangActivity)getApplication();
        Button title_set_bn = (Button) findViewById(R.id.title_set_bn);
        title_set_bn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MaterialBuyingActivity.this, PigplaceActivity.class);
                startActivity(intent);
                finish();
            }
        });
        price = (EditText)findViewById(R.id.price_et);
        sumprice = (EditText)findViewById(R.id.sumprice_et);
        count = (EditText)findViewById(R.id.count_et);
        des = (EditText)findViewById(R.id.Des_et);
        query = (Button)findViewById(R.id.query);
        query.setOnClickListener(clickListener_query);
        query2 = (Button)findViewById(R.id.query2);
        query2.setOnClickListener(clickListener_query2);
        showDate = (TextView) findViewById(R.id.showDate);
        pickDate = (Button) findViewById(R.id.but_showDate);
        pickDate.setOnClickListener(new DateButtonOnClickListener());
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
        showDate1 = (TextView) findViewById(R.id.showDate1);
        pickDate1 = (Button) findViewById(R.id.but_showDate1);
        pickDate1.setOnClickListener(new DateButtonOnClickListener1());
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        setDateTime1();
//        Looper looper = Looper.myLooper();//
////        mHandler1 = new MyHandler(looper);//
//
//        HandlerThread handlerThread1 = new HandlerThread("myHandlerThread1");//����scoket�����߳�
//        handlerThread1.start();
//        handler1 = new Handler(handlerThread1.getLooper());
//        handler1.post(new MyRunnable());
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
//                Toast.makeText(MaterialBuyingActivity.this,Integer.toString(spinnerID1),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });//是下拉列表的监听
    }

    private View.OnClickListener clickListener_query = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new Thread() {
                @Override
                public void run() {
                    if (spinnerID>=0){
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
                }
            }.start();
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x456) {
                // Toast出服务器响应的字符串
//                Toast.makeText(MaterialBuyingActivity.this,mResponse,Toast.LENGTH_LONG).show();
                mResponse = mResponse.trim();
                arr=mResponse.split(",");
                if (arr.length>1){
                    list = new ArrayList<>();
                    for (int i = 0;i<arr.length;i++){
                        list.add(arr[i]);
                    }
                    s1.setAdapter(new ArrayAdapter<String>(MaterialBuyingActivity.this, android.R.layout.simple_list_item_1, list));
                }else {
                    Toast.makeText(MaterialBuyingActivity.this,"数据库为空！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private View.OnClickListener clickListener_query2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            new Thread() {
                @Override
                public void run() {
                    if (spinnerID1>=0){
                    mMonth = mMonth+1;
                    mMonth1 = mMonth1+1;
                        if (spinnerID==0){
                            mResponse1 = GetPostUtil.sendPost(path.getIp(), "savefeedbuyin@"+showDate.getText()+"@"+price.getText()+"@"+sumprice.getText()+"@"+ arr[spinnerID1] +"@"+count.getText()+"@"+des.getText()
                                    +"@"+showDate1.getText()+"@");
                        }else if (spinnerID==1){
                            mResponse1 = GetPostUtil.sendPost(path.getIp(), "savemedicinebuyin@"+showDate.getText()+"@"+price.getText()+"@"+sumprice.getText()+"@"+arr[spinnerID1]+"@"+count.getText()+"@"+des.getText()
                                    +"@"+showDate1.getText()+"@");
                        }else if (spinnerID==2){
                            mResponse1 = GetPostUtil.sendPost(path.getIp(), "savevaccinebuyin@"+showDate.getText()+"@"+price.getText()+"@"+sumprice.getText()+"@"+arr[spinnerID1]+"@"+count.getText()+"@"+des.getText()
                                    +"@"+showDate1.getText()+"@");
                        }
                        Log.d("1111",mResponse1);
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
//                Toast.makeText(MaterialBuyingActivity.this,mMonth,Toast.LENGTH_SHORT).show();
                mResponse1 = mResponse1.trim();
                if (mResponse1.equals("success")){
                    Toast.makeText(MaterialBuyingActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MaterialBuyingActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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

    @TargetApi(Build.VERSION_CODES.N)
    private void setDateTime1() {
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        updateDisplay1();
    }

    /**
     * 更新日期
     */

    private void updateDisplay() {
        showDate.setText(new StringBuilder().append(mYear).append("-").append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append(
                (mDay < 10) ? "0" + mDay : mDay));
    }
    private void updateDisplay1() {
        showDate1.setText(new StringBuilder().append(mYear1).append("-").append(
                (mMonth1 + 1) < 10 ? "0" + (mMonth1 + 1) : (mMonth1 + 1)).append("-").append(
                (mDay1 < 10) ? "0" + mDay1 : mDay1));
    }
    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateTime();
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view1, int year1, int monthOfYear1, int dayOfMonth1) {
            setDateTime1();
            mYear1 = year1;
            mMonth1 = monthOfYear1;
            mDay1 = dayOfMonth1;
            updateDisplay1();
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
                msg.what = MaterialBuyingActivity.SHOW_DATAPICK;
            }
            MaterialBuyingActivity.this.saleHandler.sendMessage(msg);
        }
    }
    class DateButtonOnClickListener1 implements
            android.view.View.OnClickListener {

        @Override

        public void onClick(View v) {

            Message msg1 = new Message();
            if (pickDate1.equals((Button) v)) {
                msg1.what = MaterialBuyingActivity.SHOW_DATAPICK1;
            }
            MaterialBuyingActivity.this.saleHandler1.sendMessage(msg1);
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
            case DATE_DIALOG_ID1:
                return new DatePickerDialog(this, mDateSetListener1, mYear1, mMonth1, mDay1);
        }
        return null;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
            case DATE_DIALOG_ID1:
                ((DatePickerDialog) dialog).updateDate(mYear1, mMonth1, mDay1);
                break;
        }
    }
    protected void onPrepareDialog1(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
            case DATE_DIALOG_ID1:
                ((DatePickerDialog) dialog).updateDate(mYear1, mMonth1, mDay1);
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
                case MaterialBuyingActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }

    };
    Handler saleHandler1 = new Handler() {

        @Override

        public void handleMessage(Message msg1) {
            switch (msg1.what) {
                case MaterialBuyingActivity.SHOW_DATAPICK1:
                    showDialog(DATE_DIALOG_ID1);
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
                MaterialBuyingActivity.this.finish();
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
