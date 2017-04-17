package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seniorproject.snn2spade.seniorproject.R;

/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class TutorialActivity extends Activity {
    public static final String TUTORIAL_TYPE_MESSAGE="tutorial";
    public static final int HOME_TUTORIAL = 1;
    public static final int STOCKINFO_TUTORIAL = 2;
    public static final int FIN_SHEET_TUTORIAL = 3;

    private ImageView tutorialImage;
    private int tutorialType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent();
            setContentView(R.layout.tutorial_main);
            int image_res_id;
            switch (tutorialType){
                case HOME_TUTORIAL:
                    image_res_id = R.drawable.tutorial_home;
                    break;
                case STOCKINFO_TUTORIAL:
                    image_res_id = R.drawable.tutorial_stock_info;
                    break;
                case FIN_SHEET_TUTORIAL:
                    image_res_id = R.drawable.tutorial_financial_sheet;
                    break;
                default:
                    image_res_id = R.drawable.tutorial_home;
                    break;
            }
            tutorialImage = (ImageView) findViewById(R.id.tutorial_image);
            Glide.with(this).load(image_res_id).into(tutorialImage);
            initTouchListener();
        }
    }
    private void handleIntent(){
        Intent intent = getIntent();
        tutorialType = intent.getIntExtra(TUTORIAL_TYPE_MESSAGE,1);
    }
    private void initTouchListener() {
        tutorialImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                overridePendingTransition(0, 0);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(0,0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
