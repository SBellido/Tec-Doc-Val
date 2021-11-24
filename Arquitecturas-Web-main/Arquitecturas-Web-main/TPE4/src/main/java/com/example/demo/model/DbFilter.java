package com.example.demo.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.demo.pojo.*;

// En esta clase se consumen archivos JSON de la  carpeta resource
// para cargar la base de datos
@Configuration
public class DbFilter {
	
	@Bean
	public CommandLineRunner initDb(ClientRepository cr, ProductRepository pr, ClientProductRepository cpr) {
		return arg -> {
			List<Client> users = new ObjectMapper().readValue(new File("resources/aweb_customer.json"), new TypeReference<List<Client>>() {});
			cr.saveAll(users);
			List<Product> products = new ObjectMapper().readValue(new File("resources/aweb_product.json"), new TypeReference<List<Product>>() {});
			pr.saveAll(products);
			List<ClientProduct> clientProducts = new ArrayList<ClientProduct>();
			IntStream.range(1, 1000).forEach((i) -> {
				@SuppressWarnings("deprecation")
				Date randomDate = new Date(ThreadLocalRandom.current().nextLong(new Date(101, 1, 1).getTime(), new Date().getTime()));
				int cant =  (int) (Math.random() * 10);
				Client client   =  users.get((int) (Math.random() * users.size()));
				Product product =  products.get((int) (Math.random() * products.size()));
				clientProducts.add(new ClientProduct(product, client, randomDate,cant));
			});
			cpr.saveAll(clientProducts);	
		};
	}
}
