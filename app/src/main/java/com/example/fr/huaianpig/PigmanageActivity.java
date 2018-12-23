package com.example.fr.huaianpig;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by FR on 2017/5/25.
 */
public class PigmanageActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private LinearLayout home_img_bn_Layout, style_img_bn_layout, cam_img_bn_layout,shopping_img_bn_layout, show_img_bn_layout;
    private GestureDetector mGestureDetector;
    private RelativeLayout con;
    private TextView ClientInfo, record,pigcure, conv, weight, card, death,sale,changeweight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pigmanage_layout);
        mGestureDetector = new GestureDetector(this);
        con = (RelativeLayout) findViewById(R.id.con);
        con.setOnTouchListener(this);
        con.setLongClickable(true);

        home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
        home_img_bn_Layout.setOnClickListener(clickListener_home);

        style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
        style_img_bn_layout.setOnClickListener(clickListener_style);
        style_img_bn_layout.setSelected(true);

        shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
        shopping_img_bn_layout.setOnClickListener(clickListener_shopping);

        cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
        cam_img_bn_layout.setOnClickListener(clickListener_cam);

        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
        show_img_bn_layout.setOnClickListener(clickListener_show);

        ClientInfo = (TextView) findViewById(R.id.ClientInf);
        ClientInfo.setOnClickListener(clickListener_ClientInfo);

        record = (TextView) findViewById(R.id.record);
        record.setOnClickListener(clickListener_record);

        pigcure = (TextView) findViewById(R.id.cure);
        pigcure.setOnClickListener(clickListener_cure);

        conv = (TextView) findViewById(R.id.conv);
        conv.setOnClickListener(clickListener_conv);

        weight = (TextView) findViewById(R.id.pigweight);
        weight.setOnClickListener(clickListener_weight);

        card = (TextView) findViewById(R.id.card);
        card.setOnClickListener(clickListener_card);

        death = (TextView) findViewById(R.id.pigdeath);
        death.setOnClickListener(clickListener_death);

        sale = (TextView) findViewById(R.id.sale);
        sale.setOnClickListener(clickListener_sale);

        changeweight = (TextView) findViewById(R.id.pigchangeweight);
        changeweight.setOnClickListener(clickListener_changeweight);

    }
    private View.OnClickListener clickListener_ClientInfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, ClientInfoActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_record = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, TradeRecordActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener clickListener_cure = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this,PigCureActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_conv = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, ConvStyActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_card = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, CardActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_weight = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, PigWeightActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_death = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, PigDeathActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_sale = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, SaleActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_changeweight = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, ChangeWeightActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener clickListener_home = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            home_img_bn_Layout.setSelected(true);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_style = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(true);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
        }
    };
    private View.OnClickListener clickListener_cam = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(true);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this, PigplaceActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_shopping = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(true);
            show_img_bn_layout.setSelected(false);
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this,SystemActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_show = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            home_img_bn_Layout.setSelected(false);
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(false);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(true);
            Intent intent = new Intent();
            intent.setClass(PigmanageActivity.this,ContactActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
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
                PigmanageActivity.this.finish();
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
}
