package com.example.demo.pagination;

import java.util.List;

// Una implementación de un paginado
public class Pagination<T> {
	private List<T> elements;
	private int page;
	private int size;
	private int prev;
	private int next;
	private int maxPage;

	public Pagination(List<T> elements, int page, int size, int prev, int next, int maxPage) {
		super();
		this.elements = elements;
		this.page = page;
		this.size = size;
		this.prev = prev;
		this.next = next;
		this.maxPage = maxPage;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

}
