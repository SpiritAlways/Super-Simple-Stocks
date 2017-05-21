package com.global.beverage.stocks.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.global.beverage.stocks.common.StockSymbol;
import com.global.beverage.stocks.common.TradeIndicator;
import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.model.Trade;

@Service
public class SuperStockServiceImpl implements SuperStockSerivce {
 
	private static final Logger LOGGER = Logger.getLogger(SuperStockServiceImpl.class.getName());

	private Map<StockSymbol, Stock> stocks;
	private Map<StockSymbol, Deque<Trade>> trades;

	@Autowired
	private StockPriceService stockPriceService;
	
	public SuperStockServiceImpl() {
		stocks = initStocks();
		trades = new HashMap<StockSymbol, Deque<Trade>>();
	}
	
	public Map<StockSymbol, Stock> getStocks() {
		return stocks;
	}

	public Stock findStock(StockSymbol symbol) {
		return stocks.get(symbol);
	}

	public void recordTrade(StockSymbol symbol, BigDecimal price, TradeIndicator indicator, int quantity) {
		Deque<Trade> symbolTrades = findTrades(symbol);
		if(symbolTrades == null){
			symbolTrades = new LinkedList<Trade>();
			trades.put(symbol, symbolTrades);
		}
		Trade trade = new Trade(symbol, new Date(), price, quantity, indicator);
		
		symbolTrades.addLast(trade);	
	}

	public Deque<Trade> findTrades(StockSymbol symbol) {
		Deque<Trade> result = null;
		if(stocks.get(symbol) == null){
			result = new LinkedList<Trade>();
		}
		else{
			result = trades.get(symbol);
		}
		
		return result;	
	}

	public BigDecimal computeDividendYield(BigDecimal tickerPrice, Stock stock){
		return stockPriceService.computeDividendYield(tickerPrice, stock);
	}

	public BigDecimal computePERatio(BigDecimal tickerPrice, Stock stock){
		return stockPriceService.computePERatio(tickerPrice, stock);
	}

	public BigDecimal computeStockPrice(Stock stock) {
		Deque<Trade> trades = findTrades(stock.getStockSymbol());
		if(CollectionUtils.isEmpty(trades)){
			return BigDecimal.ZERO;
		}

		List<Trade> last15minTrades = retrieveLast15MinAgedTrade(trades);

		return stockPriceService.computeStockPrice(last15minTrades);
	}

	public BigDecimal computeGeometricMean() {
		List<Stock> stocksList = new ArrayList<Stock>();
		stocksList.addAll(stocks.values());

		return stockPriceService.computeGeometricMean(stocksList);
	}
	
	private Map<StockSymbol, Stock> initStocks() {
		Map<StockSymbol, Stock> result = new HashMap<StockSymbol, Stock>();

		Stock stockTEA = new Stock(StockSymbol.TEA, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.valueOf(100));
		result.put(stockTEA.getStockSymbol(), stockTEA);

		Stock stockPOP = new Stock(StockSymbol.POP, BigDecimal.valueOf(8), BigDecimal.ONE, BigDecimal.valueOf(100));
		result.put(stockPOP.getStockSymbol(), stockPOP);

		Stock stockALE = new Stock(StockSymbol.ALE, BigDecimal.valueOf(23), BigDecimal.ONE, BigDecimal.valueOf(60));
		result.put(stockALE.getStockSymbol(), stockALE);

		Stock stockGIN = new Stock(StockSymbol.GIN, BigDecimal.valueOf(80), BigDecimal.valueOf(20), BigDecimal.valueOf(100));
		result.put(stockGIN.getStockSymbol(), stockGIN);

		Stock stockJOE = new Stock(StockSymbol.JOE, BigDecimal.valueOf(13), BigDecimal.ONE, BigDecimal.valueOf(250));
		result.put(stockJOE.getStockSymbol(), stockJOE);
		
		return result;
	}

	private List<Trade> retrieveLast15MinAgedTrade(Deque<Trade> trades) {
		List<Trade> result = new ArrayList<Trade>();
		Date now = new Date();
		boolean is15MinAge = true;
		Iterator<Trade> tradeIterator = trades.descendingIterator();
		while(tradeIterator.hasNext() && is15MinAge){
			Trade trade = tradeIterator.next();
			is15MinAge = isLast15MinAgedTrade(trade, now);
			if(is15MinAge){
				result.add(trade);
			}
		}
		return result;
	}
	
	private boolean isLast15MinAgedTrade(Trade trade, Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -15);
		Date deadLine = cal.getTime();
		
		boolean isAfter = trade.getTimeStamp().after(deadLine);
		boolean isEqual = trade.getTimeStamp().equals(deadLine);
		return isAfter || isEqual;
	}
}
