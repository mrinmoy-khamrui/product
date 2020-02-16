package com.myretail.product.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Basic value object for representing money
 * 
 * @author mrinmoy
 *
 */
public final class Money {

	public static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");
	public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;
	
	private final BigDecimal amount;
	private final Currency currency;
	
	public Money(BigDecimal amount) {
		this(DEFAULT_CURRENCY, amount);
	}
	
	public Money(Currency currency, BigDecimal amount) {
		if(currency == null) {
			throw new IllegalArgumentException("currency is required");
		}
		if(amount == null) {
			throw new IllegalArgumentException("amount is required");
		}
		this.currency = currency;
		this.amount = amount.setScale(currency.getDefaultFractionDigits());
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "[" + amount + " " + currency + "]";
	}
	
	
}
