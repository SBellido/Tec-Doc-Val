package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.*;
import com.example.demo.repository.*;

@Service
public class ProductService implements BasicService<Product>{

	@Autowired
	private ProductRepository pr;
	@Autowired
	private ClientProductRepository cpr;
	
	@Override
	public Pagination<Product> findAll(int page, int size) {
		Page<Product> p = this.pr.findAll(PageRequest.of(page, size));
		if(p.getTotalPages() < page) {			
			p = this.pr.findAll(PageRequest.of(p.getTotalPages(), size));
		}
		return new Pagination<Product>(p.getContent(), page, size, ((p.isFirst())?-1:page-1), ((p.isLast())?-1:page+1), p.getTotalPages());
	}
	
	@Override
	public Optional<Product> findById(Long id) {
		return this.pr.findById(id);
	}
	
	@Override
	@Transactional
	public boolean save(Product c) {
		if(c != null) {			
			this.pr.save(c);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean delete(Product p) {
		System.out.println(p);
		if(p != null) {
			this.cpr.deleteAll(cpr.findByIdProduct(p.getIdProduct()));
			this.pr.delete(p);
			return true;
		}
		return false;
	}
	

	
	public Pagination<DTODaylyProductReport> getProductSelectByDate(int page, int size) {
		Page<DTODaylyProductReport> p =  this.pr.getProductSelectByDate(PageRequest.of(page, size));
		return new Pagination<DTODaylyProductReport>(p.getContent(), page, size, ((p.isFirst())?-1:page-1), ((p.isLast())?-1:page+1), p.getTotalPages());
	}
	
	public Object[] getMostSelledProduct() {
		return this.pr.getMostSelledProduct(PageRequest.of(0, 1));
	}
	
	public boolean buyProduct(Long idProduct, int cant) {
		Product p = this.pr.getById(idProduct);
		if(p.getStock() - cant  >= 0) {
			p.setStock(p.getStock() - cant);
			this.pr.save(p);
			return true;
		}
		return false;
	}
}
