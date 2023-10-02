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
  public Channel accept(int port) {
    throw new RuntimeException("NYI");
  }

  @Override
  public synchronized Channel connect(String name, int port) {
	  if (BrokerManager.exist(name)) {
		  if (ports.contains(port)) {
			  return new CChannel (this, port);
		  }
	  }
	  return null;
  }

}
