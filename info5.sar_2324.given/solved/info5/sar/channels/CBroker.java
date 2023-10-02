package info5.sar.channels;

import java.util.ArrayList;
import java.util.Hashtable;

public class CBroker extends Broker {
	
	protected ArrayList <Task> tasks; 
//	protected Hashtable <Task, Integer> channels;

  public CBroker(String name) {
    super(name);
    synchronized(this) {
    	tasks = new ArrayList<Task>();
    }	
//    channels = new Hashtable<Task, Integer> ();
  }

  @Override
  public synchronized Channel accept(int port) {
    while (!connexions.containsKey(port)) {
    	try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    return new CChannel ( (CChannel) connexions.get(port));
  }

  @Override
  public synchronized Channel connect(String name, int port) {
	  if (BrokerManager.exist(name)) {
		  notify();
		  CChannel channel =  new CChannel (this, port);	
		  connexions.put(port, channel);
		  return channel;
		  }
	  return null;
  }

}
