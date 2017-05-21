package com.global.beverage.stocks.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.model.Trade;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    private static final Logger LOGGER = Logger.getLogger(StockPriceServiceImpl.class.getName());
	
    public static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	public static int DECIMALS = 2;
	
	public BigDecimal computeDividendYield(BigDecimal tickerPrice, Stock stock) {
		BigDecimal dividendYield = BigDecimal.ZERO;
		
		if(!validateStock(tickerPrice, stock)) {
			return dividendYield;
		}
		
		
        // find dividend yield by stock
        switch (stock.getStockSymbol().getType()) {
            case COMMON:
                dividendYield = stock.getLastDividend()
    					.divide(tickerPrice, DECIMALS, ROUNDING_MODE)
    					.stripTrailingZeros();
                break;
            case PREFERRED:
                dividendYield = stock.getFixedDividend().multiply(stock.getParValue());
                if(! BigDecimal.ZERO.equals(dividendYield)){
        			dividendYield = dividendYield
        					.divide(tickerPrice, DECIMALS, ROUNDING_MODE)
        					.stripTrailingZeros();
        		}                
                break;
            default:
                break;
        }
        
		return dividendYield;
	}

	public BigDecimal computePERatio(BigDecimal tickerPrice, Stock stock) {
		
		boolean isValidStock = validateStock(tickerPrice, stock);
		
		boolean validStockSymbol = BigDecimal.ZERO.compareTo(stock.getLastDividend()) != 0;
		if(!isValidStock || !validStockSymbol){
			return BigDecimal.ZERO;
		}
		
		return tickerPrice
				.divide(stock.getLastDividend(), DECIMALS, ROUNDING_MODE)
				.stripTrailingZeros();
	}

	public BigDecimal computeStockPrice(List<Trade> trades) {

		if(null == trades || trades.size() == 0) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal stockPriceQuantitySum = BigDecimal.ZERO;
		double sumOfQuantities = 0;

		for(Trade trade: trades){
			BigDecimal quantity = BigDecimal.valueOf(trade.getQuantity());
			stockPriceQuantitySum = stockPriceQuantitySum.add(trade.getPrice().multiply(quantity));

			sumOfQuantities += trade.getQuantity();
		}

		BigDecimal result = BigDecimal.ZERO;
		if(sumOfQuantities != 0){
			BigDecimal bgQty = BigDecimal.valueOf(sumOfQuantities);
			result = stockPriceQuantitySum
					.divide(bgQty, DECIMALS, ROUNDING_MODE)
					.stripTrailingZeros();
		}	
		
		return result;
	}

	public BigDecimal computeGeometricMean(List<Stock> stocks) {
		if(CollectionUtils.isEmpty(stocks)){
			return BigDecimal.ZERO;
		}

		BigDecimal price = BigDecimal.ONE;
		for(Stock t: stocks){
			price = price.multiply(t.getParValue());
		}
		
		double pow = 1.0 / stocks.size();
		double result = Math.pow(price.doubleValue(), pow);
		
		return BigDecimal.valueOf(result)
				.setScale(DECIMALS, ROUNDING_MODE)
				.stripTrailingZeros();
	}

    private boolean validateStock(BigDecimal tickerPrice, Stock stock) {

        if (stock == null) {
            LOGGER.info("Stock not found");
            return false;
        }

        // check ticker price
        if (tickerPrice == null || BigDecimal.ZERO.compareTo(tickerPrice) == 0) {
            LOGGER.info("Ticker price of stock = "+tickerPrice);
            return false;
        }
        
        return true;
    }	
}
