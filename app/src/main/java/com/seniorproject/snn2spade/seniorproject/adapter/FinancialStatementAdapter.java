package com.seniorproject.snn2spade.seniorproject.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.financialStatement.FinancialStatementDao;
import com.seniorproject.snn2spade.seniorproject.dao.financialStatement.YearDocumentDao;
import com.seniorproject.snn2spade.seniorproject.manager.Contextor;
import com.seniorproject.snn2spade.seniorproject.util.Utils;
import com.seniorproject.snn2spade.seniorproject.util.ViewModifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class FinancialStatementAdapter extends RecyclerView.Adapter<FinancialStatementAdapter.ViewHolder> {
    private FinancialStatementDao mFinancialStatementDao;

    // / Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    public FinancialStatementAdapter(FinancialStatementDao mFinancialStatementDao) {
        this.mFinancialStatementDao = mFinancialStatementDao;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_financial_statement, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FinancialStatementAdapter.ViewHolder vh = new FinancialStatementAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        /* Add bottom space for last item */
        if (position + 1 == getItemCount()) {
            CardView cv = holder.mCardView;
            RecyclerView.MarginLayoutParams margin = (RecyclerView.MarginLayoutParams) cv.getLayoutParams();
            margin.setMargins(margin.leftMargin, margin.topMargin, margin.rightMargin, Utils.getInstance().dpToPx(10));
            cv.setLayoutParams(margin);
        }

        /* change card content */
        updateAttributeName(holder, position);
        updateGraph(holder, position);
    }


    private void updateGraph(ViewHolder holder, int position) {
        GraphView graph = (GraphView) holder.mCardView.findViewById(R.id.fin_graph);

        DataPoint[] dataPoints = new DataPoint[mFinancialStatementDao.getYear_doc_list().size()];


        for (int i = 0; i < mFinancialStatementDao.getYear_doc_list().size(); i++) {

            YearDocumentDao year_doc = mFinancialStatementDao.getYear_doc_list().get(i);
            if (year_doc.getAttr_list().get(position).getValue() == null) {
                dataPoints[i] = new DataPoint(i, 0);
            } else {
                Double val = year_doc.getAttr_list().get(position).getValue();
                val = val / 1000.0;
                dataPoints[i] = new DataPoint(i, val);
                Log.d("xxxxxx11111", year_doc.getYear() + ":" + val);
            }
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);



//        series.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(Contextor.getInstance().getContext(), "" + dataPoint, Toast.LENGTH_SHORT).show();
//            }
//        });

//        series.setDrawBackground(true);
//        series.setAnimated(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(12);
        series.setThickness(8);

//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(2012);
//        graph.getViewport().setMaxX(2016);

        graph.getGridLabelRenderer().setNumVerticalLabels(5);
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return super.formatLabel(value, isValueX).replace(",", "");
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " MB";
                }
            }
        });
        graph.addSeries(series);
    }

    private void updateAttributeName(ViewHolder holder, int position) {
        String attr_name = mFinancialStatementDao.getYear_doc_list().get(0).getAttr_list().get(position).getAttr_name();
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.fin_attribute_name, attr_name);
        Log.d("xxxxxx11111", attr_name);
    }

    @Override
    public int getItemCount() {
        if (mFinancialStatementDao.getYear_doc_list().size() > 0) {
            return mFinancialStatementDao.getYear_doc_list().get(0).getAttr_list().size();
        }
        return 0;
    }

    public void updateDataSet(FinancialStatementDao mFinancialStatementDao) {
        this.mFinancialStatementDao = mFinancialStatementDao;
        Collections.reverse(this.mFinancialStatementDao.getYear_doc_list());
        notifyDataSetChanged();
    }

}
