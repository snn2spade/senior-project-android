package com.seniorproject.snn2spade.seniorproject.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.snn2spade.seniorproject.manager.Contextor;

/**
 * Created by snn2spade on 3/28/2017 AD.
 */

public class ViewModifier {
    private static ViewModifier instance;

    public static ViewModifier getInstance() {
        if (instance == null)
            instance = new ViewModifier();
        return instance;
    }

    private Context mContext;

    private ViewModifier() {
        mContext = Contextor.getInstance().getContext();
    }

    public void setTextView(View v, int item_id, String txt) {
        TextView tv = (TextView) v.findViewById(item_id);
        if (tv != null) {
            tv.setText(txt);
        }
    }

    public void setColorTextView(View v, int item_id, int color) {
        TextView tv = (TextView) v.findViewById(item_id);
        if (tv != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextColor(mContext.getColor(color));
            } else {
                tv.setTextColor(mContext.getResources().getColor(color));
            }
        }
    }

    public void setImageView(View v, int item_id, int resId, int color) {
        ImageView iv = (ImageView) v.findViewById(item_id);
        if (iv != null) {
            iv.setImageResource(resId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                iv.setColorFilter(mContext.getColor(color));
            } else {
                iv.setColorFilter(mContext.getResources().getColor(color));
            }
        }
    }

}
