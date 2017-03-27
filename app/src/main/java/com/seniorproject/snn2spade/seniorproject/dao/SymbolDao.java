package com.seniorproject.snn2spade.seniorproject.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by snn2spade on 3/27/2017 AD.
 */

public class SymbolDao {
    @SerializedName("name")
    private String name;
    @SerializedName("market")
    private String market;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
