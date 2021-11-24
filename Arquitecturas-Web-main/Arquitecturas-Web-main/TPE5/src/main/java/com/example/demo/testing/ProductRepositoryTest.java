package com.example.demo.testing;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.pojo.Client;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.pojo.DTODaylyProductReport;
import com.example.demo.pojo.Product;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Test: Capa de repositorios - Product Repository")
public class ProductRepositoryTest extends JUnitTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientProductRepository clientProductRepository;
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

	@Test
	@DisplayName("Test: test para acciones de alta de un elemento")
	public void testSave() {
		productRepository.save(product);
		Product fetchedProduct = productRepository.findById(product.getIdProduct()).get();
		assertEquals(product.getIdProduct(), fetchedProduct.getIdProduct());
	}

	@Test
	@DisplayName("Test: test para acciones de altas de varios elementos")
	public void testSaveAll() {
		System.out.println("testSaveAll");
		Product product1 = new Product("Nombre", "Descripción", 1, 1);
		Product product2 = new Product("Nombre", "Descripción", 1, 1);
		productRepository.save(product1);
		productRepository.save(product2);
		List<Product> productList = (List<Product>) productRepository.findAll();
		assertEquals(List.of(product1, product2).size(), productList.size());
	}

	@Test
	@DisplayName("Test: test para acciones de consulta por ID")
	public void testGetById() {
		Product product1 = new Product("Nombre", "Descripción", 1, 1);
		Product product2 = productRepository.save(product1);
		Optional<Product> optional = productRepository.findById(product2.getIdProduct());
		assertEquals(product2.getIdProduct(), optional.get().getIdProduct());
	}

	@Test
	@DisplayName("Test: test para acciones de bajas por ID")
	public void givenIdTODeleteThenShouldDeleteTheProduct() {
		Product product = new Product("Nombre", "Descripción", 1, 1);
		productRepository.save(product);
		productRepository.deleteById(product.getIdProduct());
		Optional<Product> optional = productRepository.findById(product.getIdProduct());
		assertEquals(Optional.empty(), optional);
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
		Page<DTODaylyProductReport> result = this.productRepository.getProductSelectByDate(PageRequest.of(1, 1000));
//		productsSelled.stream().collect(Collectors.groupingBy(ClientProduct::getDate)).entrySet().forEach((k) -> {
//			k.getValue().stream().reduce((p1, p2) -> p1.getCant() *  + value2);
//		});
		result.stream().forEach((p) ->
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
		assertEquals(((Product) ((Object[]) this.productRepository.getMostSelledProduct(PageRequest.of(0, 1))[0])[1]).getIdProduct(),products.stream().reduce((p1, p2) -> (p1.getStock() > p2.getStock()) ? p1:p2).get().getIdProduct());
	}
}
