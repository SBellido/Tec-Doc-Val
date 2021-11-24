package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.service.*;

@RestController
@RequestMapping(value = { "api/products/bought" })
public class ClientProductController {

	@Autowired
	private ProductService ps;

	@Autowired
	private ClientProductService cps;

	@GetMapping(value = { "/page", "/", "" })
	public ResponseEntity<Pagination<ClientProduct>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		return new ResponseEntity<Pagination<ClientProduct>>(this.cps.findAll(page, size), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<ClientProduct>> getById(@PathVariable("id") Long id) {
		List<ClientProduct> o = this.cps.findById(id);
		return new ResponseEntity<List<ClientProduct>>(o, HttpStatus.OK);

	}

	@PostMapping(value = { "/","" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientProduct> save(@RequestBody ClientProduct c) {
		if (cps.canBuy(c) && ps.buyProduct(c.getProduct().getIdProduct(), c.getCant())) {
			if (this.cps.save(c)) {
				return new ResponseEntity<ClientProduct>(c, HttpStatus.OK);
			}
			return new ResponseEntity<ClientProduct>(c, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ClientProduct>(c, HttpStatus.FORBIDDEN);
	}

}
