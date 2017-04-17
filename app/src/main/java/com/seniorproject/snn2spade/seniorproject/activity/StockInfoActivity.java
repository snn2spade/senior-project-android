package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.fragment.StockInfoFragment;

/**
 * Created by snn2spade on 3/18/2017 AD.
 */

public class StockInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setContentView(R.layout.activity_stock_info);
        initSearchBarListener();
        initHelpIconClickListener();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stockInfo_fragment_container,StockInfoFragment.newInstance())
                    .commit();
        }

    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent newIntent = new Intent(getBaseContext(), MainActivity.class);
            newIntent.putExtra(SearchManager.QUERY,query);
            newIntent.setAction(Intent.ACTION_SEARCH);
            startActivity(newIntent);
            finish();
            Log.d("<<<StockInfoActivity>>>","---------------- PASS SEARCH INTENT : "+query);
        }
    }

    private void initHelpIconClickListener(){
        ImageView helpView = (ImageView) findViewById(R.id.help_icon);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialIntent = new Intent(getBaseContext(),TutorialActivity.class);
                tutorialIntent.putExtra(TutorialActivity.TUTORIAL_TYPE_MESSAGE,TutorialActivity.STOCKINFO_TUTORIAL);
                startActivity(tutorialIntent);
            }
        });
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

    @Override
    protected void onStop() {
        super.onStop();
    }

}
