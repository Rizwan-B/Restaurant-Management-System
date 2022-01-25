package uk.ac.rhul.rms;

import java.util.LinkedList;

/**
 * A queue facade to be used for handling the storage of orders. 
 *
 * @author Lucas Kimber
 *
 */
public class OrderQueue {
  private LinkedList<MenuItem> queue = new LinkedList<MenuItem>();
  
  /**
   * Adds an item to the queue.
   *
   * @param newItem The MenuItem to be added.
   */
  public void add(MenuItem newItem) {
    this.queue.add(newItem);
  }
  
  /**
   * Returns the MenuItem from the front of the queue. 
   *
   * @return The MenuItem at the front of the queue.
   */
  public MenuItem dequeue() {
    if (this.queue.size() == 0) {
      return null;
    }
    return this.queue.removeFirst();
  }
}
