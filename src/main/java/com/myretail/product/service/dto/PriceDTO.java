package com.myretail.product.service.dto;

import java.math.BigDecimal;
import java.util.Currency;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.myretail.product.domain.Money;

public class PriceDTO {

	@NotNull
	@DecimalMin("0")
	private BigDecimal value;
	
//	@NotEmpty
//	@Size(min = 3, max = 3)
	@Pattern(regexp = "[a-zA-Z]{3}")
	private String currencyCode;
	
	public PriceDTO() {
		
	}

	public PriceDTO(Money price) {
		this.value = price.getAmount();
		this.currencyCode = price.getCurrency().getCurrencyCode();
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public Money toMoney() {
		if(value == null || currencyCode == null) {
			throw new IllegalStateException("missing mandatory details");
		}
		Currency currency = Currency.getInstance(currencyCode.toUpperCase());
		return new Money(currency, value);
	}
}
