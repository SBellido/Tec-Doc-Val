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

	/**
	* @brief Dado que la tabla Client_Product tiene una PK compuesta, se realizo una consulta que busca solo por la PK del cliente
	* @param idClient id del cliente a buscar
	*/
	@Query(value="SELECT cp FROM ClientProduct cp WHERE cp.id.idClient = :idClient ")
	List<ClientProduct> findByIdClient(@Param("idClient") Long idClient);
	
	/**
	* @param idProduct id del producto a buscar
	*/
	@Query(value="SELECT cp FROM ClientProduct cp WHERE cp.id.idProduct = :idProduct")
	List<ClientProduct> findByIdProduct(@Param("idProduct") Long idProduct);
	
	/**
	* @brief 
	* @param idClient id del cliente que realizo las compras
	* @param mydate fecha a buscar
	*/
	@Query(value=""
			+ "SELECT SUM(cp.cant) "
			+ "FROM ClientProduct cp "
			+ "WHERE cp.id.idClient = :idClient "
			+ "AND cp.date = :mydate "
			+ "GROUP BY (cp.id.idClient)")
	Integer findAmountBoughtProductsByDate(@Param("idClient") Long idClient, @Param("mydate") Date mydate);
	
	
}
