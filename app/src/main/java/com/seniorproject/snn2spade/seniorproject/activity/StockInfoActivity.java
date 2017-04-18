package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.SymbolDao;
import com.seniorproject.snn2spade.seniorproject.fragment.StockInfoFragment;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by snn2spade on 3/18/2017 AD.
 */

public class StockInfoActivity extends AppCompatActivity {
    private SimpleCursorAdapter mSearchCursorAdapter;
    SearchView searchView;
    private String[] mSearchWordSet = {"No Suggestions"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setContentView(R.layout.activity_stock_info);
        setupSearchBar();
        initSearchBarListener();
        initHelpIconClickListener();
        initOnClickLogoListener();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.stockInfo_fragment_container, StockInfoFragment.newInstance())
                    .commit();
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

    private void initHelpIconClickListener() {
        ImageView helpView = (ImageView) findViewById(R.id.help_icon);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorialIntent = new Intent(getBaseContext(), TutorialActivity.class);
                tutorialIntent.putExtra(TutorialActivity.TUTORIAL_TYPE_MESSAGE, TutorialActivity.STOCKINFO_TUTORIAL);
                startActivity(tutorialIntent);
            }
        });
    }

    private void setupSearchBar() {
        final String[] from = new String[]{"name"};
        final int[] to = new int[]{android.R.id.text1};
        // setup SimpleCursorAdapter
        mSearchCursorAdapter = new SimpleCursorAdapter(StockInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // Fetch data from mysql table using AsyncTask
        fetchSymbolListData();
    }

    private void initSearchBarListener() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // <-- search suggestion -->
        searchView.setSuggestionsAdapter(mSearchCursorAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                // Add clicked text to search box
                CursorAdapter ca = searchView.getSuggestionsAdapter();
                Cursor cursor = ca.getCursor();
                cursor.moveToPosition(position);
                String query = cursor.getString(cursor.getColumnIndex("name"));
                searchView.setQuery(query.substring(0, query.indexOf(",")), true);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter data
                final MatrixCursor mc = new MatrixCursor(new String[]{BaseColumns._ID, "name"});
                for (int i = 0; i < mSearchWordSet.length; i++) {
                    if (mSearchWordSet[i].toLowerCase().startsWith(newText.toLowerCase()))
                        mc.addRow(new Object[]{i, mSearchWordSet[i]});
                }
                mSearchCursorAdapter.changeCursor(mc);
                return false;
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void fetchSymbolListData() {
        Call<List<SymbolDao>> call = HttpManager.getInstance().getService()
                .loadSymbolList();
        call.enqueue(new Callback<List<SymbolDao>>() {
            @Override
            public void onResponse(Call<List<SymbolDao>> call, Response<List<SymbolDao>> response) {
                if (response.isSuccessful()) {
                    List<SymbolDao> symbolListColllection = response.body();
                    List<String> result = new ArrayList<>();
                    for (SymbolDao doc : symbolListColllection) {
                        result.add(doc.getName() + ", " + doc.getMarket());
                    }
                    mSearchWordSet = new String[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        mSearchWordSet[i] = result.get(i);
                    }
                } else {
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<SymbolDao>> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

}
