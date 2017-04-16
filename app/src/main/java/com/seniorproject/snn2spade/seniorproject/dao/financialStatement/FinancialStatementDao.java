package com.seniorproject.snn2spade.seniorproject.dao.financialStatement;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by snn2spade on 4/17/2017 AD.
 */

public class FinancialStatementDao {
    @SerializedName("symbol_name")
    private String symbol_name;
    @SerializedName("data")
    private List<YearDocumentDao> year_doc_list = new ArrayList<>();

    public String getSymbol_name() {
        return symbol_name;
    }

    public void setSymbol_name(String symbol_name) {
        this.symbol_name = symbol_name;
    }

    public List<YearDocumentDao> getYear_doc_list() {
        return year_doc_list;
    }

    public void setYear_doc_list(List<YearDocumentDao> year_doc_list) {
        this.year_doc_list = year_doc_list;
    }
}
