package info5.sar.channels;

public class CChannel extends Channel {
	
	protected int port;

  protected CChannel(Broker broker, int port) {
    super(broker);
    throw new RuntimeException("NYI");
  }

  @Override
  public int read(byte[] bytes, int offset, int length) {
    throw new RuntimeException("NYI");
  }

  @Override
  public int write(byte[] bytes, int offset, int length) {
    throw new RuntimeException("NYI");
  }

  @Override
  public void disconnect() {
    throw new RuntimeException("NYI");
  }

  @Override
  public boolean disconnected() {
    throw new RuntimeException("NYI");
  }

}
