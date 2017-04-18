package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.fragment.FinancialPositionFragment;

/**
 * Created by snn2spade on 4/16/2017 AD.
 */

public class FinancialStatementActivity extends AppCompatActivity implements FinancialPositionFragment.OnFragmentInteractionListener {
    public static final String SYMBOL_MESSAGE = "symbol";
    public static final int FIN_POS_BUTTON = 0;
    public static final int COMP_INC_BUTTON = 1;
    public static final int CASH_FLOW_BUTTON = 2;

    private FinancialPositionFragment[] mFinancialPositionFragment;

    private int current_button_state;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setContentView(R.layout.activity_financial_statement);
        initSearchBarListener();
        initButtonListener();
        initHelpIconClickListener();
        initOnClickLogoListener();
        if (savedInstanceState == null) {
            mFinancialPositionFragment = new FinancialPositionFragment[3];
            mFinancialPositionFragment[0] = FinancialPositionFragment.newInstance(FinancialPositionFragment.FIN_POS_TYPE, "");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mFinancialPositionFragment[0])
                    .commit();
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent newIntent = new Intent(getBaseContext(), MainActivity.class);
            newIntent.putExtra(SearchManager.QUERY, query);
            newIntent.setAction(Intent.ACTION_SEARCH);
            startActivity(newIntent);
            finish();
            Log.d("<<<StockInfoActivity>>>", "---------------- PASS SEARCH INTENT : " + query);
        }
    }

    private void initOnClickLogoListener() {
        ImageView logo = (ImageView) findViewById(R.id.logo_icon);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewHomePageActivity();
            }
        });
    }

    private void insertNewHomePageActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initSearchBarListener() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchBar = (SearchView) findViewById(R.id.search_bar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setIconified(false);
            }
        });
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    private void initHelpIconClickListener() {
        ImageView helpView = (ImageView) findViewById(R.id.help_icon);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialIntent = new Intent(getBaseContext(), TutorialActivity.class);
                tutorialIntent.putExtra(TutorialActivity.TUTORIAL_TYPE_MESSAGE, TutorialActivity.FIN_SHEET_TUTORIAL);
                startActivity(tutorialIntent);
            }
        });
    }

    private void initButtonListener() {
        current_button_state = FIN_POS_BUTTON;
        setButtonListener(R.id.fin_fin_pos_button, FIN_POS_BUTTON);
        setButtonListener(R.id.fin_comp_inc_button, COMP_INC_BUTTON);
        setButtonListener(R.id.fin_cash_flow_button, CASH_FLOW_BUTTON);
    }

    private void setButtonListener(int textView_id, final int button_state) {
        final TextView tv = (TextView) findViewById(textView_id);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangeButtonState(button_state);
            }
        });
    }

    public void handleChangeButtonState(int button_state) {
        if (current_button_state != button_state) {
            current_button_state = button_state;
            setActiveButtonStyle(button_state);
            if (mFinancialPositionFragment[button_state] == null) {
                switch (button_state) {
                    case 0:
                        mFinancialPositionFragment[button_state] = FinancialPositionFragment.newInstance(FinancialPositionFragment.FIN_POS_TYPE, "");
                        break;
                    case 1:
                        mFinancialPositionFragment[button_state] = FinancialPositionFragment.newInstance(FinancialPositionFragment.COMP_INCOME_TYPE, "");
                        break;
                    case 2:
                        mFinancialPositionFragment[button_state] = FinancialPositionFragment.newInstance(FinancialPositionFragment.CASH_FLOW_TYPE, "");
                        break;
                    default:
                        mFinancialPositionFragment[button_state] = FinancialPositionFragment.newInstance(FinancialPositionFragment.FIN_POS_TYPE, "");
                        break;
                }

            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mFinancialPositionFragment[button_state])
                    .commit();
        }
    }

    private void setActiveButtonStyle(int button_state) {
        TextView tv0 = (TextView) findViewById(R.id.fin_fin_pos_button);
        TextView tv1 = (TextView) findViewById(R.id.fin_comp_inc_button);
        TextView tv2 = (TextView) findViewById(R.id.fin_cash_flow_button);
        switch (button_state) {
            case 0:
                tv0.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorTrendDown));
                tv1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                tv2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                break;
            case 1:
                tv0.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                tv1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorTrendDown));
                tv2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                break;
            case 2:
                tv0.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                tv1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.darkGrey));
                tv2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.colorTrendDown));
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
