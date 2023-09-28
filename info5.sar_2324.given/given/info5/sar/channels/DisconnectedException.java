package info5.sar.channels;

public class DisconnectedException extends Exception {
  public DisconnectedException() {
    super();
  }

  public DisconnectedException(String message) {
    super(message);
  }

  public DisconnectedException(String message, Throwable cause) {
    super(message, cause);
  }

  public DisconnectedException(Throwable cause) {
    super(cause);
  }
}
