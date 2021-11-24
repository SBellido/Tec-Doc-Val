package com.example.demo.testing;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

//import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.pojo.Client;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.pojo.Product;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Test: Capa de repositorios - Client Repository")
public class ClientRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientProductRepository clientProductRepository;

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
		productRepository.deleteAll();
	}

	@SuppressWarnings("deprecation")
	@DisplayName("Test: test para generar un reporte donde se indiquen los clientes y el monto total de sus compras")
	@Test
	public void testForGetAmountSpendByClientReport() {
		Client c = clientRepository.save(new Client(1L, "Juan"));
		List<Product> products = List.of(
				new Product("Nombre", "Descripción", 10, 1),
				new Product("Nombre", "Descripción", 10, 1),
				new Product("Nombre", "Descripción", 20, 1),
				new Product("Nombre", "Descripción", 20, 1));
		List<ClientProduct> productsSelled = List.of(
				new ClientProduct(products.get(0), c, new Date(2021, 1, 1), products.get(0).getStock()),
				new ClientProduct(products.get(1), c, new Date(2021, 1, 1), products.get(1).getStock()),
				new ClientProduct(products.get(2), c, new Date(2021, 1, 1), products.get(2).getStock()),
				new ClientProduct(products.get(3), c, new Date(2021, 1, 1), products.get(3).getStock()));
		this.productRepository.saveAll(products);
		this.clientProductRepository.saveAll(productsSelled);
		System.out.println();
		System.out.println();
		assertEquals((Integer )this.clientRepository.getAmountSpendByClientReport(PageRequest.of(0, 1)).getContent().get(0).getAmountSpend(),productsSelled.stream().map( p -> p.getCant() * p.getProduct().getPrice()).reduce((p1, p2) -> p1 + p2).get());
	}
}
