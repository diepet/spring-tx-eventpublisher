package it.diepet.spring.tx.eventpublisher.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import it.diepet.spring.tx.context.TransactionContextManager;
import it.diepet.spring.tx.eventpublisher.TransactionalEventPublisher;
import it.diepet.spring.tx.eventpublisher.event.TransactionalEvent;
import it.diepet.spring.tx.eventpublisher.impl.constants.Constants;
import it.diepet.spring.tx.eventpublisher.impl.listener.TransactionContextEventListener;

public class TransactionalEventPublisherImpl implements TransactionalEventPublisher, ApplicationEventPublisherAware {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalEventPublisher.class);

	/** The application event publisher. */
	private ApplicationEventPublisher applicationEventPublisher;

	/** The transaction context manager. */
	private TransactionContextManager transactionContextManager;

	/**
	 * The transaction context event listener for publishing the transactional
	 * events collected.
	 * 
	 * This property is unused, but it is injected here in order to collect in
	 * an unique abstract Spring bean the definitions of all beans needed and
	 * simplify the module configuration by inheriting one only abstract Spring
	 * bean.
	 * 
	 */
	@SuppressWarnings("unused")
	private TransactionContextEventListener transactionContextEventListener;

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
	@Required
	public void setTransactionContextManager(final TransactionContextManager transactionContextManager) {
		this.transactionContextManager = transactionContextManager;
	}

	/**
	 * Sets the transaction context event listener.
	 *
	 * @param transactionContextEventListener
	 *            the new transaction context event listener
	 */
	public void setTransactionContextEventListener(TransactionContextEventListener transactionContextEventListener) {
		this.transactionContextEventListener = transactionContextEventListener;
	}
}