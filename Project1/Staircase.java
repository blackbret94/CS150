/**
 * A staircase container, uses an ArrayList to hold instances
 * of the Stairs class.  Ground floor is 0
 * 
 * @author Bret Black 
 */

import java.util.*;

public class Staircase extends ArrayList<Stairs> {
  
  /** Capacity of stairs */
  private int stairsCap;
  
  /** The default constructor */
  public Staircase(){
    super();
    System.out.println("You forgot to assign parameters to a staircase!");
  }
  
  /** Constructor that takes floors into account
    * @param floors the number of stairs to create (number of floors in a building)
    * @param stairsCap the maximum number of people allowed on the stair at once
    */
  public Staircase(int floors, int stairsCap){
    super();
    
    this.stairsCap = stairsCap;
    
    // create emergency exit
    addStair(new Stairs(1));
    
    for (int i = 0; i < floors; i++){
      addStair(new Stairs(stairsCap));
    }
  }
  
  /** Adds a stair to the list 
    * @newStair the stair to add
    */
  public void addStair(Stairs newStair){
    add(newStair);
  }
  
  /** Runs the stairs and transfers Person instances */
  public void oneTimeStep(){
    // loop through list and run stairs
    for (int i = 0; i < size(); i++){
      // run using param and save output
      Person returnedPerson = get(i).oneTimeStep();
      
      // if a person was returned, add to next stair
      if (returnedPerson != null){
        // add to next stair if this is not the ground floor
        if (i != 0){
          if(!get(i-1).addPerson(returnedPerson)){
            // add back into normal stair and set wait time to 1 less than the max
            returnedPerson.setWaitTime(1);
            get(i).addPerson(returnedPerson);
          }
        } else {
          returnedPerson.incExit();
        }
      }
    }
  }
  
  /** Adds the specified person to the specified floor
    * @param newPerson the instance to add
    * @param floor the floor to add to
    */
  public void addToStair(Person newPerson, int floor){
    get(floor).addPerson(newPerson);
  }
  
  /** Gets the specified stair
    * @return The specified floor */
  public Stairs getStair(int floor){
    if (floor < size()){
      return get(floor);
    } else {
      return null;
    }
  }
}