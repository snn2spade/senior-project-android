package com.seniorproject.snn2spade.seniorproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.dao.PredictStockDao;
import com.seniorproject.snn2spade.seniorproject.fragment.DashboardFragment;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;

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
    private Map<String,Boolean> mPredictStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSearchBarListener();
        mDashboardFragment = DashboardFragment.newInstance("");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_fragment_container, mDashboardFragment)
                    .commit();
        }
        mPredictStock = new HashMap<>();
        initPredictStockResultList();
        handleIntent(getIntent());
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

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra    (SearchManager.QUERY);
            Log.d("<<<MainActivity>>>","---------------- GOT SEARCH INTENT : "+query);
            List<String> symbol_list = new ArrayList<>();
            symbol_list.add(query);
            initHistoricalTradingListDataSet(symbol_list);
        }
        else{
            List<String> symbol_list = new ArrayList<>();
            symbol_list.add("ADVANC");
            symbol_list.add("AOT");
            symbol_list.add("CPALL");
            symbol_list.add("KBANK");
            symbol_list.add("PTT");
            symbol_list.add("SCB");
            symbol_list.add("TRUE");
            initHistoricalTradingListDataSet(symbol_list);
        }
    }

    private void initHistoricalTradingListDataSet(final List<String> symbol_list) {
        Call<List<HistoricalTradingDao>> call = HttpManager.getInstance().getService()
                .loadHistoricalTradingBySymbolList(symbol_list);
        call.enqueue(new Callback<List<HistoricalTradingDao>>() {
            @Override
            public void onResponse(Call<List<HistoricalTradingDao>> call, Response<List<HistoricalTradingDao>> response) {
                if (response.isSuccessful()) {
                    List<HistoricalTradingDao> historicalTradingCollection = response.body();
                    if(historicalTradingCollection.size() !=0){
                        mSymbolList = symbol_list;
                        mDashboardFragment.updateDataSet(historicalTradingCollection,mSymbolList);
                    }
                    else{
                        Toast.makeText(Contextor.getInstance().getContext(),
                                "Symbol search not found", Toast.LENGTH_LONG)
                                .show();
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

    private void initPredictStockResultList(){
        Call<List<PredictStockDao>> call = HttpManager.getInstance().getService()
                .loadPredictStockList();
        call.enqueue(new Callback<List<PredictStockDao>>() {
            @Override
            public void onResponse(Call<List<PredictStockDao>> call, Response<List<PredictStockDao>> response) {
                if (response.isSuccessful()) {
                    mPredictStock = new HashMap<>();
                    List<PredictStockDao> predictionStockCollection = response.body();
                    for (PredictStockDao doc : predictionStockCollection) {
                        Log.d("MainActivity","put "+doc.getName()+ ":"+ doc.getPredict());
                        mPredictStock.put(doc.getName(), doc.getPredict());
                        mDashboardFragment.updatePredictResult(mPredictStock);
                    }
                }
                else{
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
