package uk.ac.rhul.rms;

import javafx.concurrent.Task;


/**
 * Allows the customer to call the waiter.
 *
 * @author Unknown
 */
public class WaiterCallNotifier extends Task<Void> {

  @Override
  protected Void call() throws Exception {
    int i = 0;
    while (true) {
      System.out.println("i: " + i);
      Thread.sleep(2000);
      i++;
    }
  }
}
