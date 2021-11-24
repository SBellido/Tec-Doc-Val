package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Para resolver el trabajo se implemento la siguiente arquitectura:
// Capa de Controller:
// # En esta capa se intercambian peticiones HTTP de los cliente
// # paquete de com.example.demo.controller
// Capa de servicios:
// # En esta capa tiene la lógica del negocio
// # paquete de com.example.demo.services
// Capa de persistencia:
// # En esta capa se guardan los datos en la DB mediante los 
//   repositorios
// # paquete de com.example.demo.repository
@SpringBootApplication
public class AwebTp4EntregableApplication {
	public static void main(String[] args) {
		SpringApplication.run(AwebTp4EntregableApplication.class, args);
	}

}
