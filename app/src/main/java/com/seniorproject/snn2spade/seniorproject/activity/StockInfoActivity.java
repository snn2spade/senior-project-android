package com.seniorproject.snn2spade.seniorproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.fragment.StockInfoFragment;

/**
 * Created by snn2spade on 3/18/2017 AD.
 */

public class StockInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stockInfo_fragment_container,StockInfoFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
