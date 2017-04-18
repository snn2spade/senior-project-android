package com.seniorproject.snn2spade.seniorproject.view;


import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.util.ViewModifier;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class FinancialStatementCardViewGroup extends FrameLayout {


    public FinancialStatementCardViewGroup(@NonNull Context context) {
        super(context);
        initInflate();
    }

    public FinancialStatementCardViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
    }

    public FinancialStatementCardViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
    }

    public FinancialStatementCardViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
    }

    public void initInflate() {
        inflate(getContext(), R.layout.cardview_financial_statement, this);
    }

    public void setAttributeName(String attr_name) {
        ViewModifier.getInstance().setTextView(this, R.id.fin_attribute_name, attr_name);
    }

    public void setGraphDataSet(List<DataPoint> dataPointList) {
        /* prepare data point set */
        int numberOfYear = dataPointList.size();
        DataPoint[] dataPoints = new DataPoint[numberOfYear];
        for (int i = 0; i < numberOfYear; i++) {
            dataPoints[i] = dataPointList.get(i);
        }
        /* prepare graph */
        GraphView graph = (GraphView) this.findViewById(R.id.fin_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                DecimalFormat df_year = new DecimalFormat("###");
                DecimalFormat df_val = new DecimalFormat("###,###.#");
                String year = df_year.format(dataPoint.getX());
                String val = df_val.format(dataPoint.getY());
                Toast.makeText(getContext(), "Year: " + year + " Value: " + val + " MB", Toast.LENGTH_SHORT).show();
            }
        });

        series.setDrawBackground(true);
        series.setAnimated(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(12);
        series.setThickness(8);

//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(2012);
//        graph.getViewport().setMaxX(2015);

        graph.getGridLabelRenderer().setNumVerticalLabels(5);
        graph.getGridLabelRenderer().setNumHorizontalLabels(numberOfYear);

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
}
