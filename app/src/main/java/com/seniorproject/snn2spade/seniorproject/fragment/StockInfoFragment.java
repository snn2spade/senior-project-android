package com.seniorproject.snn2spade.seniorproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.adapter.StockInfoAdapter;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by snn2spade on 3/18/2017 AD.
 */

public class StockInfoFragment extends Fragment {
    private StockInfoAdapter mAdapter;
    private String mSymbol;

    public static StockInfoFragment newInstance() {
        Bundle args = new Bundle();
        StockInfoFragment fragment = new StockInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Intent intent = getActivity().getIntent();
            mSymbol = intent.getStringExtra(DashboardFragment.SYMBOL_MESSAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock_info, container, false);
        mAdapter = new StockInfoAdapter(rootView);
        if (mSymbol == null) {
            Toast.makeText(getActivity(), "mSymbol is null", Toast.LENGTH_LONG).show();
        } else {
            connectApiToRetrieveDataSet();
        }
        return rootView;
    }

    public void connectApiToRetrieveDataSet() {
        Call<List<HistoricalTradingDao>> call = HttpManager.getInstance().getService().loadHistoricalTrading(mSymbol, 5);
        call.enqueue(new Callback<List<HistoricalTradingDao>>() {
            @Override
            public void onResponse(Call<List<HistoricalTradingDao>> call, Response<List<HistoricalTradingDao>> response) {
                if (response.isSuccessful()) {
                    List<HistoricalTradingDao> hisTradingCollection = response.body();
                    mAdapter.updateDataSet(mSymbol, hisTradingCollection);
                } else {
                    Log.e("StockInfoFragment", response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<HistoricalTradingDao>> call, Throwable t) {
                Log.e("StockInfoFragment", t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


}
