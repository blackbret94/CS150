import java.util.*;
/**
 * A building contains floors and staircases.
 * 
 * @author Bret Black 
 */

import java.util.*;

public class Building {
  /** Number of floors */
  private int floors;
  
  /** Number of staircases */
  private int staircases;
  
  /** Capacity of stairs */
  private int stairsCap;
  
  /** Mean number of tenants per floor */
  private int tenantsMean;
  
  /** Variance of number of tenants per floor */
  private int tenantsVar;
  
  /** Contains staircases */
  private ArrayList<Staircase> staircaseList;
  
  /** Contains floors */
  private ArrayList<Floor> floorList;
  
  /** Default constructor*/
  public Building(){
    this.floors = 5;
    this.staircases = 5;
    this.stairsCap = 5;
    this.tenantsMean = 31;
    this.tenantsVar = 12;
    
    staircaseList = new ArrayList<Staircase>();
    floorList = new ArrayList<Floor>();
  }
  
  /** Constructor with parameters
    * @param floors The number of floors to build
    * @param staircases the number of staircases to build
    * @param stairsCap that maximum number of people on each stair at a time`
    * @param tenantsMean the average number of people per floor
    * @param tenantsVar the variance of people per floor
    */
  public Building(int floors, int staircases, int stairsCap, int tenantsMean, int tenantsVar){
    this.floors = floors;
    this.staircases = staircases;
    this.stairsCap = stairsCap;
    this.tenantsMean = tenantsMean;
    this.tenantsVar = tenantsVar;
    
    staircaseList = new ArrayList<Staircase>();
    floorList = new ArrayList<Floor>();
    
    // add staircases
    createStaircases(staircases,floors,stairsCap);
    
    // add floors
    createFloors(floors,tenantsMean,tenantsVar);
  }
  
  /** Create floors
    * @param floors the number of floors to create
    * @param tenantsMean the average number of tenants per floor
    * @param tenantsVar the variance in tenantsMean
    */
  public void createFloors(int floors, int tenantsMean, int tenantsVar){
    for (int i = 0; i < floors; i++){
      // check if this is a third floor
      boolean isThird;
      if ((i-1)%3 == 0){
        isThird = true;
      } else isThird = false;
      
      // add floor
      floorList.add(new Floor(tenantsMean,tenantsVar, isThird));
      
      // add related stairs to floor
      for (int j = 0; j < staircaseList.size(); j++){
        // stair to add
        Stairs stair = staircaseList.get(j).getStair(i+1);
        
        // add the stair
        floorList.get(i).getStairList().add(stair);
      }
    }
  }
  
  /** Create stairs
    * @param staircases the number of staircases to create
    * @param floors the number of stairs to add to the staircases
    * @param stairsCap the number of people allowed on each floor
    */
  public void createStaircases(int staircases, int floors, int stairsCap){
    for (int i = 0; i < staircases; i++){
      staircaseList.add(new Staircase(floors, stairsCap));
    }
  }
  
  /** One time step calls every method that should be run */
  public void oneTimeStep(){
    runFloors();
    runStaircases();
  }
  
  /** Run floors */
  public void runFloors(){
    for (int i = 0; i < floorList.size(); i++){
      floorList.get(i).oneTimeStep();
    }
  }
  
  /** Run staircases */
  public void runStaircases(){
    for (int i = 0; i < staircaseList.size(); i++){
      staircaseList.get(i).oneTimeStep();
    }
  }
  
  // SETTERS AND GETTERS
  /** Gets the list of staircases
    * @return The list of staircases
    */
  public ArrayList<Staircase> getStaircaseList(){
    return staircaseList;
  }
  
  /** Gets the list of floors 
    * @return the list of floors
    */
  public ArrayList<Floor> getFloorList(){
    return floorList;
  }
}