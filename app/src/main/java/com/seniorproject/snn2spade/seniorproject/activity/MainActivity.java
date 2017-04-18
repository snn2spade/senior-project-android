package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.dao.PredictStockDao;
import com.seniorproject.snn2spade.seniorproject.dao.SymbolDao;
import com.seniorproject.snn2spade.seniorproject.fragment.DashboardFragment;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener {

    private DashboardFragment mDashboardFragment;
    private List<String> mSymbolList;
    private Map<String, Boolean> mPredictStock;

    private SimpleCursorAdapter mSearchCursorAdapter;
    SearchView searchView;
    private String[] mSearchWordSet = {"No Suggestions"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSearchBar();
        initSearchBarListener();
        initHelpIconClickListener();
        initOnClickLogoListener();
        mDashboardFragment = DashboardFragment.newInstance("");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_fragment_container, mDashboardFragment)
                    .commit();
        }
        mPredictStock = new HashMap<>();
        fetchPredictStockResultList();
        handleIntent(getIntent());
    }

    private void setupSearchBar() {
        final String[] from = new String[]{"name"};
        final int[] to = new int[]{android.R.id.text1};
        // setup SimpleCursorAdapter
        mSearchCursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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

    private void initHelpIconClickListener() {
        ImageView helpView = (ImageView) findViewById(R.id.help_icon);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDashboardFragment.scrollToTop();
                Intent tutorialIntent = new Intent(getBaseContext(), TutorialActivity.class);
                tutorialIntent.putExtra(TutorialActivity.TUTORIAL_TYPE_MESSAGE, TutorialActivity.HOME_TUTORIAL);
                startActivity(tutorialIntent);
            }
        });
    }


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("<<<MainActivity>>>", "---------------- GOT SEARCH INTENT : " + query);
            List<String> symbol_list = new ArrayList<>();
            symbol_list.add(query);
            fetchHistoricalTradingListDataSet(symbol_list);
        } else {
            List<String> symbol_list = new ArrayList<>();
            symbol_list.add("GFPT");
            symbol_list.add("CPF");
            symbol_list.add("SUC");
            symbol_list.add("KYE");
            symbol_list.add("SCB");
            symbol_list.add("BLA");
            symbol_list.add("SCB");
            symbol_list.add("AEC");
            symbol_list.add("STANLY");
            symbol_list.add("CTW");
            symbol_list.add("UTP");
            symbol_list.add("PTL");
            symbol_list.add("PTTGC");
            symbol_list.add("INOX");
            symbol_list.add("SCC");
            symbol_list.add("CPN");
            symbol_list.add("PTT");
            symbol_list.add("PDI");
            symbol_list.add("CPALL");
            symbol_list.add("BDMS");
            symbol_list.add("BEC");
            symbol_list.add("CENTEL");
            symbol_list.add("AOT");
            symbol_list.add("DELTA");
            symbol_list.add("ADVANC");
            fetchHistoricalTradingListDataSet(symbol_list);
        }
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
                    Log.e("MainActivity", response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<SymbolDao>> call, Throwable t) {
                Log.e("MainActivity", t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void fetchHistoricalTradingListDataSet(final List<String> symbol_list) {
        Call<List<HistoricalTradingDao>> call = HttpManager.getInstance().getService()
                .loadHistoricalTradingBySymbolList(symbol_list);
        call.enqueue(new Callback<List<HistoricalTradingDao>>() {
            @Override
            public void onResponse(Call<List<HistoricalTradingDao>> call, Response<List<HistoricalTradingDao>> response) {
                if (response.isSuccessful()) {
                    List<HistoricalTradingDao> historicalTradingCollection = response.body();
                    if (historicalTradingCollection.size() != 0) {
                        AVLoadingIndicatorView loading_icon = (AVLoadingIndicatorView) findViewById(R.id.loadingIcon);
                        loading_icon.smoothToHide();
                        mSymbolList = symbol_list;
                        mDashboardFragment.updateDataSet(historicalTradingCollection, mSymbolList);
                    } else {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                "Symbol search not found", Toast.LENGTH_LONG)
                                .show();
                        if(!isTaskRoot()){
                            finish();
                        }
                    }
                } else {
                    Log.e("MainActivity", response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<HistoricalTradingDao>> call, Throwable t) {
                Log.e("MainActivity", t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void fetchPredictStockResultList() {
        Call<List<PredictStockDao>> call = HttpManager.getInstance().getService()
                .loadPredictStockList();
        call.enqueue(new Callback<List<PredictStockDao>>() {
            @Override
            public void onResponse(Call<List<PredictStockDao>> call, Response<List<PredictStockDao>> response) {
                if (response.isSuccessful()) {
                    mPredictStock = new HashMap<>();
                    List<PredictStockDao> predictionStockCollection = response.body();
                    for (PredictStockDao doc : predictionStockCollection) {
                        mPredictStock.put(doc.getName(), doc.getPredict());
                        mDashboardFragment.updatePredictResult(mPredictStock);
                    }
                } else {
                    Log.e("MainActivity", response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<PredictStockDao>> call, Throwable t) {
                Log.e("MainActivity", t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
