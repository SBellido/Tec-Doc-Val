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
import com.example.demo.pojo.Client;
import com.example.demo.pojo.DTOClientAmountSpend;
import com.example.demo.service.ClientProductService;
import com.example.demo.service.ClientService;

@RestController
@RequestMapping("api/clients")
public class ClientController implements BasicController<Client> {

	@Autowired
	private ClientService cs;

	@GetMapping(value = { "/report/page", "/report/", "/report" })
	public ResponseEntity<Pagination<DTOClientAmountSpend>> getReportAmountSpend(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		return new ResponseEntity<Pagination<DTOClientAmountSpend>>(this.cs.getAmountSpendByClientReport(page, size),
				HttpStatus.OK);
	}

	@Override
	@GetMapping(value = { "/page", "/", "" })
	public ResponseEntity<Pagination<Client>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		return new ResponseEntity<Pagination<Client>>(this.cs.findAll(page, size), HttpStatus.OK);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Client> getById(@PathVariable("id") Long id) {
		Optional<Client> o = this.cs.findById(id);
		if (o.isPresent()&&this.cs.delete(o.get())) {
			return new ResponseEntity<Client>(o.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
	}

	@Override
	@PostMapping(value = { "/",
			"" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> save(@RequestBody Client c) {
		if (this.cs.save(c)) {
			return new ResponseEntity<Client>(c, HttpStatus.OK);
		}
		return new ResponseEntity<Client>(c, HttpStatus.NOT_FOUND);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> delete(@PathVariable("id") Long id) {
		Optional<Client> o = this.cs.findById(id);
		if (o.isPresent()) {
			return new ResponseEntity<Client>(o.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
	}

	@Override
	@PutMapping(value = { "/",
			"" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Client> update(@RequestBody Client o) {
		return this.save(o);
	}
}
