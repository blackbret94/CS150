/**
 * Creates a queue out of an ArrayList
 * 
 * @author Bret Black 
 */

import java.util.*;

public class LineQueueArray<E> extends AbstractQueue<E>{
  /** ArrayList to store data */
  private ArrayList<E> listForQueue;
  /** Flips with every step, if True then the first person in the list is removed */
  private boolean shouldRemove = false;
  
  /** Default constructor */
  public LineQueueArray(){
    // call super
    super();
    
    // init arrayList
    listForQueue = new ArrayList<E>();
  }
  
  /** Add to the back of the queue
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
    // shrink
    listForQueue.trimToSize();
    
    // return
    return listForQueue.size();
  }
  
  /** Returns the front of the queue without removing it
    * @return The front of the queue
    */
  public E peek(){
    return listForQueue.get(0);
  }
  
  /** Removes the front of the queue and returns the element
    * @return Null if empty, otherwise first element is returned
    */
  public E poll(){
    // return null if the list is empty
    if (!isEmpty()){
      // save element
      E toReturn = listForQueue.get(0);
      
      // remove element
      listForQueue.remove(0);
      
      // return element
      return toReturn;
    } else return null;
  }
  
  /** Called every step and decides if someone should be removed 
    * @return The front of the queue
    */
  public E oneTimeStep(){
    if(!isEmpty()){
      if (shouldRemove){
        // flip the boolean
        shouldRemove = false;
        
        // remove and return the front
        return poll();
      } else {
        // flip the boolean
        shouldRemove = true;
      }
      // flip the boolean
    } else shouldRemove = false;
    return null;
  }
  
  // SETTERS AND GETTERS
  /** Gets the state of removal
    * @return True if data will be removed on the next step
    */
  public boolean getShouldRemove(){
    return shouldRemove;
  }
  
  /** Gets the ArrayList being used
    * @returns the ArrayList being used
    */
  public ArrayList<E> getArrayList(){
    return listForQueue;
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