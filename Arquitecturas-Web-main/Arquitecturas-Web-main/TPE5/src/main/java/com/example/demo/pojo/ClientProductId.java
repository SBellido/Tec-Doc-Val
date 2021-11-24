package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Embeddable
public class ClientProductId implements Serializable{

	@Override
	public String toString() {
		return "ClientProductId [idProduct=" + idProduct + ", idClient=" + idClient + ", date=" + date + "]";
	}


	private static final long serialVersionUID = 8825738415373631317L;
	
	@Column(name = "id_product")
	private Long idProduct;
	@Column(name = "id_client")
	private Long idClient;
	@Column(name = "id_date")
	private Long date;


	public ClientProductId() {}

	public ClientProductId(Long idProduct, Long idClient, Long date) {
		super();
		this.idProduct = idProduct;
		this.idClient = idClient;
		this.date = date;
	}


	public Long getIdProduct() {
		return idProduct;
	}


	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}


	public Long getIdClient() {
		return idClient;
	}


	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}


	public Long getDate() {
		return date;
	}


	public void setDate(Long date) {
		this.date = date;
	}



	@Override
	public int hashCode() {
		return Objects.hash(date, idClient, idProduct);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientProductId other = (ClientProductId) obj;
		return Objects.equals(date, other.date) && Objects.equals(idClient, other.idClient)
				&& Objects.equals(idProduct, other.idProduct);
	}
}
