# spring-tx-eventpublisher [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
A Spring Framework 3.2 plugin for publishing events only after a successfull transaction.

This plugin could be useful in projects still using Spring 3.2, because adds to these projects a similar feature implemented by using the [@TransactionalEventListener](https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2) annotation available in Spring 4.

# Requisites

* Java 1.6 or higher
* Spring 3.2 or higher (but for Spring 4 uses [@TransactionalEventListener](https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2) annotation)
* [spring-tx-lifecycle](https://github.com/diepet/spring-tx-lifecycle)
* [spring-tx-context](https://github.com/diepet/spring-tx-context)


# Configuration

* Configure a transaction manager as explained [here](https://github.com/diepet/spring-tx-lifecycle), in order to dispatch transaction lifecycle events.
* Configure a transaction context manager as explained [here](https://github.com/diepet/spring-tx-context).
* Import `META-INF/eventpublisher-tx-spring-application-context.xml` Spring configuration file.
* Define a new Spring bean inheriting its configuration from `transactionalEventPublisherAbstract` abstract bean and injects the transaction context manager by setting the property `transactionContextManager`.

Example:

```xml
	<!-- TX Manager configuration by using spring-tx-lifecycle -->		
	<bean id="transactionManager" class="it.diepet.spring.tx.lifecycle.EventDispatcherJpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"></property>
	</bean>
	
	<!--Creates a transaction context manager by using spring-tx-context -->
	<import resource="classpath:META-INF/context-tx-spring-application-context.xml"/>
	<bean id="transactionContextManager" parent="transactionContextManagerAbstract" />
	
	<!--import spring-tx-eventpublisher configuration: no any instance will be created in the Spring context-->
	<import resource="classpath:META-INF/eventpublisher-tx-spring-application-context.xml"/>
	
	<!-- Creates the transactional event publisher and injects the transaction context manager -->
	<bean id="transactionalEventPublisher" parent="transactionalEventPublisherAbstract">
		<!-- Required -->
		<property name="transactionContextManager" ref="transactionContextManager" />
	</bean> 
```

And that's it. The configuration for using the transactional event publisher is completed.

# Usage


After configured a transactional event publisher, creates a custom event by extending the abstract class:

`it.diepet.spring.tx.eventpublisher.event.TransactionalEvent`

(note that `it.diepet.spring.tx.eventpublisher.event.TransactionalEvent` is a subclass of the Spring base event abstract class `org.springframework.context.ApplicationEvent`).

For example:

```Java

import it.diepet.spring.tx.eventpublisher.event.TransactionalEvent;

public class CustomTransactionalEvent extends TransactionalEvent {

	private String message;

	public CustomTransactionalEvent(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
```
Finally, injects the transactional event publisher in a class executing operations inside a transaction. All the events published will be really published only if the transaction completes successfully (commit succeeds).

For example:

```Java

import it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher;

public class ProductServiceImpl implements ProductService {

	@Autowired
	private TransactionalEventPublisher transactionalEventPublisher;

	@Transactional
	public void createProduct(Product p) {
		
		// some db stuff
		
		// the event will be published automatically later, 
		// only if the transaction completes successfully
		transactionalEventPublisher.publishEvent(new CustomTransactionalEvent("Product created"));
		
		// some db stuff
	}

} 
```

# Furthermore notes

The transactional event publisher:

`it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher`

and the Spring classic event publisher:

`org.springframework.context.ApplicationEventPublisher`

are two different interfaces, but share the same method for publishing events:

`void publishEvent(ApplicationEvent event);`

So the transactional event publisher can be used for publishing any kind of Spring event class. But if the published event extend the `org.springframework.context.ApplicationEvent` class but not extend `it.diepet.spring.tx.eventpublisher.event.TransactionalEvent` class, than the event will be published immediately just like the Spring `org.springframework.context.ApplicationEventPublisher` should do.

Likewise, if we publish an event using `it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher` in an operation executed outside a transaction, the event will be published immediately (even if the class event is a subclass of `it.diepet.spring.tx.eventpublisher.event.TransactionalEvent`).

Example:

```Java

public class ProductServiceImpl implements ProductService {

	@Autowired
	private TransactionalEventPublisher transactionalEventPublisher;

	@Transactional
	public void f() {
		
		// some db stuff
		
		// the event will be published after that the transaction completes successfully
		transactionalEventPublisher.publishEvent(new CustomTransactionalEvent("Launched f()"));
		
		// some db stuff
	}

	public void g() {
		
		// the event will be published immediately, 
		// because g() does not have a @Transactional annotation
		transactionalEventPublisher.publishEvent(new CustomTransactionalEvent("Launched g()"));
		
	}


} 

```

# License

This project is licensed under the terms of the MIT license.


