package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.DTODaylyProductReport;
import com.example.demo.pojo.Product;
import com.example.demo.service.ClientProductService;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController implements BasicController<Product> {

	@Autowired
	private ProductService ps;

	@GetMapping(value = { "/report/page", "/report/", "/report" })
	public ResponseEntity<Pagination<DTODaylyProductReport>> findById(
			@RequestParam(value = "page", defaultValue="1") int page, 
			@RequestParam(value = "size", defaultValue="5") int size) {
			return new ResponseEntity<Pagination<DTODaylyProductReport>>(this.ps.getProductSelectByDate(page, size), HttpStatus.OK);
	}
	
	@GetMapping("/most-selled")
	public ResponseEntity<Object[]> getMostSelledProduct() {
			return new ResponseEntity<Object[]>(this.ps.getMostSelledProduct(), HttpStatus.OK);
	}
	
	@Override
	@GetMapping(value={"/page", "/", ""})
	public ResponseEntity<Pagination<Product>> getAll(
			@RequestParam(value = "page", defaultValue="1") int page, 
			@RequestParam(value = "size", defaultValue="5") int size) {
		return new ResponseEntity<Pagination<Product>>(ps.findAll(page, size), HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
		Optional<Product> o = this.ps.findById(id);
		if(o.isPresent()) {
			return new ResponseEntity<Product>(o.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}
		
	@Override
	@PostMapping(value={"/", ""},
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> save(@RequestBody Product c) {
		if(this.ps.save(c)) {
			return new  ResponseEntity<Product>(c, HttpStatus.OK);
		}
		return new  ResponseEntity<Product>(c, HttpStatus.NOT_FOUND);
	}
	
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> delete(@PathVariable("id") Long id) {
		Optional<Product> o = this.ps.findById(id);
		if(o.isPresent()&&this.ps.delete(o.get())) {		
			return new  ResponseEntity<Product>(o.get(), HttpStatus.OK);
		}
		return new  ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	}
	
	@Override
	@PutMapping(value={"/", ""},
		    consumes = MediaType.APPLICATION_JSON_VALUE, 
		    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> update(@RequestBody Product c) {
		return this.save(c);
	}

}
