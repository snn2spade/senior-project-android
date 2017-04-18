package com.seniorproject.snn2spade.seniorproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.util.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by snn2spade on 3/30/2017 AD.
 */

public class HisTradingRowViewGroup extends LinearLayout {
    private TextView tvDate;
    private TextView tvClose;
    private TextView tvChg;
    private TextView tvVolume;
    public HisTradingRowViewGroup(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public HisTradingRowViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public HisTradingRowViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    public HisTradingRowViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate(){
        inflate(getContext(), R.layout.viewgroup_his_trading_row,this);
    }

    private void initInstance(){
        tvDate = (TextView) findViewById(R.id.his_date);
        tvClose = (TextView) findViewById(R.id.his_close);
        tvChg = (TextView) findViewById(R.id.his_percent_chg);
        tvVolume = (TextView) findViewById(R.id.his_volume);
    }

    public void setDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tvDate.setText(df.format(date).toString());
    }

    public void setClose(Double closePrice){
        try{
            String cp = Utils.getInstance().convertDoubleToString(closePrice,2);
            tvClose.setText(cp);
        }
        catch (NullPointerException e){
            tvClose.setText("N/A");
        }
    }

    public void setChg(Double percentChg){
        try{
            String chg = Utils.getInstance().convertDoubleToString(percentChg,1);
            chg = Utils.getInstance().insertTrendingSign(chg);
            tvChg.setText(chg+"%");
            if(percentChg>=0){
                tvChg.setTextColor(Contextor.getInstance().getContext().getResources().getColor(R.color.colorTrendUp));
            }
            else{
                tvChg.setTextColor(Contextor.getInstance().getContext().getResources().getColor(R.color.colorTrendDown));
            }
        }
        catch (NullPointerException e){
            tvChg.setText("N/A");
            tvChg.setTextColor(Contextor.getInstance().getContext().getResources().getColor(R.color.colorTrendDown));
        }
    }

    public void setVolume(Double volume){
        try{
            String vol = Utils.getInstance().convertMillionUnit(volume);
            tvVolume.setText(vol+" MB");
        }
        catch (NullPointerException e){
            tvVolume.setText("N/A");
        }
    }
}
