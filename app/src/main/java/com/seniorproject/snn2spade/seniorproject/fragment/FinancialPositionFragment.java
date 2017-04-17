package com.seniorproject.snn2spade.seniorproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.jjoe64.graphview.series.DataPoint;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.activity.FinancialStatementActivity;

import com.seniorproject.snn2spade.seniorproject.dao.financialStatement.FinancialStatementDao;
import com.seniorproject.snn2spade.seniorproject.dao.financialStatement.YearDocumentDao;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;
import com.seniorproject.snn2spade.seniorproject.util.DynamicScrollbar;
import com.seniorproject.snn2spade.seniorproject.util.Utils;
import com.seniorproject.snn2spade.seniorproject.view.FinancialStatementCardViewGroup;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinancialPositionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinancialPositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinancialPositionFragment extends Fragment {
    public static final int FIN_POS_TYPE = 1;
    public static final int COMP_INCOME_TYPE = 2;
    public static final int CASH_FLOW_TYPE = 3;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String mSymbol;
    private FinancialStatementDao mFinancialStatementDao;
    private int mSheetType;

    public FinancialPositionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinancialPositionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinancialPositionFragment newInstance(int mSheetType, String param2) {
        FinancialPositionFragment fragment = new FinancialPositionFragment();
        fragment.mSheetType = mSheetType;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Intent intent = getActivity().getIntent();
        mSymbol = intent.getStringExtra(FinancialStatementActivity.SYMBOL_MESSAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_financial_position, container, false);
        retrieveFinancialStatementData(rootView, mSymbol,mSheetType);
        initScrollListener(rootView);
        return rootView;
    }



    private void initScrollListener(final View rootView ) {
        final ScrollView sv = (ScrollView) rootView.findViewById(R.id.fin_scroll_view);
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = sv.getScrollY();
                int scrollX = sv.getScrollX();
//                Log.d("scrollTesting",""+scrollY);
                DynamicScrollbar.getInstance().recalculateActionBar(getActivity(), scrollY);
            }
        });
    }


    private void retrieveFinancialStatementData(final View rootView, String mSymbol,int sheet_type) {
        Call<FinancialStatementDao> call;
        switch (sheet_type){
            case FIN_POS_TYPE:
                call = HttpManager.getInstance().getService().loadFinancialPosition(mSymbol);
                break;
            case COMP_INCOME_TYPE:
                call = HttpManager.getInstance().getService().loadComprehensiveIncome(mSymbol);
                break;
            case CASH_FLOW_TYPE:
                call = HttpManager.getInstance().getService().loadCashFlow(mSymbol);
                break;
            default:
                call = HttpManager.getInstance().getService().loadFinancialPosition(mSymbol);
                break;

        }
        call.enqueue(new Callback<FinancialStatementDao>() {
            @Override
            public void onResponse(Call<FinancialStatementDao> call, Response<FinancialStatementDao> response) {
                if (response.isSuccessful()) {
                    AVLoadingIndicatorView loading_icon = (AVLoadingIndicatorView) getActivity().findViewById(R.id.loadingIcon);
                    loading_icon.smoothToHide();
                    FinancialStatementDao financialStatement = response.body();
                    mFinancialStatementDao = financialStatement;
                    Collections.reverse(mFinancialStatementDao.getYear_doc_list());
                    drawFinancialStatementCard(rootView);
                } else {
                    Log.e("FinancialPositionSt", response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<FinancialStatementDao> call, Throwable t) {
                Log.e("FinancialPositionSt", t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void drawFinancialStatementCard(View rootView) {
        int numberOfYear = mFinancialStatementDao.getYear_doc_list().size();
        if (numberOfYear == 0) {
            return;
        }
        int numberOfAttribute = mFinancialStatementDao.getYear_doc_list().get(0).getAttr_list().size();
        LinearLayout cardContainer = (LinearLayout) rootView.findViewById(R.id.fin_card_container);
        for (int attr_idx = 0; attr_idx < numberOfAttribute; attr_idx++) {
            FinancialStatementCardViewGroup finStatementViewGroup = new FinancialStatementCardViewGroup(getContext());
            /* add bottom space for last item */
            if (attr_idx == (numberOfAttribute - 1)) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0, 0, Utils.getInstance().dpToPx(10));
                finStatementViewGroup.setLayoutParams(lp);
            }
            /* set attr name */
            String attr_name = mFinancialStatementDao.getYear_doc_list().get(0).getAttr_list().get(attr_idx).getAttr_name();
            finStatementViewGroup.setAttributeName(attr_name);
            cardContainer.addView(finStatementViewGroup);
            /* set graph */
            List<DataPoint> dataPointList = new ArrayList<>();
            for (int year_idx = 0; year_idx < numberOfYear; year_idx++) {
                YearDocumentDao yearDoc = mFinancialStatementDao.getYear_doc_list().get(year_idx);
                if (yearDoc.getAttr_list().get(attr_idx).getValue() == null) {
                    dataPointList.add(new DataPoint(Integer.parseInt(yearDoc.getYear()), 0));
                } else {
                    Double val = yearDoc.getAttr_list().get(attr_idx).getValue() / 1000.0;
                    dataPointList.add(new DataPoint(Integer.parseInt(yearDoc.getYear()), val));
                }
            }
            finStatementViewGroup.setGraphDataSet(dataPointList);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
