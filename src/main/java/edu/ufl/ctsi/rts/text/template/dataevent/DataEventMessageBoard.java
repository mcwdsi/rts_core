package edu.ufl.ctsi.rts.text.template.dataevent;

import java.util.concurrent.LinkedBlockingDeque;

public class DataEventMessageBoard {

	static LinkedBlockingDeque<DataEvent> queue;
	
	
	
	static synchronized void start() {
		if (queue == null) {
			queue = new LinkedBlockingDeque<DataEvent>(50);
		}
	}
	
	static void publish(DataEvent e) {
		boolean added;
		do {
			added = queue.offerLast(e);
			if (!added) queue.removeFirst();
		} while (!added);
	}
	
	/*
	 * Subscribe to all "topics" aka all data events
	 */
	static void subscribeAll(DataEventSubscriber s) {
		
	}
	
	
}
