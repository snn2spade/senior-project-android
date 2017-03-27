package com.seniorproject.snn2spade.seniorproject.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by snn2spade on 3/27/2017 AD.
 */

public class HistoricalTradingDao {
    @SerializedName("date")
    private Date date;
    @SerializedName("prior")
    private Double prior;
    @SerializedName("open")
    private Double open;
    @SerializedName("high")
    private Double high;
    @SerializedName("low")
    private Double low;
    @SerializedName("close")
    private Double close;
    @SerializedName("change")
    private Double change;
    @SerializedName("percent_change")
    private Double percentChange;
    @SerializedName("average")
    private Double average;
    @SerializedName("aom_value(shares)")
    private Double aomValueShares;
    @SerializedName("aom_value(baht)")
    private Double aomValueBaht;
    @SerializedName("total_value(shares")
    private Double totalValueShares;
    @SerializedName("total_value(baht)")
    private Double totalValueBaht;
    @SerializedName("market_cap")
    private Double marketCap;
    @SerializedName("p/e")
    private Double pE;
    @SerializedName("p/bv")
    private Double pBv;
    @SerializedName("divided_yield(%)")
    private Double dividedYield;
    @SerializedName("turnover_ratio(%)")
    private Double turnoverRatio;
    @SerializedName("par")
    private Double par;
    @SerializedName("listed_shares(shares)")
    private Double listedSharesShares;
    @SerializedName("trading_sign")
    private String tradingSign;
    @SerializedName("benefit_sign")
    private String benefitSign;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrior() {
        return prior;
    }

    public void setPrior(Double prior) {
        this.prior = prior;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getAomValueShares() {
        return aomValueShares;
    }

    public void setAomValueShares(Double aomValueShares) {
        this.aomValueShares = aomValueShares;
    }

    public Double getAomValueBaht() {
        return aomValueBaht;
    }

    public void setAomValueBaht(Double aomValueBaht) {
        this.aomValueBaht = aomValueBaht;
    }

    public Double getTotalValueShares() {
        return totalValueShares;
    }

    public void setTotalValueShares(Double totalValueShares) {
        this.totalValueShares = totalValueShares;
    }

    public Double getTotalValueBaht() {
        return totalValueBaht;
    }

    public void setTotalValueBaht(Double totalValueBaht) {
        this.totalValueBaht = totalValueBaht;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getpE() {
        return pE;
    }

    public void setpE(Double pE) {
        this.pE = pE;
    }

    public Double getpBv() {
        return pBv;
    }

    public void setpBv(Double pBv) {
        this.pBv = pBv;
    }

    public Double getDividedYield() {
        return dividedYield;
    }

    public void setDividedYield(Double dividedYield) {
        this.dividedYield = dividedYield;
    }

    public Double getTurnoverRatio() {
        return turnoverRatio;
    }

    public void setTurnoverRatio(Double turnoverRatio) {
        this.turnoverRatio = turnoverRatio;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }

    public Double getListedSharesShares() {
        return listedSharesShares;
    }

    public void setListedSharesShares(Double listedSharesShares) {
        this.listedSharesShares = listedSharesShares;
    }

    public String getTradingSign() {
        return tradingSign;
    }

    public void setTradingSign(String tradingSign) {
        this.tradingSign = tradingSign;
    }

    public String getBenefitSign() {
        return benefitSign;
    }

    public void setBenefitSign(String benefitSign) {
        this.benefitSign = benefitSign;
    }
}
