package it.diepet.spring.tx.eventpublisher.test.app.dao;

import it.diepet.spring.tx.eventpublisher.test.app.model.Product;

public class ProductDAO extends AbstractJpaDAO<Product> {
	public ProductDAO() {
		setClazz(Product.class);
	}
}
