package info5.sar.channels;

import java.util.ArrayList;
import java.util.Hashtable;

public class BrokerManager {

	static ArrayList <String> brokers;
	
	public  BrokerManager () {
		brokers = new ArrayList <String>();
	}
	
//	public  BrokerManager (ArrayList <Broker> brokers) {
//		BrokerManager.brokers = brokers;
//	}
	
	static boolean exist (String name) {
		for (int i = 0; i < BrokerManager.brokers.size(); i++) {
			String brokerName = BrokerManager.brokers.get(i);
			if (brokerName == name) {
				return true;
			}
		}
		return false;
	}
	
	static void addBroker (String name) {
		for (int i = 0; i < BrokerManager.brokers.size(); i++) {
			String brokerName = BrokerManager.brokers.get(i);
			if (brokerName == name) {
				return;
			}
		}
		BrokerManager.brokers.add(name);
	}
	
}
