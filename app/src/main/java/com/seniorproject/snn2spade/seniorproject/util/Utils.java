package com.seniorproject.snn2spade.seniorproject.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.provider.Settings;

import com.seniorproject.snn2spade.seniorproject.manager.Contextor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {

    private static Utils instance;

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();
        return instance;
    }

    private Context mContext;

    private Utils() {
        mContext = Contextor.getInstance().getContext();
    }


    public String getDeviceId() {
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getVersionName() {
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            return "1.0";
        }
    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public String convertMillionUnit(Double val) {
        if (val == null) {
            return "N/A";
        }
        val = val / 1000000.0;
        return new DecimalFormat("###,###.#").format(val).toString();
    }

    public String convertDoubleToString(Double val,int digit) {
        if (val == null) {
            return "N/A";
        } else {
            try {
                DecimalFormat nf = new DecimalFormat("###,###");
                nf.setMaximumFractionDigits(digit);
                nf.setMinimumFractionDigits(digit);
                nf.setMinimumIntegerDigits(1);
                return nf.format(val);
            }catch (NumberFormatException e){
                return  "N/A";
            }
        }
    }

    public String insertTrendingSign(String val){
        try{
            Double val_d = Double.parseDouble(val);
            if(val_d>=0){
                return "+"+val;
            }
            else{
                return val;
            }

        }catch (NumberFormatException e){
            return "N/A";
        }
    }

}
