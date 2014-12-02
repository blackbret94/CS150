/**
 * Creates a queue out of a LinkedList
 * 
 * @author Bret Black 
 */

import java.util.*;

public class LineQueueList<E> extends AbstractQueue<E>{
  /** List used as a queue */
  private LinkedList<E> listForQueue;
  
  /** Default constructor */
  public LineQueueList(){
    // call super
    super();
    
    // init arrayList
    listForQueue = new LinkedList<E>();
  }
  
  /** Add to the queue
    * @return True if data is added
    */
  public boolean add(E e){
    listForQueue.add(e);
    return true;
  }
  
  /** The size of the queue
    * @return The size of the queue
    */
  public int size(){
    // return
    return listForQueue.size();
  }
  
  /** Returns the front of the queue without removing it
    * @return The front of the queue
    */
  public E peek(){
    return listForQueue.peek();
  }
  
  /** Returns and removes the front of the queue
    * @return Null if empty, otherwise first element is returned
    */
  public E poll(){
    // return null if the list is empty
    if (!isEmpty()){
      // return element
      return listForQueue.remove();
    } else return null;
  }
  
  // ADDITIONAL INHERITANCE
  // not needed
  public boolean offer(E e){
    return false;
  }
  
  // not needed
  public Iterator<E> iterator(){
    return null;
  }
}