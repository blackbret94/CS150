/**
 * A stair holds instances of the Person class and passes them
 * on to the next stair when it is time.
 * 
 * @author Bret Black 
 */

import java.util.*;

public class Stairs {
  /** The queue that holds people waiting to go to the next stair */
  private LineQueueArray<Person> waitingList;
  
  /** The number of people allowed on the stair at once */
  private int max;
  
  /** The current count on the clock */
  private int clock;
  
  /** The default constructor */
  public Stairs(){
    // create waiting list
    waitingList = new LineQueueArray<Person>();
    
    // save param
    max = 12;
    
    // set clock
    clock = 0;
  }
  
  /** Constructor that takes floor
    * @param max the maximum number of people allowed on the stair
    */
  public Stairs(int max){
    // create waiting list
    waitingList = new LineQueueArray<Person>();
    
    // save param
    this.max = max;
    
    // set clock
    clock = 0;
  }
  
  /** Runs the queue and returns any person who was popped
    * @param newPerson the person to add if not equal to null
    * @return Person exiting queue
    */
  public Person oneTimeStep(){
    // run clock
    clock();
    
    // print occupacy
    //System.out.println(waitingList.size());
    
    // see if a person can be removed
    return removePerson();
  }
  
  /** Adds a person to the queue if not full
    * @param newPerson the person to add
    * @return True if the person was added
    */
  public boolean addPerson(Person newPerson){
    // check number of people
    if (waitingList.size() < max){
      // add to queue
      if (newPerson != null){
        waitingList.add(newPerson);
        newPerson.setWaitTime(max);
        return true;
      }
    }
    return false;
  }
  
  /** Removes a person from the queue if possible
    * @return the person removed
    */
  public Person removePerson(){
    // decide if someone can exit
    if (!waitingList.isEmpty()){
      if (waitingList.peek().getWaitTime() <= 0){
        // remove from the top of the list
        return waitingList.poll();
      }
    }
    return null;
  }
  /** Runs the clock, which increments the wait time of the persons
    */
  public void clock(){
    
    // run through arrayList and increment
    for (int i = 0; i < waitingList.size(); i++){
      waitingList.getArrayList().get(i).decWaitTime();
    }
  }
  
  /** Used to check if the staircase is full
    * @return true if the staircase is full */
  public boolean isFull(){
    if (waitingList.getArrayList().size() < max){
      return false;
    } else return true;
  }
  
  //SETTERS AND GETTERS
  /** Get occupacy
    * @return The number of people on the stair
    */
  public int getSize(){
    return waitingList.size();
  }
  
  /** Gets the max number of people
    * @return the max number of people
    */
  public int getMax(){
    return max;
  }
}