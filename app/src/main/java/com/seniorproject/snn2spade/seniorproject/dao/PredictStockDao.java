package com.seniorproject.snn2spade.seniorproject.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by snn2spade on 4/16/2017 AD.
 */

public class PredictStockDao {
    @SerializedName("symbol_name")
    private String name;
    @SerializedName("predict")
    private Boolean predict;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPredict() {
        return predict;
    }

    public void setPredict(Boolean predict) {
        this.predict = predict;
    }
}
