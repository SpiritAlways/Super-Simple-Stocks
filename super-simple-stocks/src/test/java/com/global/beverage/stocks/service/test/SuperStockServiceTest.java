package com.global.beverage.stocks.service.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.global.beverage.stocks.common.StockSymbol;
import com.global.beverage.stocks.common.TradeIndicator;
import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.service.SuperStockSerivce;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/simple-stocks-context-test.xml")
public class SuperStockServiceTest {

	private static final Logger LOGGER = Logger.getLogger(SuperStockServiceTest.class.getName());

	@Autowired
	private SuperStockSerivce superStockService;

	@Test
	public void getStocks() {
		Map<StockSymbol, Stock> stocks = superStockService.getStocks();
		LOGGER.info("All stocks ["+stocks+"]");
	}

	@Test
	public void findStockPrice() {
		Stock stock = new Stock(StockSymbol.ALE, BigDecimal.valueOf(23.15), BigDecimal.valueOf(123.15), BigDecimal.valueOf(3.15));
		superStockService.recordTrade(StockSymbol.ALE, BigDecimal.valueOf(27.15), TradeIndicator.BUY, 150);
		BigDecimal result = superStockService.computeStockPrice(stock);

		LOGGER.info("Stock ALE price..."+result);
		
		Assert.assertTrue("Stock ALE price is 27.15", result.compareTo(BigDecimal.valueOf(27.15)) == 0);

	}
	
	@Test
	public void findGeometricMean() {
		BigDecimal result = superStockService.computeGeometricMean();
		LOGGER.info("Geometric Mean..."+result);
	}
	
}
