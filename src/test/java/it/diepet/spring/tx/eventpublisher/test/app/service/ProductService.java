package it.diepet.spring.tx.eventpublisher.test.app.service;

import java.util.List;

import it.diepet.spring.tx.eventpublisher.test.app.model.Product;

public interface ProductService {

	void add(Product product);

	List<Product> findAll();

}
