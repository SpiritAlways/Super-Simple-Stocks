package com.global.beverage.stocks.service.test;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.global.beverage.stocks.common.StockSymbol;
import com.global.beverage.stocks.model.Stock;
import com.global.beverage.stocks.service.StockPriceService;
import com.global.beverage.stocks.service.SuperStockSerivce;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/simple-stocks-context-test.xml")
public class StockPriceSeriviceTest {

	private static final Logger LOGGER = Logger.getLogger(StockPriceSeriviceTest.class.getName());

	@Autowired
	private StockPriceService stockPriceService;
	
	@Autowired
	private SuperStockSerivce superStockService;
	
	@Test
	public void computeDividendYeildTest() {
		Stock stock = superStockService.findStock(StockSymbol.JOE);
		BigDecimal result =  stockPriceService.computeDividendYield(BigDecimal.ZERO, stock);
		
		Assert.assertTrue("Expected Dividend Yield is Zero", result.compareTo(BigDecimal.ZERO) == 0);
	}
	
	@Test
	public void computeCommonDividendYield() {
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);

		Stock stock = superStockService.findStock(StockSymbol.POP);
		BigDecimal result =  stockPriceService.computeDividendYield(tickerPrice, stock);
		LOGGER.info("Result Common Dividend..."+result);
		
		Assert.assertTrue("Common Dividend...Expected Dividend Yield is 0.04", result.compareTo(BigDecimal.valueOf(0.04)) == 0);
	}

	@Test
	public void computePreferredDividendYield() {
		BigDecimal tickerPrice = BigDecimal.valueOf(215.75);
		Stock stock = superStockService.findStock(StockSymbol.GIN);
		BigDecimal result =  stockPriceService.computeDividendYield(tickerPrice, stock);
		
		LOGGER.info("Result Preferred Dividend..."+result);
		Assert.assertTrue("Preferred Dividend...Expected Dividend Yield is 9.27", result.compareTo(BigDecimal.valueOf(9.27)) == 0);
	}
	
	@Test
	public void computePERatio() {
		BigDecimal tickerPrice = BigDecimal.valueOf(0.75);
		Stock stock = superStockService.findStock(StockSymbol.ALE);
		BigDecimal result = stockPriceService.computePERatio(tickerPrice, stock);
		
		LOGGER.info("Result PE Ratio..."+result);
		Assert.assertTrue("Expected PE Ratio is 0.03", result.compareTo(BigDecimal.valueOf(0.03)) == 0);

	}
}
