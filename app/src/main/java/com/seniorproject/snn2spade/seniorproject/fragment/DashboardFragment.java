package com.seniorproject.snn2spade.seniorproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.adapter.CardViewAdapter;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.manager.http.HttpManager;
import com.seniorproject.snn2spade.seniorproject.util.DynamicScrollbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String SYMBOL_MESSAGE = "symbol";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardViewAdapter mAdapter;
    private List<String> symbolList;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initRecyclerView(rootView);
        symbolList = new ArrayList<>();
        symbolList.add("ADVANC");
        symbolList.add("AOT");
        symbolList.add("CPALL");
        symbolList.add("KBANK");
        symbolList.add("PTT");
        symbolList.add("SCB");
        symbolList.add("TRUE");
        initHistoricalTradingListDataSet();
        return rootView;
    }

    private void initHistoricalTradingListDataSet(){
        Call<List<HistoricalTradingDao>> call = HttpManager.getInstance().getService()
                .loadHistoricalTradingBySymbolList(symbolList);
        call.enqueue(new Callback<List<HistoricalTradingDao>>() {
            @Override
            public void onResponse(Call<List<HistoricalTradingDao>> call, Response<List<HistoricalTradingDao>> response) {
                if(response.isSuccessful()){
                    List<HistoricalTradingDao> historicalTradingCollection = response.body();
                    mAdapter.updateList(historicalTradingCollection,symbolList);
                }
                else{
                    Log.e("DashboardFragment",response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(),
                            "Server problem - 404 not found",Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<HistoricalTradingDao>> call, Throwable t) {
                Log.e("DashboardFragment",t.toString().toString());
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Require internet for retrieve data",Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void initRecyclerView(View rootView) {
        // 1. get a reference to recyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // 2. set layoutManger
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // this is data fro recycler view
        List<HistoricalTradingDao> dataSet = new ArrayList<>();
        List<String> symbolList = new ArrayList<>();
        // 3. create an adapter
        mAdapter = new CardViewAdapter(dataSet,symbolList,this);
        // 4. set adapter
        mRecyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 6. set scroll listener
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int vertical_offset = mRecyclerView.computeVerticalScrollOffset();
                DynamicScrollbar.getInstance().recalculateActionBar(getActivity(), vertical_offset);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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
