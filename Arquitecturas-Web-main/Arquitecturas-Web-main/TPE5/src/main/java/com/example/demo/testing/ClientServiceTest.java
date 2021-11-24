package com.example.demo.testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.pojo.Client;
import com.example.demo.pojo.ClientProduct;
import com.example.demo.pojo.DTOClientAmountSpend;
import com.example.demo.pojo.Product;
import com.example.demo.repository.ClientProductRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.ProductService;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test: Capa de Servicios - Client Service")
public class ClientServiceTest {

	@Mock
	private ClientRepository clientRepository;
	
	@Autowired
	@InjectMocks
	private ClientService clientService;

    
	@BeforeEach
	public void setUp() {
//		product = new Product("Nombre", "Descripción", 1, 1);
	}

	@AfterEach
	public void tearDown() {
//		product = null;
	}
	
	@Mock
	Page<DTOClientAmountSpend> p;
	@SuppressWarnings("deprecation")
	@DisplayName("Test: test para generar un reporte donde se indiquen los clientes y el monto total de sus compras")
	@Test
	public void testForGetAmountSpendByClientReport() {
		Client c = new Client(1L, "Juan");
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
		List<DTOClientAmountSpend> listTest = List.of(new DTOClientAmountSpend(c, 60));
//		PageImpl<Product> p = new PageImpl<Product>();
		when(p.getContent()).thenReturn(listTest);
		when(p.hasNext()).thenReturn(false);
		when(p.hasPrevious()).thenReturn(false);
		when(p.getTotalPages()).thenReturn(1);
		System.out.println(p.getContent());
		System.out.println(p.hasNext());
		System.out.println(p.hasPrevious());
		System.out.println(p.getTotalPages());
		when(clientRepository.getAmountSpendByClientReport(PageRequest.of(0, 1))).thenReturn(p);
		assertEquals((Integer)this.clientService.getAmountSpendByClientReport(0, 1).getElements().get(0).getAmountSpend(),productsSelled.stream().map( p -> p.getCant() * p.getProduct().getPrice()).reduce((p1, p2) -> p1 + p2).get());
	}
}
