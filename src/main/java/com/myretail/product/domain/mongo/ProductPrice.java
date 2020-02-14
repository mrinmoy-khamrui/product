package com.myretail.product.domain.mongo;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.myretail.product.domain.Money;

@Document
public class ProductPrice {

	@Id
	private int productId;
	private BigDecimal value;
	private Currency currency;
	
	public ProductPrice() {
		
	}
	
	public ProductPrice(int productId, String value, String currencyCode) {
		this.productId = productId;
		this.value = new BigDecimal(value);
		this.currency = Currency.getInstance(currencyCode);
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Transient
	public Money getPriceAsMoney() {
		return new Money(currency, value);
	}
}
