package com.seniorproject.snn2spade.seniorproject.dao.financialStatement;

import com.google.gson.annotations.SerializedName;

/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class AtrributeDao {
    @SerializedName("name")
    private String attr_name;
    @SerializedName("value")
    private Double value;
    @SerializedName("percent_chg")
    private Double percent_chg;

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPercent_chg() {
        return percent_chg;
    }

    public void setPercent_chg(Double percent_chg) {
        this.percent_chg = percent_chg;
    }
}
