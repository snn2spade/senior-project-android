package com.seniorproject.snn2spade.seniorproject.dao.financialStatement;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class YearDocumentDao {
    @SerializedName("year")
    private String year;
    @SerializedName("attributes")
    private List<AtrributeDao>  attr_list  = new ArrayList<>();;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<AtrributeDao> getAttr_list() {
        return attr_list;
    }

    public void setAttr_list(List<AtrributeDao> attr_list) {
        this.attr_list = attr_list;
    }
}
