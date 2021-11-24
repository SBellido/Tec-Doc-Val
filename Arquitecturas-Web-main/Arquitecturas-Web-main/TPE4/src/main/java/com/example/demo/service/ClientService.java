package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.*;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;

@Service
public class ClientService implements BasicService<Client>{

	@Autowired
	private ClientRepository cr;
	@Autowired
	private ClientProductRepository cpr;
	
	public Pagination<DTOClientAmountSpend> getAmountSpendByClientReport(int page, int size) {
		Page<DTOClientAmountSpend> p = this.cr.getAmountSpendByClientReport(PageRequest.of(page, size));
		return new Pagination<DTOClientAmountSpend>(p.getContent(), page, size, ((p.isFirst())?-1:page-1), ((p.isLast())?-1:page+1), p.getTotalPages());
	}
		
	@Override
	public Pagination<Client> findAll(int page, int size) {
		Page<Client> p = this.cr.findAll(PageRequest.of(page, size));
		if(p.getTotalPages() < page) {			
			p = this.cr.findAll(PageRequest.of(p.getTotalPages(), size));
		}
		return new Pagination<Client>(p.getContent(), page, size, ((p.isFirst())?-1:page-1), ((p.isLast())?-1:page+1), p.getTotalPages());
	}
	
	@Override
	public Optional<Client> findById(Long id) {
		return this.cr.findById(id);
	}
	
	@Override
	@Transactional
	public boolean save(Client c) {
		if(c != null) {			
			this.cr.save(c);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean delete(Client c) {
		if(c != null) {
			this.cpr.deleteAll(cpr.findByIdClient(c.getIdClient()));
			this.cr.delete(c);
			return true;
		}
		return false;
	}
}
