package com.example.demo.pojo;

import javax.persistence.*;

@Entity
public class Product {
	@Override
	public String toString() {
		return "Product [idProduct=" + idProduct + ", name=" + name + ", description=" + description + ", price="
				+ price + ", stock=" + stock + "]";
	}

	@Id
	@Column(name = "id_product")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduct;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private int stock;

	public Product() {
	}

	public Product(Long idProduct, String name, String description, int price, int stock) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
