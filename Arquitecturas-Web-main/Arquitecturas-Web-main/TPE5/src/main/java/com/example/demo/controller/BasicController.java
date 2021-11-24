package com.example.demo.controller;

import org.springframework.http.ResponseEntity;

import com.example.demo.pagination.Pagination;

/** 
* @brief Interaz que define los mÃ©todos comunes para todos los controladores
*/ 
public interface BasicController<T> {

	/**
	* @brief Dado que la base de datos contiene varias tuplas, se implemento un paginado
	*/ 
	ResponseEntity<Pagination<T>> getAll(int page, int size);

	public ResponseEntity<T> getById(Long id);

	public ResponseEntity<T> save(T o);

	public ResponseEntity<T> delete(Long id);

	public ResponseEntity<T> update(T o);

}
