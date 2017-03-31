package com.seniorproject.snn2spade.seniorproject.adapter;

import android.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.util.Utils;
import com.seniorproject.snn2spade.seniorproject.util.ViewModifier;
import com.seniorproject.snn2spade.seniorproject.view.FourColumnRowViewGroup;
import com.seniorproject.snn2spade.seniorproject.view.HisTradingRowViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by snn2spade on 3/30/2017 AD.
 */

public class StockInfoAdapter {
    private String mSymbol;
    private List<HistoricalTradingDao> mDataSet;
    private View mRootView;

    public StockInfoAdapter(View mRootView) {
        this.mRootView = mRootView;
    }

    public void updateDataSet(String mSymbol, List<HistoricalTradingDao> mDataSet) {
        this.mSymbol = mSymbol;
        this.mDataSet = mDataSet;
        updateContent();
    }

    private void updateContent() {
        /* top section */
        /* -- symbol */
        ViewModifier.getInstance().setTextView(mRootView, R.id.info_symbol, mSymbol);
        /* -- close */
        ViewModifier.getInstance().setTextView(mRootView, R.id.info_close,
                Utils.getInstance().convertDoubleToString(mDataSet.get(0).getClose(), 2));
        /* -- change */
        String change = "";
        change = change + Utils.getInstance().convertDoubleToString(mDataSet.get(0).getChange(), 2).replace("-", "");
        change = change + " (" + Utils.getInstance().convertDoubleToString(
                mDataSet.get(0).getPercentChange(), 1) + "%)";
        ViewModifier.getInstance().setTextView(mRootView, R.id.info_chg, change);
        /* -- volumne */
        ViewModifier.getInstance().setTextView(mRootView, R.id.info_vol,
                Utils.getInstance().convertMillionUnit(mDataSet.get(0).getTotalValueBaht()) + " MB");
        /* -- date */
        DateFormat df = new SimpleDateFormat("dd MMM yy");
        String date = "(" + df.format(mDataSet.get(0).getDate()) + ")";
        ViewModifier.getInstance().setTextView(mRootView, R.id.info_date, date);
        /* trading info card */
        LinearLayout tradingInfoCard = (LinearLayout) mRootView.findViewById(R.id.info_tradingInfoCard);
        tradingInfoCard.removeViewAt(1);
        FourColumnRowViewGroup row1 = new FourColumnRowViewGroup(mRootView.getContext());
        row1.setTextPair1("", "");
        row1.setTextPair2("", "");
        row1.setTextPair1("Low", Utils.getInstance().convertDoubleToString(mDataSet.get(0).getLow(), 2));
        row1.setTextPair2("High", Utils.getInstance().convertDoubleToString(mDataSet.get(0).getHigh(), 2));
        tradingInfoCard.addView(row1);
        FourColumnRowViewGroup row2 = new FourColumnRowViewGroup(mRootView.getContext());
        row2.setTextPair1("Avg", Utils.getInstance().convertDoubleToString(mDataSet.get(0).getAverage(), 2));
        row2.setTextPair2("Turn over", Utils.getInstance().convertDoubleToString(mDataSet.get(0).getTurnoverRatio(), 2) + " %");
        tradingInfoCard.addView(row2);
        if (mDataSet.get(0).getTradingSign() != null) {
            FourColumnRowViewGroup row3 = new FourColumnRowViewGroup(mRootView.getContext());
            row2.setTextPair1("Benefit sign", mDataSet.get(0).getTradingSign());
            tradingInfoCard.addView(row3);
        }
        /* historical trading card */
        LinearLayout hisTradingCard = (LinearLayout) mRootView.findViewById(R.id.info_hisTradingCard);
        hisTradingCard.removeViewAt(1);
        for (int i = 0; i < 5; i++) {
            HisTradingRowViewGroup his_row = new HisTradingRowViewGroup(mRootView.getContext());
            his_row.setDate(mDataSet.get(i).getDate());
            his_row.setClose(mDataSet.get(i).getClose());
            his_row.setVolume(mDataSet.get(i).getTotalValueBaht());
            his_row.setChg(mDataSet.get(i).getPercentChange());
            hisTradingCard.addView(his_row);
        }
        /* financial statement card */
        LinearLayout financialStCard = (LinearLayout) mRootView.findViewById(R.id.info_financialStatementCard);
        financialStCard.removeViewAt(1);
        FourColumnRowViewGroup fin_row1 = new FourColumnRowViewGroup(mRootView.getContext());
        fin_row1.setTextPair1("P/E",Utils.getInstance().convertDoubleToString(mDataSet.get(0).getpE(),2));
        fin_row1.setTextPair2("P/BV",Utils.getInstance().convertDoubleToString(mDataSet.get(0).getpBv(),2));
        FourColumnRowViewGroup fin_row2 = new FourColumnRowViewGroup(mRootView.getContext());
        fin_row2.setTextPair1("Yield",Utils.getInstance().convertDoubleToString(mDataSet.get(0).getDividedYield(),2)+" %");
        financialStCard.addView(fin_row1);
        financialStCard.addView(fin_row2);
    }
}
