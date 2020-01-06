package edu.ufl.ctsi.rts.text.template.dataevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class DataEventMessageBoard {

	/*
	 * The queue maintains a maximum of the 50 most recent events.  We can make this value 
	 *   something configurable later, but right now it's hard coded.
	 *   
	 *   Whenever there is a subscription made, the message board will iterate over all the 
	 *   	events currently in the queue and notify the new subscriber.  This behavior, too,
	 *   	we ought to eventually enable the user of the message board to decide (no thanks, 
	 *   	don't send me the entire queue, just send me whatever is new from my subscription
	 *   	onward).
	 */
	static LinkedBlockingDeque<DataEvent> queue;
	static HashMap<AbstractDataEventFilter, ArrayList<DataEventSubscriber>> filterSubscriberMap;
	
	/*
	 * We can either ask each subscriber to check each event against its filter
	 * 
	 * Or the subscriber can pass a filter when it subscribes, then we hash each filter against a list of subscribers to that filter aka "topic".
	 * 
	 * Actually, for "partial" matches, the latter approach won't work. Actually, if we hash the filter itself, then whenever and
	 *   event is published, we go through all filters in the hash, test each one, and then only pass the event to the associated 
	 *   list of subscribers if the filter passes.  Only have to test each filter once.  Not quite as good as only just getting 
	 *   the lists that want the event but still better than making each subscriber test the same filter over and over and over...
	 */
	
	public static synchronized void start() {
		if (queue == null) {
			queue = new LinkedBlockingDeque<DataEvent>(50);
			filterSubscriberMap = new HashMap<AbstractDataEventFilter, ArrayList<DataEventSubscriber>>();
		}
	}
	
	public static void publish(DataEvent e) {
		addEventToQueue(e);
		broadcastEventToSubscribers(e);
	}
	
	protected static void addEventToQueue(DataEvent e) {
		boolean added;
		do {
			added = queue.offerLast(e);
			if (!added) queue.removeFirst();
		} while (!added);
	}
	
	protected static void broadcastEventToSubscribers(DataEvent e) {
		Iterator<AbstractDataEventFilter> filterIterator = filterSubscriberMap.keySet().iterator();
		while (filterIterator.hasNext()) {
			AbstractDataEventFilter ef = filterIterator.next();
			System.err.println("Checking data event against filter: ");
			System.err.println("\tData event type: " + e.getDataEventType());
			if (ef instanceof DataEventFilter) {
				DataEventFilter def = (DataEventFilter)ef;
				System.err.println("\tFilter event type: " + def.getDataEventType());
			}
			if (ef.passes(e)) {
				ArrayList<DataEventSubscriber> subs = filterSubscriberMap.get(ef);
				for (DataEventSubscriber sub : subs) {
					sub.notify(e);
				}
			}
		}
	}
	
	/*
	 * Subscribe to all "topics" aka all data events
	 */
	public static void subscribeAll(DataEventSubscriber s) {
		subscribe(s, new NullDataEventFilter());
	}
	
	public static void subscribe(DataEventSubscriber s, AbstractDataEventFilter f) {
		if (!filterSubscriberMap.containsKey(f)) {
			ArrayList<DataEventSubscriber> list = new ArrayList<DataEventSubscriber>();
			list.add(s);
			filterSubscriberMap.put(f, list);
		} else {
			ArrayList<DataEventSubscriber> list = filterSubscriberMap.get(f);
			list.add(s);
		}
		
		/*
		 * Lastly, run all the events currently in the queue past
		 * 	this new subscriber.
		 */
		for (DataEvent e : queue) {
			if (f.passes(e)) {
				s.notify(e);
			}
		}
	}
	
	public static void unsubscribe(DataEventSubscriber s, AbstractDataEventFilter f) {
		if (filterSubscriberMap.containsKey(f)) {
			ArrayList<DataEventSubscriber> list = filterSubscriberMap.get(f);
			list.remove(s);
		}
	}
	
	
}
