package info5.sar.channels;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Brokers are there to permit to establish channels.
 * 
 * Each broker must be uniquely named. 
 * 
 * Each broker may be used to accept on different ports concurrently,
 * although multiple tasks would need to be used since the
 * accept is a blocking rendez-vous with a connect, on a
 * matching port.
 */
public abstract class Broker {
  String name;
  Hashtable <Integer, Channel> connexions;
  /* 
   * Each broker must be uniquely named. 
   * @throws IllegalArgumentException if the name is not unique.
   */
  protected Broker(String name) {
	  if (!BrokerManager.exist(name)) {
		  this.name = name;
		  synchronized(this){
			  BrokerManager.addBroker(name);
		  }
	  }else {
		  throw new IllegalArgumentException("");
	  }
	  connexions = new Hashtable <Integer, Channel> ();
  }
  
  /*
   * @returns the name of this broker.
   */
  public String getName() { return name; }
  
  /*
   * Indicate that this broker will accept one connection
   * on the given port and return a fully connected channel.
   * This is a thread-safe blocking rendez-vous. 
   * @throws IllegalArgumentException if there is already 
   *         an accept pending on the given port.
   */
  public abstract Channel accept(int port);
  
  /*
   * Attempts a connection to the given port, via 
   * the broker with the given name. 
   * If such a broker cannot be found, this method returns null.
   * If the broker is found, this connect will block until
   * an accept on the given port is pending.
   * This is a thread-safe blocking rendez-vous. 
   * Note: multiple accepts from different tasks with 
   *       the same name and port are legal
   */
  public abstract Channel connect(String name, int port);
}
