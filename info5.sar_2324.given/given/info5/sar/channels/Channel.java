package info5.sar.channels;

/*
 * Channel is a point-to-point stream of bytes.
 * Full-duplex, each end point can be used to read or write.
 * A connected channel is FIFO and lossless. 
 * A channel can be disconnected at any time, from either side.
 * 
 * Overall, this class should not be considered thread safe 
 * since the read and write operations may complete partially.
 * However, it is safe to use one thread to read and one thread 
 * to write. It is also safe to use different threads to operate
 * sequentially on a channel, like a message layer sending or 
 * receiving messages through, using different threads. 
 * Note this is correct only if the needed synchronization
 * to ensure the atomicity of the send or receive operations 
 * is handled properly at the message layer.
 * 
 * It is safe to disconnect a channel from any thread. This means
 * that a channel can be disconnect from one thread while other
 * threads are blocked in a read or write operation. These blocked
 * operations will be interrupted throwing a disconnected exception. 
 */
public abstract class Channel {
  Broker broker;
  Channel channel;
  CircularBuffer in;
  CircularBuffer out;
  enum state {OPEN, CLOSE};
  state etat;

  protected Channel(Broker broker) {
    this.broker = broker;
    this.in = new CircularBuffer(64);
    this.out = new CircularBuffer(64);
    this.channel = null;
    etat = state.OPEN;
  }
  
  protected Channel (Channel channel) {
	  this.in = channel.out;
	  this.out = channel.in;
	  this.channel = channel;
	  etat = state.OPEN;
	  }

  public Broker getBroker() {
    return broker;
  }

  /*
   * Not thread-safe. Read bytes in the given array, starting at the given offset.
   * At most "length" bytes will be read. If there are no bytes available, this
   * method will block until there are bytes available or the end of stream is
   * reached.
   * 
   * @returns the number of bytes read, may not be zero.
   * @throws DisconnectedException if this channel is disconnected.
   */
  public abstract int read(byte[] bytes, int offset, int length) throws DisconnectedException;

  /*
   * Not thread-safe. Write bytes from the given array, starting at the given
   * offset. At most "length" bytes will be written. If there is no room to write
   * any byte, this method will block until there is room or the end of stream is
   * reached.
   * 
   * @returns the number of bytes written, may not be zero.
   * @throws DisconnectedException if this channel is disconnected.
   */
  public abstract int write(byte[] bytes, int offset, int length) throws DisconnectedException;

  /*
   * Thread-safe disconnects this channel, unblocking any thread 
   * blocked read or write operation.
   */
  public abstract void disconnect();

  /*
   * @returns true if this channel is disconnected (thread-safe)
   */
  public abstract boolean disconnected();

}
