package it.diepet.spring.tx.eventpublisher.test.app.listener;

import org.springframework.context.ApplicationListener;

import it.diepet.spring.tx.eventpublisher.test.app.event.CustomTransactionalEvent;
import it.diepet.spring.tx.eventpublisher.test.util.StringCollector;

public class CustomTransactionalEventListener implements ApplicationListener<CustomTransactionalEvent> {

	@Override
	public void onApplicationEvent(CustomTransactionalEvent event) {
		StringCollector.add("Processed transactional event having message: " + event.getMessage());
	}

}
