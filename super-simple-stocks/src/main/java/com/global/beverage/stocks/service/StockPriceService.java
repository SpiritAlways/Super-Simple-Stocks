package com.global.beverage.stocks.service;

import java.math.BigDecimal;
import java.util.List;

import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.model.Trade;

public interface StockPriceService {

	BigDecimal computeDividendYield(BigDecimal tickerPrice, Stock stock);
	
	BigDecimal computePERatio(BigDecimal tickerPrice, Stock stock);
    
    BigDecimal computeStockPrice(List<Trade> trades);
    
    BigDecimal computeGeometricMean(List<Stock> stocks);
}
