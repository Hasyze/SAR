package info5.sar.channels.tests;

import info5.sar.channels.Broker;
import info5.sar.channels.Channel;
import info5.sar.channels.DisconnectedException;
import info5.sar.channels.Task;

public class TestServer implements Runnable {
  static boolean VERBOSE = false;
  static void log(String s) {
    if (VERBOSE)
      System.out.println(s);
  }
  static void log(Throwable th) {
    if (VERBOSE)
      th.printStackTrace();
  }

  int port;

  TestServer(int port) {
    this.port = port;
  }

  @Override
  public void run() {
    int cno = 0;
    Channel ch;
    System.out.println("Server started!");
    try {
      Task task = (Task) Thread.currentThread();
      Broker broker = task.getBroker();
      while (true) {
        ch = broker.accept(port);
        String name = broker.getName() + ":Worker[" + cno + "]";
        Task client = new Task(name, broker);
        client.start(new _Client(ch, cno++));
      }
    } finally {
      System.out.println("Server done.");
    }
  }

  class _Client implements Runnable {
    Channel ch;
    int no;

    _Client(Channel ch, int no) {
      this.ch = ch;
      this.no = no;
    }

    @Override
    public void run() {
      log("Server: worker[" + no + "] started.");
      try {
        byte bytes[] = new byte[128];
        while (true) {
          int n = read(bytes);
          write(bytes, n);
          log("Server: worker[" + no + "] echoed " + n + " bytes!");
        }
      } catch (DisconnectedException ex) {
        log("Server: worker[" + no + "] done.");
      } catch (Throwable th) {
        // what should we do here? 
        // There is no rationale for an unknown exception,
        // so let's be fail-stop.
        Test.failStop(th);
      }
    }

    private void write(byte[] bytes, int nbytes) throws DisconnectedException {
      int offset = 0;
      int remaining = nbytes;
      while (remaining != 0) {
        int n = ch.write(bytes, offset, remaining);
        Test.ensure(n > 0);
        log("Server: worker[" + no + "] wrote " + n + " bytes!");
        offset += n;
        remaining -= n;
      }
    }

    private int read(byte[] bytes) throws DisconnectedException {
      int n = ch.read(bytes, 0, bytes.length);
      Test.ensure(n > 0);
      return n;
    }
  }

}
