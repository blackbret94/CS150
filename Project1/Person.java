/**
 * A person is a simple placeholder
 * that will be passed from floor to floor
 * 
 * @author Bret Black 
 */

public class Person {
  /** The time this person has waited on the current stair */
  private int waitTime;
  
  /** Staticlly counts the number of persons created */
  private static int personCount = 0;
  
  /** Statically counts the people who have exited */
  private static int personExited = 0;
  
  /** The default constructor */
  public Person(){
    waitTime = 0;
    personCount++;
  }
  
  /** Constructor with wait time 
    * @param waitTime starting wait time
    */
    public Person(int waitTime){
    this.waitTime = waitTime;
    personCount++;
  }
  
  /** Creates an instance of the person without incremementing the counter */
  public Person(boolean accessorBoolean){
    waitTime = 0;
    
    // DO NOT INCREMENT
  }
  
  /** Dec the waitTime */
  public void decWaitTime(){
    waitTime--;
  }
  
  /** Resets the waitTime */
  public void resetWaitTime(){
    waitTime = 0;
  }
  
  /** Sets the waitTime to the specified value
    * @param i the value to set the wait time to
    */
  public void setWaitTime(int i){
    waitTime = i;
  }
  
  /** Increments the personExited counter */
  public void incExit(){
    personExited++;
  }
  
  // SETTERS AND GETTERS
  /** Gets the waitTime
    * @return the waitTime */
  public int getWaitTime(){
    return waitTime;
  }
  
  /** Gets the number of persons created 
    * @return the number of people who have been created
    */
  public int getPersonCount(){
    return personCount;
  }
  
  /** Gets the number of persons who have exited the building
    * @return the number of persons who have exited the building */
  public int getPersonExited(){
    return personExited;
  }
}