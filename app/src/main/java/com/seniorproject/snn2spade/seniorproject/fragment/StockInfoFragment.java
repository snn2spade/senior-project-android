package com.seniorproject.snn2spade.seniorproject.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.util.DynamicScrollbar;

/**
 * Created by snn2spade on 3/18/2017 AD.
 */

public class StockInfoFragment extends Fragment {

    public static StockInfoFragment newInstance() {
        Bundle args = new Bundle();
        StockInfoFragment fragment = new StockInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock_info,container,false);
        return rootView;
    }

}
