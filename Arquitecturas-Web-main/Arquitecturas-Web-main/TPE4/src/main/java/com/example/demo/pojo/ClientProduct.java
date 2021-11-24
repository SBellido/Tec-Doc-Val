
package com.example.demo.pojo;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "client_product")
public class ClientProduct {
    @EmbeddedId
    private ClientProductId id;
    
    @ManyToOne()
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
	private Product product;
    @ManyToOne()
    @MapsId("idClient")
    @JoinColumn(name = "id_client")
	private Client client;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = true)
	private int cant;

	public ClientProduct() {this.id =  new ClientProductId();}

	public ClientProduct(Product product, Client client, Date date, int cant) {
		super();
		this.product = product;
		this.client = client;
		this.date = Date.from(date.toInstant().truncatedTo( ChronoUnit.DAYS ));
		this.cant = cant;
		this.id =  new ClientProductId(product.getIdProduct(), client.getIdClient(), date.getTime());
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.id.setIdProduct(product.getIdProduct());
		this.product = product;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.id.setIdClient(client.getIdClient());
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		this.date = Date.from(date.toInstant().truncatedTo( ChronoUnit.DAYS ));
		this.id.setDate(date.getTime());
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}

	@Override
	public String toString() {
		return "ClientProduct [id=" + id + ", product=" + product + ", client=" + client + ", date=" + date + ", cant="
				+ cant + "]";
	}
}
