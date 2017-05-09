package it.diepet.spring.tx.eventpublisher;

import org.springframework.context.ApplicationEvent;

/**
 * The Interface TransactionalEventPublisher.
 */
public interface TransactionalEventPublisher {

	/**
	 * Publish event.
	 * 
	 * If the service is called inside a transactional context and the event is
	 * an instance of
	 * it.diepet.spring.tx.eventpublisher.event.TransactionalEvent then the
	 * event will be published only after the transaction success (commit
	 * succeeds).
	 * 
	 * Else the event will be published immediately, by using the injected
	 * Spring instance of org.springframework.context.ApplicationEventPublisher.
	 *
	 * @param event
	 *            the event
	 */
	public void publishEvent(ApplicationEvent event);
}
