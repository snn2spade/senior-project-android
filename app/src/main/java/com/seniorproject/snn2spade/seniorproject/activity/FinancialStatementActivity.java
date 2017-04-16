package com.seniorproject.snn2spade.seniorproject.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.fragment.FinancialPositionFragment;

/**
 * Created by snn2spade on 4/16/2017 AD.
 */

public class FinancialStatementActivity extends AppCompatActivity implements FinancialPositionFragment.OnFragmentInteractionListener {
    public static final String SYMBOL_MESSAGE = "symbol";

    private FinancialPositionFragment mFinancialPositionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_statement);


        if (savedInstanceState == null) {
            mFinancialPositionFragment = FinancialPositionFragment.newInstance("","");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mFinancialPositionFragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
