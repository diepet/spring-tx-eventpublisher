package it.diepet.spring.tx.eventpublisher.test.app;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.diepet.spring.tx.eventpublisher.test.app.model.Product;
import it.diepet.spring.tx.eventpublisher.test.app.service.ProductService;
import it.diepet.spring.tx.eventpublisher.test.util.StringCollector;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/test-spring-tx-eventdispatcher-application-context.xml")
public class TxEventPublisherAppTest {

	@Autowired
	private ProductService productService;

	@Before
	public void init() {
		StringCollector.reset();
	}

	@Test
	public void testPersistence() {
		Product product = new Product();
		product.setId(9L);
		product.setCode("99999");
		product.setDescription("Apple");
		productService.add(product);
		List<Product> productList = productService.findAll();

		Assert.assertNotNull(productList);
		Assert.assertEquals(4, productList.size());

		List<String> stringList = StringCollector.getList();
		Assert.assertNotNull(stringList);
		Assert.assertEquals(2, stringList.size());
		Assert.assertEquals("productService.add()", stringList.get(0));
		Assert.assertEquals("productService.findAll()", stringList.get(1));
	}

	@Test
	public void testPublishTransactionalEvent() {
		productService.publishTransactionalEvent();
		List<String> stringList = StringCollector.getList();
		Assert.assertNotNull(stringList);
		Assert.assertEquals(3, stringList.size());
		Assert.assertEquals("BEFORE PUBLISHING", stringList.get(0));
		Assert.assertEquals("AFTER PUBLISHING", stringList.get(1));
		Assert.assertEquals("Processed transactional event having message: publishTransactionalEvent",
				stringList.get(2));
	}

	@Test
	public void testPublishNotTransactionalEvent() {
		productService.publishNotTransactionalEvent();
		List<String> stringList = StringCollector.getList();
		Assert.assertNotNull(stringList);
		Assert.assertEquals(3, stringList.size());
		Assert.assertEquals("BEFORE PUBLISHING", stringList.get(0));
		Assert.assertEquals("Processed NOT transactional event having message: publishNotTransactionalEvent",
				stringList.get(1));
		Assert.assertEquals("AFTER PUBLISHING", stringList.get(2));
	}

	@Test
	public void testPublishTransactionalEventWithoutTransactionalAnnotation() {
		productService.publishTransactionalEventWithoutTransactionalAnnotation();
		List<String> stringList = StringCollector.getList();
		Assert.assertNotNull(stringList);
		Assert.assertEquals(3, stringList.size());
		Assert.assertEquals("BEFORE PUBLISHING", stringList.get(0));
		Assert.assertEquals(
				"Processed transactional event having message: publishTransactionalEventWithoutTransactionalAnnotation",
				stringList.get(1));
		Assert.assertEquals("AFTER PUBLISHING", stringList.get(2));
	}

	@Test
	public void testPublishNotTransactionalEventWithoutTransactionalAnnotation() {
		productService.publishNotTransactionalEventWithoutTransactionalAnnotation();
		List<String> stringList = StringCollector.getList();
		Assert.assertNotNull(stringList);
		Assert.assertEquals(3, stringList.size());
		Assert.assertEquals("BEFORE PUBLISHING", stringList.get(0));
		Assert.assertEquals(
				"Processed NOT transactional event having message: publishNotTransactionalEventWithoutTransactionalAnnotation",
				stringList.get(1));
		Assert.assertEquals("AFTER PUBLISHING", stringList.get(2));
	}

}
