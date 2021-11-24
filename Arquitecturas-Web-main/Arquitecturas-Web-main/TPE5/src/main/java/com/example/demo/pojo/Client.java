package com.example.demo.pojo;

import javax.persistence.*;

@Entity
public class Client {

	@Id
	@Column(name = "id_client")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idClient;

	@Column(nullable = false)
	private String name;

	public Client() {
	}

	public Client(Long idClient, String name) {
		super();
		this.idClient = idClient;
		this.name = name;
	}

	public Long getIdClient() {
		return idClient;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", name=" + name + "]";
	}

}
