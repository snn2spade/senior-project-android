package com.seniorproject.snn2spade.seniorproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.activity.FinancialStatementActivity;
import com.seniorproject.snn2spade.seniorproject.adapter.StockInfoAdapter;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;
import com.seniorproject.snn2spade.seniorproject.util.DynamicScrollbar;
import com.wang.avi.AVLoadingIndicatorView;

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
    private Boolean mPredict;

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
        mPredict = intent.getBooleanExtra(DashboardFragment.PREDICT_MESSAGE, true);
        if (mSymbol == null) {
            // Toast.makeText(getActivity(), "mSymbol is null", Toast.LENGTH_LONG).show();
        } else {
            connectApiToRetrieveDataSet();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock_info, container, false);
        mAdapter = new StockInfoAdapter(rootView);
        initFinancialStatementListener(rootView);
        initScrollListener(rootView);
        return rootView;
    }

    private void initScrollListener(final View rootView) {
        final ScrollView sv = (ScrollView) rootView.findViewById(R.id.fin_scroll_view);
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = sv.getScrollY();
                int scrollX = sv.getScrollX();
                DynamicScrollbar.getInstance().recalculateActionBar(getActivity(), scrollY);
            }
        });
    }

    private void initFinancialStatementListener(View rootView) {
        LinearLayout finStatementCard = (LinearLayout) rootView.findViewById(R.id.info_financialStatementCard);
        finStatementCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), FinancialStatementActivity.class);
                intent.putExtra(FinancialStatementActivity.SYMBOL_MESSAGE, mSymbol);
                getActivity().startActivity(intent);
            }
        });
    }

    public void connectApiToRetrieveDataSet() {
        Call<List<HistoricalTradingDao>> call = HttpManager.getInstance().getService().loadHistoricalTrading(mSymbol, 5);
        call.enqueue(new Callback<List<HistoricalTradingDao>>() {
            @Override
            public void onResponse(Call<List<HistoricalTradingDao>> call, Response<List<HistoricalTradingDao>> response) {
                if (response.isSuccessful()) {
                    List<HistoricalTradingDao> hisTradingCollection = response.body();
                    mAdapter.updateDataSet(mSymbol, hisTradingCollection, mPredict);
                    AVLoadingIndicatorView loading_icon = (AVLoadingIndicatorView) getActivity().findViewById(R.id.loadingIcon);
                    loading_icon.smoothToHide();
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
