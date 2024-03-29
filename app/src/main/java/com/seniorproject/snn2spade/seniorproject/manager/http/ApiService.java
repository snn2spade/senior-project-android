package com.seniorproject.snn2spade.seniorproject.manager.http;

import com.seniorproject.snn2spade.seniorproject.dao.PredictStockDao;
import com.seniorproject.snn2spade.seniorproject.dao.SymbolDao;
import com.seniorproject.snn2spade.seniorproject.dao.HistoricalTradingDao;
import com.seniorproject.snn2spade.seniorproject.dao.financialStatement.FinancialStatementDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by snn2spade on 3/27/2017 AD.
 */

public interface ApiService {
    @GET("symbol/getlist")
    Call<List<SymbolDao>> loadSymbolList();
    @GET("historicaltrading/getlimit/{symbol}/{limit}")
    Call<List<HistoricalTradingDao>> loadHistoricalTrading(@Path("symbol") String symbol,@Path("limit") int limit);
    @FormUrlEncoded
    @POST("historicaltrading/getbysymbollist")
    Call<List<HistoricalTradingDao>> loadHistoricalTradingBySymbolList(@Field("list[]") List<String> symbolList);
    @GET("predictstock/getpredictlist")
    Call<List<PredictStockDao>> loadPredictStockList();
    @GET("financialstatement/getfinancialpos/yearly/{symbol}")
    Call<FinancialStatementDao> loadFinancialPosition(@Path(("symbol")) String symbol);
    @GET("financialstatement/getcomincome/yearly/{symbol}")
    Call<FinancialStatementDao> loadComprehensiveIncome(@Path(("symbol")) String symbol);
    @GET("financialstatement/getcashflow/yearly/{symbol}")
    Call<FinancialStatementDao> loadCashFlow(@Path(("symbol")) String symbol);
}
