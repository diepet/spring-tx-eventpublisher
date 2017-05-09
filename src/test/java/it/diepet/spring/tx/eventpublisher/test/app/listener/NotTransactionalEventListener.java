package it.diepet.spring.tx.eventpublisher.test.app.listener;

import org.springframework.context.ApplicationListener;

import it.diepet.spring.tx.eventpublisher.test.app.event.NotTransactionalEvent;
import it.diepet.spring.tx.eventpublisher.test.util.StringCollector;

public class NotTransactionalEventListener implements ApplicationListener<NotTransactionalEvent> {

	@Override
	public void onApplicationEvent(NotTransactionalEvent event) {
		StringCollector.add("Processed NOT transactional event having message: " + event.getMessage());
	}

}
