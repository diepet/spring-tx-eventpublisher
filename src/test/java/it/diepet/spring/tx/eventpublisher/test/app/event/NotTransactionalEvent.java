package it.diepet.spring.tx.eventpublisher.test.app.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NotTransactionalEvent extends ApplicationEvent {

	private String message;

	public NotTransactionalEvent(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
