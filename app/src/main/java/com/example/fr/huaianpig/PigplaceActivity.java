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
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by FR on 2017/5/25.
 */
public class PigplaceActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {


    private LinearLayout home_img_bn_Layout, style_img_bn_layout, cam_img_bn_layout,shopping_img_bn_layout, show_img_bn_layout;
    private TextView materialbuying, materialusing,materialusinglater, video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pigplace_layout);
        home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
        home_img_bn_Layout.setOnClickListener(clickListener_home);

        style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
        style_img_bn_layout.setOnClickListener(clickListener_style);

        cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
        cam_img_bn_layout.setOnClickListener(clickListener_cam);
        cam_img_bn_layout.setSelected(true);

        shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
        shopping_img_bn_layout.setOnClickListener(clickListener_shopping);

        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
        show_img_bn_layout.setOnClickListener(clickListener_show);

        materialbuying = (TextView) findViewById(R.id.materialbuying);
        materialbuying.setOnClickListener(clickListener_materialbuying);

        materialusing = (TextView) findViewById(R.id.materialusing);
        materialusing.setOnClickListener(clickListener_materialusing);

        materialusinglater = (TextView) findViewById(R.id.materialusing_later);
        materialusinglater.setOnClickListener(clickListener_materialusinglater);

        video = (TextView) findViewById(R.id.video1);
        video.setOnClickListener(clickListener_video);

    }

    private View.OnClickListener clickListener_materialbuying = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this, MaterialBuyingActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener clickListener_materialusing = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this, MaterialUsingActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener clickListener_materialusinglater = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this, MaterialUsingLaterActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener clickListener_stymanage = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this, ClientInfoActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener clickListener_video = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this,VideoActivity.class);
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
            intent.setClass(PigplaceActivity.this, MainActivity.class);
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
            style_img_bn_layout.setSelected(false);
            cam_img_bn_layout.setSelected(true);
            shopping_img_bn_layout.setSelected(false);
            show_img_bn_layout.setSelected(false);
            Intent intent = new Intent();
            intent.setClass(PigplaceActivity.this, PigmanageActivity.class);
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
            intent.setClass(PigplaceActivity.this,SystemActivity.class);
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
            intent.setClass(PigplaceActivity.this,ContactActivity.class);
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
                PigplaceActivity.this.finish();
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
