package com.example.demo.pojo;

public class DTOClientAmountSpend {

	private Client client;
	private int amountSpend;

	public DTOClientAmountSpend() {
	}

	public DTOClientAmountSpend(Client client, int amountSpend) {
		super();
		this.client = client;
		this.amountSpend = amountSpend;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getAmountSpend() {
		return amountSpend;
	}

	public void setAmountSpend(int amountSpend) {
		this.amountSpend = amountSpend;
	}

}
