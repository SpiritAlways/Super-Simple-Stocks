package com.global.beverage.stocks.model;

import java.math.BigDecimal;
import java.util.Date;

import com.global.beverage.stocks.common.StockSymbol;
import com.global.beverage.stocks.common.TradeIndicator;

public class Trade {

	private StockSymbol symbol;
	private Date timeStamp;
	private int quantity;
	private TradeIndicator indicator;
	private BigDecimal price;
	
	public Trade(StockSymbol symbol, Date timeStamp, BigDecimal price, int quantity, TradeIndicator indicator) {
		super();
		this.symbol = symbol;
		this.timeStamp = timeStamp;
		this.quantity = quantity;
		this.indicator = indicator;
		this.price = price;
	}

	/**
	 * @return the symbol
	 */
	public StockSymbol getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(StockSymbol symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the indicator
	 */
	public TradeIndicator getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(TradeIndicator indicator) {
		this.indicator = indicator;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
