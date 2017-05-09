package it.diepet.spring.tx.eventpublisher.test.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.diepet.spring.tx.context.TransactionContextManager;
import it.diepet.spring.tx.eventpublisher.test.app.dao.ProductDAO;
import it.diepet.spring.tx.eventpublisher.test.app.model.Product;
import it.diepet.spring.tx.eventpublisher.test.util.StringCollector;

public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private TransactionContextManager transactionContextManager;

	@Override
	@Transactional
	public void add(Product product) {
		LOGGER.debug("[START] add()");
		StringCollector.add("productService.add()");
		productDAO.create(product);
		transactionContextManager.getTransactionContext().setAttribute("operation", "add");
		transactionContextManager.getTransactionContext().addListAttribute("productList", product);
		transactionContextManager.getTransactionContext().addSetAttribute("productSet", product);
		LOGGER.debug("[END] add()");
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		LOGGER.debug("[START] findAll()");
		StringCollector.add("productService.findAll()");
		List<Product> result = productDAO.findAll();
		transactionContextManager.getTransactionContext().setAttribute("operation", "findAll");
		LOGGER.debug("[END] findAll()");
		return result;
	}

}
