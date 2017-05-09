package it.diepet.spring.tx.eventpublisher.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import it.diepet.spring.tx.context.TransactionContextManager;
import it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher;
import it.diepet.spring.tx.eventpublisher.constants.Constants;
import it.diepet.spring.tx.eventpublisher.event.TransactionalEvent;

public class TransactionalEventPublisherImpl implements TransactionalEventPublisher, ApplicationEventPublisherAware {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalEventPublisher.class);

	/** The application event publisher. */
	private ApplicationEventPublisher applicationEventPublisher;

	/** The transaction context manager. */
	private TransactionContextManager transactionContextManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher#
	 * publishEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void publishEvent(final ApplicationEvent event) {
		if (event instanceof TransactionalEvent && transactionContextManager.isTransactionActive()) {
			transactionContextManager.getTransactionContext()
					.addListAttribute(Constants.TRANSACTIONAL_EVENT_LIST_ATTRIBUTE_NAME, event);
			LOGGER.debug("Added a transaction event to transaction context");
		} else {
			LOGGER.debug("Transaction inactive or not a transactional event: publish by using the original publisher");
			this.applicationEventPublisher.publishEvent(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.ApplicationEventPublisherAware#
	 * setApplicationEventPublisher
	 * (org.springframework.context.ApplicationEventPublisher)
	 */
	@Override
	public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * Sets the transaction context manager.
	 *
	 * @param transactionContextManager
	 *            the new transaction context manager
	 */
	public void setTransactionContextManager(final TransactionContextManager transactionContextManager) {
		this.transactionContextManager = transactionContextManager;
	}
}