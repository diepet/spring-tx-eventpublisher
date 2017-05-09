package it.diepet.spring.tx.eventpublisher.test.app.event;

import it.diepet.spring.tx.eventpublisher.event.TransactionalEvent;

@SuppressWarnings("serial")
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
