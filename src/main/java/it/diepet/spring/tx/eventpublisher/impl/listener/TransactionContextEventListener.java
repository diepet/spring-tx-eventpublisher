package it.diepet.spring.tx.eventpublisher.impl.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;

import it.diepet.spring.tx.context.TransactionContext;
import it.diepet.spring.tx.context.event.TransactionContextEvent;
import it.diepet.spring.tx.eventpublisher.event.TransactionalEvent;
import it.diepet.spring.tx.eventpublisher.impl.constants.Constants;

/**
 * Listener for it.diepet.spring.tx.context.event.TransactionContextEvent in
 * order to publish the list of transactional events collected in a transaction
 * context during the transaction execution.
 */
public class TransactionContextEventListener
		implements ApplicationListener<TransactionContextEvent>, ApplicationEventPublisherAware {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionContextEventListener.class);

	/** The application event publisher. */
	private ApplicationEventPublisher applicationEventPublisher;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(final TransactionContextEvent event) {
		LOGGER.debug("Start retrieving transactional events from the published transaction context");
		final TransactionContext transactionContext = event.getTransactionContext();
		final List<TransactionalEvent> transactionalEventList = (List<TransactionalEvent>) transactionContext
				.getAttribute(Constants.TRANSACTIONAL_EVENT_LIST_ATTRIBUTE_NAME);
		if (transactionalEventList != null) {
			for (TransactionalEvent transactionalEvent : transactionalEventList) {
				this.applicationEventPublisher.publishEvent(transactionalEvent);
			}
		}
		LOGGER.debug("Published all events found in the transaction context (if any)");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationEventPublisherAware#
	 * setApplicationEventPublisher
	 * (org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

}
