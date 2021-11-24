package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.repository.ClientProductRepository;

@Service
public class ClientProductService {

	@Autowired
	private ClientProductRepository cpr;
	
	public Pagination<ClientProduct> findAll(int page, int size) {
		Page<ClientProduct> p = this.cpr.findAll(PageRequest.of(page, size));
		if(p.getTotalPages() < page) {			
			p = this.cpr.findAll(PageRequest.of(p.getTotalPages(), size));
		}
		return new Pagination<ClientProduct>(p.getContent(), page, size, ((p.isFirst())?-1:page-1), ((p.isLast())?-1:page+1), p.getTotalPages());
	}
	
	public List<ClientProduct> findById(Long clientId) {
		return this.cpr.findByIdClient(clientId);
	}
	
	@Transactional
	public boolean save(ClientProduct c) {
		if(c != null) {
			this.cpr.save(c);
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean delete(ClientProduct p) {
		if(p != null) {			
		this.cpr.delete(p);
			return true;
		}
		return false;
	}
	
	public boolean canBuy(ClientProduct p) {
		p.setDate(new Date());
//		System.out.println(this.cpr.findAmountBoughtProductsByDate(p.getClient().getIdClient(), p.getDate()));
		Integer cant = this.cpr.findAmountBoughtProductsByDate(p.getClient().getIdClient(), p.getDate());
		return (cant != null) ? cant + p.getCant() <= 3 : p.getCant() <= 3;
	}
}