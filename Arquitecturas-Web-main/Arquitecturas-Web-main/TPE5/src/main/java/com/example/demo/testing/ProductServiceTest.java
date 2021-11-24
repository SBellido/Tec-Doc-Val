package com.example.demo.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.pagination.Pagination;
import com.example.demo.pojo.Client;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.pojo.DTODaylyProductReport;
import com.example.demo.pojo.Product;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Test: Capa de Servicios - Product Service")
public class ProductServiceTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientProductRepository clientProductRepository;
	@Autowired
	private ProductService productService;
	private Product product;

	@BeforeEach
	public void setUp() {
		product = new Product("Nombre", "Descripción", 1, 1);
	}

	@AfterEach
	public void tearDown() {
		productRepository.deleteAll();
		clientProductRepository.deleteAll();
		clientRepository.deleteAll();
		product = null;
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test: test para acciones de bajas por ID")
	public void testForGetProductSelectByDate() {
		Product p1 = productRepository.save(new Product("Nombre", "Descripción", 10, 1));
		Product p2 = productRepository.save(new Product("Nombre", "Descripción", 10, 1));
		Product p3 = productRepository.save(new Product("Nombre", "Descripción", 20, 1));
		Product p4 = productRepository.save(new Product("Nombre", "Descripción", 20, 1));
		Client c = clientRepository.save(new Client(1L, "Juan"));
		List<Product> products = productRepository.saveAll(List.of(p1, p2, p3, p4));
		List<ClientProduct> productsSelled = List.of(
		new ClientProduct(p1, c, new Date(2021, 1, 1), p1.getStock()), 
		new ClientProduct(p2, c, new Date(2021, 1, 1), p2.getStock()), 
		new ClientProduct(p3, c, new Date(2021, 1, 1), p3.getStock()), 
		new ClientProduct(p4, c, new Date(2021, 1, 1), p4.getStock()));
		Pagination<DTODaylyProductReport> result = this.productService.getProductSelectByDate(1, 1000);
//		productsSelled.stream().collect(Collectors.groupingBy(ClientProduct::getDate)).entrySet().forEach((k) -> {
//			k.getValue().stream().reduce((p1, p2) -> p1.getCant() *  + value2);
//		});
		result.getElements().stream().forEach((p) ->
			assertEquals(p.getAmountCollected(), p1.getStock() * p1.getPrice() + p2.getStock() * p2.getPrice())
		);
		
//		assertEquals(, optional);
	}
	
	@SuppressWarnings("deprecation")
	@DisplayName("Test: test para saber cuál fue el producto más vendido")
	@Test
	public void testForGetAmountSpendByClientReport() {
		Client c = clientRepository.save(new Client(1L, "Juan"));
		List<Product> products = List.of(
				new Product("Nombre", "Descripción", 10, 1),
				new Product("Nombre", "Descripción", 10, 1),
				new Product("Nombre", "Descripción", 20, 1),
				new Product("Nombre", "Descripción", 20, 10));
		List<ClientProduct> productsSelled = List.of(
				new ClientProduct(products.get(0), c, new Date(2021, 1, 1), products.get(0).getStock()),
				new ClientProduct(products.get(1), c, new Date(2021, 1, 1), products.get(1).getStock()),
				new ClientProduct(products.get(2), c, new Date(2021, 1, 1), products.get(2).getStock()),
				new ClientProduct(products.get(3), c, new Date(2021, 1, 1), products.get(3).getStock()));
		this.productRepository.saveAll(products);
		this.clientProductRepository.saveAll(productsSelled);
		System.out.println(this.productRepository.getMostSelledProduct(PageRequest.of(0, 1))[0]);
		
		assertEquals(((Product) ((Object[]) this.productService.getMostSelledProduct()[0])[1]).getIdProduct(),products.stream().reduce((p1, p2) -> (p1.getStock() > p2.getStock()) ? p1:p2).get().getIdProduct());
	}
}
