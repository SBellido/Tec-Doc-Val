package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.ClientProduct;

@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {

	// Dado que la tabla Client_Product tiene una PK compuesta, se realizao
	// una consulta que busca solo por la PK del cliente
	@Query(value="SELECT cp FROM ClientProduct cp WHERE cp.id.idClient = :idClient ")
	List<ClientProduct> findByIdClient(@Param("idClient") Long idClient);
	
	@Query(value="SELECT cp FROM ClientProduct cp WHERE cp.id.idProduct = :idProduct")
	List<ClientProduct> findByIdProduct(@Param("idProduct") Long idProduct);
	
	@Query(value=""
			+ "SELECT SUM(cp.cant) "
			+ "FROM ClientProduct cp "
			+ "WHERE cp.id.idClient = :idClient "
			+ "AND cp.date = :mydate "
			+ "GROUP BY (cp.id.idClient)")
	Integer findAmountBoughtProductsByDate(@Param("idClient") Long idClient, @Param("mydate") Date mydate);
	
	
}
