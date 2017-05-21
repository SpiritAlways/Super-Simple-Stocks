package com.global.beverage.stocks.service;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.Map;

import com.global.beverage.stocks.common.StockSymbol;
import com.global.beverage.stocks.common.TradeIndicator;
import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.model.Trade;

public interface SuperStockSerivce {
	
	public Map<StockSymbol, Stock> getStocks();

	public Stock findStock(StockSymbol symbol);
	
	public void recordTrade(StockSymbol symbol, BigDecimal price, TradeIndicator indicator, int quantity);
	
	public Deque<Trade> findTrades(StockSymbol symbol);
	
	BigDecimal computeDividendYield(BigDecimal tickerPrice, Stock stock);
	
	BigDecimal computePERatio(BigDecimal tickerPrice, Stock stock);
    
    BigDecimal computeStockPrice(Stock stock);
    
    BigDecimal computeGeometricMean();	
	
}
