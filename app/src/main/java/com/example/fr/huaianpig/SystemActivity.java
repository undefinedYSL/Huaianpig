package com.example.fr.huaianpig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by FR on 2017/5/25.
 */
public class SystemActivity extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private LinearLayout home_img_bn_Layout, style_img_bn_layout, cam_img_bn_layout,shopping_img_bn_layout, show_img_bn_layout;
    private TextView piginfo,sickpiginfo,deadpiginfo,materialtakeinfo,materialusinginfo,materialbuyinginfo,weighttreadinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.system_layout);
        home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
        home_img_bn_Layout.setOnClickListener(clickListener_home);

        shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
        shopping_img_bn_layout.setOnClickListener(clickListener_shopping);
        shopping_img_bn_layout.setSelected(true);

        style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
        style_img_bn_layout.setOnClickListener(clickListener_style);

        cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
        cam_img_bn_layout.setOnClickListener(clickListener_cam);

        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
        show_img_bn_layout.setOnClickListener(clickListener_show);

        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
        show_img_bn_layout.setOnClickListener(clickListener_show);

        piginfo = (TextView)findViewById(R.id.PigInf);
        piginfo.setOnClickListener(clickListener_piginfo);

        sickpiginfo = (TextView)findViewById(R.id.sickpiginfo_tv);
        sickpiginfo.setOnClickListener(clickListener_sickpiginfo);

        deadpiginfo = (TextView)findViewById(R.id.deadpiginfo_tv);
        deadpiginfo.setOnClickListener(clickListener_deadpiginfo);

        materialtakeinfo = (TextView)findViewById(R.id.materialtakeinfo_tv);
        materialtakeinfo.setOnClickListener(clickListener_materialtakeinfo);

        materialbuyinginfo = (TextView)findViewById(R.id.materialbuyinginfo_tv);
        materialbuyinginfo.setOnClickListener(clickListener_materialbuyinginfo);

        materialusinginfo = (TextView)findViewById(R.id.materialusinginfo_tv);
        materialusinginfo.setOnClickListener(clickListener_materialusinginfo);

        weighttreadinfo = (TextView)findViewById(R.id.weighttread_tv);
        weighttreadinfo.setOnClickListener(clickListener_weighttreadinfo);

    }
    private View.OnClickListener clickListener_piginfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, PigInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_sickpiginfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, SickPigInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_deadpiginfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, DeadPigInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_materialtakeinfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, MaterialTakeInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_materialbuyinginfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, MaterialBuyingInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_materialusinginfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, MaterialUsingInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
        }
    };
    private View.OnClickListener clickListener_weighttreadinfo = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, WeightTreadActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
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
            intent.setClass(SystemActivity.this, MainActivity.class);
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
            Intent intent = new Intent();
            intent.setClass(SystemActivity.this, PigmanageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoommin, R.anim.zoomout);
            finish();
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
            intent.setClass(SystemActivity.this, PigplaceActivity.class);
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
            intent.setClass(SystemActivity.this,ContactActivity.class);
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
                SystemActivity.this.finish();
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
