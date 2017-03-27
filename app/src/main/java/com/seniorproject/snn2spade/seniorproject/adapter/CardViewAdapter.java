package com.seniorproject.snn2spade.seniorproject.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.fragment.StockInfoFragment;
import com.seniorproject.snn2spade.seniorproject.util.Utils;
import com.seniorproject.snn2spade.seniorproject.util.ViewModifier;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by snn2spade on 3/15/2017 AD.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private final static int VIEW_TYPE_INDEX_CARD = 0;
    private final static int VIEW_TYPE_STOCK_CARD = 1;
    private List<HistoricalTradingDao> mDataset;
    private List<String> mStockSymbolList;
    private boolean isLayoutInflated = false;

    // Provide a reference to the views for each data item
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardViewAdapter(List<HistoricalTradingDao> myDataset, List<String> mStockSymbolList) {
        this.mDataset = myDataset;
        this.mStockSymbolList = mStockSymbolList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        int card_layout = 0;
        if (viewType == CardViewAdapter.VIEW_TYPE_INDEX_CARD) {
            card_layout = R.layout.index_card_view;
        } else if (viewType == CardViewAdapter.VIEW_TYPE_STOCK_CARD) {
            card_layout = R.layout.stock_card_view;
        }
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(card_layout, parent, false);
        isLayoutInflated = true;
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0) {
            return CardViewAdapter.VIEW_TYPE_STOCK_CARD;
        }

        return CardViewAdapter.VIEW_TYPE_INDEX_CARD;
    }

    // Replace the contents of a view (invoked by the layout manager)
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
        if (position > 0) {
            updateStockCardContent(holder, position);
        }

        /* Add click listener to stock card */
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), StockInfoActivity.class);
//                v.getContext().startActivity(intent);
                ((Activity) v.getContext()).getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, StockInfoFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void updateStockCardContent(ViewHolder holder, int position) {
        /* set text */
        /* --name */
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.stock_name,
                mStockSymbolList.get(position));
        /* --price */
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.stock_price,
                Utils.getInstance().convertDoubleToString(mDataset.get(position).getClose()));
        /* --volume */
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.stock_volume,
                Utils.getInstance().convertMillionUnit(mDataset.get(position).getTotalValueBaht()) + " M");
        /* --change */
        String change = "";
        change = change + Utils.getInstance().convertDoubleToString(mDataset.get(position).getChange()).replace("-", "");
        change = change + " (" + Utils.getInstance().convertDoubleToString(
                mDataset.get(position).getPercentChange()) + "%)";
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.stock_change, change);
        /* --date */
        DateFormat df = new SimpleDateFormat("dd MMM yy");
        String date = "(" + df.format(mDataset.get(position).getDate()) + ")";
        ViewModifier.getInstance().setTextView(holder.mCardView, R.id.stock_date, date);
        /* change color */
        if(mDataset.get(position).getChange() != null) {
            if (mDataset.get(position).getChange() >= 0) {
                ViewModifier.getInstance().setColorTextView(holder.mCardView, R.id.stock_change, R.color.colorTrendUp);
                ViewModifier.getInstance().setImageView(holder.mCardView
                        , R.id.stock_change_icon
                        , R.drawable.ic_arrow_up_white_24dp
                        , R.color.colorTrendUp);
            } else {
                ViewModifier.getInstance().setColorTextView(holder.mCardView, R.id.stock_change, R.color.colorTrendDown);
                ViewModifier.getInstance().setImageView(holder.mCardView
                        , R.id.stock_change_icon
                        , R.drawable.ic_arrow_down_white_24dp
                        , R.color.colorTrendDown);
            }
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateList(List<HistoricalTradingDao> mDataset, List<String> mStockSymbolList) {
        this.mDataset = mDataset;
        this.mStockSymbolList = mStockSymbolList;
        notifyDataSetChanged();
    }
}




