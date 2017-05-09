package it.diepet.spring.tx.eventpublisher.test.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher;
import it.diepet.spring.tx.eventpublisher.test.app.dao.ProductDAO;
import it.diepet.spring.tx.eventpublisher.test.app.event.CustomTransactionalEvent;
import it.diepet.spring.tx.eventpublisher.test.app.event.NotTransactionalEvent;
import it.diepet.spring.tx.eventpublisher.test.app.model.Product;
import it.diepet.spring.tx.eventpublisher.test.util.StringCollector;

public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private TransactionalEventPublisher transactionalEventPublisher;

	@Override
	@Transactional
	public void add(Product product) {
		LOGGER.debug("[START] add()");
		StringCollector.add("productService.add()");
		productDAO.create(product);
		LOGGER.debug("[END] add()");
	}

	@Override
	@Transactional
	public List<Product> findAll() {
		LOGGER.debug("[START] findAll()");
		StringCollector.add("productService.findAll()");
		List<Product> result = productDAO.findAll();
		LOGGER.debug("[END] findAll()");
		return result;
	}

	@Override
	@Transactional
	public void publishTransactionalEvent() {
		LOGGER.debug("[START] publishTransactionalEvent()");
		StringCollector.add("BEFORE PUBLISHING");
		transactionalEventPublisher.publishEvent(new CustomTransactionalEvent("publishTransactionalEvent"));
		StringCollector.add("AFTER PUBLISHING");
		LOGGER.debug("[END] publishTransactionalEvent()");
	}

	@Override
	@Transactional
	public void publishNotTransactionalEvent() {
		LOGGER.debug("[START] publishNotTransactionalEvent()");
		StringCollector.add("BEFORE PUBLISHING");
		transactionalEventPublisher.publishEvent(new NotTransactionalEvent("publishNotTransactionalEvent"));
		StringCollector.add("AFTER PUBLISHING");
		LOGGER.debug("[END] publishNotTransactionalEvent()");
	}

	@Override
	public void publishTransactionalEventWithoutTransactionalAnnotation() {
		LOGGER.debug("[START] publishTransactionalEventWithoutTransactionalAnnotation()");
		StringCollector.add("BEFORE PUBLISHING");
		transactionalEventPublisher
				.publishEvent(new CustomTransactionalEvent("publishTransactionalEventWithoutTransactionalAnnotation"));
		StringCollector.add("AFTER PUBLISHING");
		LOGGER.debug("[END] publishTransactionalEventWithoutTransactionalAnnotation()");
	}

	@Override
	public void publishNotTransactionalEventWithoutTransactionalAnnotation() {
		LOGGER.debug("[START] publishNotTransactionalEventWithoutTransactionalAnnotation()");
		StringCollector.add("BEFORE PUBLISHING");
		transactionalEventPublisher
				.publishEvent(new NotTransactionalEvent("publishNotTransactionalEventWithoutTransactionalAnnotation"));
		StringCollector.add("AFTER PUBLISHING");
		LOGGER.debug("[END] publishNotTransactionalEventWithoutTransactionalAnnotation()");
	}

}
