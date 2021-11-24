package com.example.demo.service;

import java.util.Optional;

import com.example.demo.pagination.Pagination;

// Una interfaz común para todos los servicios
public interface BasicService<T> {

	Pagination<T> findAll(int page, int size);

	public Optional<T> findById(Long id);

	// No se realizo un método para actualizar los datos dado que la función para
	// guardar los también lo hace
	public boolean save(T o);

	public boolean delete(T o);
}
