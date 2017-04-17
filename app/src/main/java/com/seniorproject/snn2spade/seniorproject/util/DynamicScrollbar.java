package com.seniorproject.snn2spade.seniorproject.util;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.seniorproject.snn2spade.seniorproject.R;

public class DynamicScrollbar {

    private static DynamicScrollbar instance;
    private static boolean isMinHeightActionBar;

    public static DynamicScrollbar getInstance() {
        if (instance == null) {
            instance = new DynamicScrollbar();
            isMinHeightActionBar = false;
        }
        return instance;
    }

    public void recalculateActionBar(Activity act, int dy) {
        if (act != null && act.findViewById(R.id.action_bar_container) != null) {
            if (dy <= 500) {
                LinearLayout ll = (LinearLayout) act.findViewById(R.id.action_bar_container);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
                int height = (int) ((500 - dy) / 500.0 * 30) + 50;
                params.height = Utils.getInstance().dpToPx(height);
                params.width = ll.getWidth();
                ll.setLayoutParams(params);
                ll.invalidate();
                isMinHeightActionBar = false;
            } else if (!isMinHeightActionBar && dy > 500) {
                LinearLayout ll = (LinearLayout) act.findViewById(R.id.action_bar_container);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
                params.height = Utils.getInstance().dpToPx(50);
                params.width = ll.getWidth();
                ll.setLayoutParams(params);
                ll.invalidate();
                isMinHeightActionBar = true;
            }
        }
    }

    public void resizeActionBarSize(Activity act, int dp) {
        LinearLayout ll = (LinearLayout) act.findViewById(R.id.action_bar_container);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = Utils.getInstance().dpToPx(dp);
        params.width = ll.getWidth();
        ll.setLayoutParams(params);
        ll.invalidate();
        if (dp <= 50) isMinHeightActionBar = true;
    }
}
