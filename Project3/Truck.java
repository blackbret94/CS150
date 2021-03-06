/** This class represents a truck.  It contains a supply list and tracks milage 
  * @author Bret Black
  */
import java.util.*;

public class Truck {
  /** Supply list tracks what is on the truck */
  private SupplyList sl;
  /** Tracks milage */
  private float milage;
  /** The maximum milage */
  private float maxMilage;
  /** Maximum storage */
  private int maxStorage;
  /** The truck's number */
  private int number;
  /** Determines if the truck has exceeded milage */
  private boolean stop;
  /** Tracks where the truck starts and ends */
  private Location startingLoc;
  /** List of Locations to be visited */
  private LinkedList<Location> locList;
  
  /** Default constructor */
  public Truck(){
    System.out.println("Parameters for a truck were not specified!");
  }
  
  /** Constructor with parameters
    * @param n The number of the truck
    * @param s The max storage on the truck
    * @param m The max milage for the truck
    * @param srt The starting node for the truck
    */
  public Truck(int n, int s, float m, Location srt){
    sl = new SupplyList();
    milage = 0;
    maxMilage = m;
    maxStorage = s;
    number = n;
    stop = false;
    startingLoc = srt;
    locList = new LinkedList<Location>();
  }
  
  // setters and getters
  /** Gets the supply list
    * @return supply list */
  public SupplyList getSupplyList(){
    return sl;
  }
  
  /** Gets the current milage
    * @return The current milage */
  public float getMilage(){
    return milage;
  }
  
  /** Sets the current milage
    * @param m New milage 
    * @return False if the truck is out of gas, true if it is okay.
    */
  public boolean setMilage(float m){
    milage = m;
    if (milage > maxMilage){
      stop = true;
    }
    
    // return true if the truck is okay
    if (stop){
      return false;
    } else {
      return true;
    }
  }
  
  /** Gets the max milage 
    * @return The max milage */
  public float getMaxMilage(){
    return maxMilage;
  }
  
  /** Gets the max storage 
    * @return The max storage */
  public int getMaxStorage(){
    return maxStorage;
  }
  
  /** Gets the current storage
    * @return The current storage */
  public int getCurrentStorage(){
    return sl.getSpace();
  }
  
  /** Gets the truck's number
    * @return The truck's identification number */
  public int getNumber(){
    return number;
  }
  
  /** Gets the boolean that represents whether or not the truck has run out of gas
    * @return True if the truck is out of gas, false if it is not */
  public boolean getStop(){
    return stop;
  }
  
  /** Gets the list of locations
    * @return The list of locations */
  public LinkedList<Location> getLocList(){
    return locList;
  }
  
  /** Gets the starting location for the truck
    * @return The starting location */
  public Location getStartingLocation(){
    return startingLoc;
  }
}