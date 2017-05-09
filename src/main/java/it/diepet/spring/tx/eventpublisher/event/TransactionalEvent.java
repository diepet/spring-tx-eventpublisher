package it.diepet.spring.tx.eventpublisher.event;

import org.springframework.context.ApplicationEvent;

/**
 * The Class TransactionalEvent.
 */
public abstract class TransactionalEvent extends ApplicationEvent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7636620931395042074L;

	/**
	 * Instantiates a new transactional event.
	 *
	 * @param source
	 *            the source
	 */
	public TransactionalEvent(Object source) {
		super(source);
	}

}
