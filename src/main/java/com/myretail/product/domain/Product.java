package com.myretail.product.domain;

/**
 * Product entity with any domain logic
 * 
 * @author mrinmoy
 *
 */
public class Product {

	private int id;
	private String name;
	private Money price;
	
	public Product(int id, String name, Money price) {
		if(id <= 0) {
			throw new IllegalArgumentException("invalid id");
		}
		if(name == null || name.isBlank()) {
			throw new IllegalArgumentException("invalid name");
		}
		if(price == null) {
			throw new IllegalArgumentException("invalid price");
		}
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Money getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
}
