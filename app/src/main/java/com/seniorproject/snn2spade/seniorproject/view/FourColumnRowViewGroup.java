package com.seniorproject.snn2spade.seniorproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seniorproject.snn2spade.seniorproject.R;

/**
 * Created by snn2spade on 3/30/2017 AD.
 */

public class FourColumnRowViewGroup extends LinearLayout {
    private TextView tvColumn1;
    private TextView tvColumn2;
    private TextView tvColumn3;
    private TextView tvColumn4;
    public FourColumnRowViewGroup(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public FourColumnRowViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public FourColumnRowViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    public FourColumnRowViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    public void initInflate(){
        inflate(getContext(), R.layout.viewgroup_four_column_row,this);
    }

    public void initInstance(){
        tvColumn1 = (TextView) findViewById(R.id.four_column_1);
        tvColumn2 = (TextView) findViewById(R.id.four_column_2);
        tvColumn3 = (TextView) findViewById(R.id.four_column_3);
        tvColumn4 = (TextView) findViewById(R.id.four_column_4);
        setTextPair1("","");
        setTextPair2("","");
    }

    public void setTextPair1(String txt1,String txt2){
        tvColumn1.setText(txt1);
        tvColumn2.setText(txt2);
    }

    public void setTextPair2(String txt1,String txt2){
        tvColumn3.setText(txt1);
        tvColumn4.setText(txt2);
    }
}
