package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.DTODaylyProductReport;
import com.example.demo.pojo.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// En esta consulta se obtiene el reporte de los productos vendidos por día, detellando la fecha
	// la cantidad de productos vendidos, y el monto recaudado, guardando la información
	// en un DTO
	@Query(value =""
			+ "SELECT  new com.example.demo.pojo.DTODaylyProductReport(CONCAT(CONCAT(CONCAT(EXTRACT(YEAR FROM cp.date), '-'), EXTRACT(MONTH FROM cp.date)), CONCAT('-', EXTRACT(DAY FROM cp.date))), CAST(COUNT(p) AS int), CAST(SUM(cp.cant * p.price) AS int)) "
			+ "FROM ClientProduct cp, Product p "
			+ "WHERE cp.product.idProduct = p.idProduct "
			+ "GROUP BY CONCAT(CONCAT(CONCAT(EXTRACT(YEAR FROM cp.date), '-'), EXTRACT(MONTH FROM cp.date)), CONCAT('-', EXTRACT(DAY FROM cp.date))) "
			+ "ORDER BY CONCAT(CONCAT(CONCAT(EXTRACT(YEAR FROM cp.date), '-'), EXTRACT(MONTH FROM cp.date)), CONCAT('-', EXTRACT(DAY FROM cp.date)))")
	public Page<DTODaylyProductReport> getProductSelectByDate(PageRequest limit);
	
	// Se consulta la tablas de cliente_producto y producto, el resultado obtenido
	// se muestra ordenando de mayor a menor los productos en base a la cantidad que  
	// vendio ese producto
	@Query(value =""
			+ "SELECT SUM(cp.cant), p "
			+ "FROM ClientProduct cp, Product p "
			+ "WHERE cp.product.idProduct = p.idProduct "
			+ "GROUP BY (p) "
			+ "ORDER BY SUM(cp.cant) DESC")
	public Object[] getMostSelledProduct(PageRequest limit);

}
