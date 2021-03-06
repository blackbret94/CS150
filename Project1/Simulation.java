/**
 * Simulation for the experiment
 * 
 * @author Bret Black 
 */
import java.io.*;
import java.util.*;

public class Simulation {
  /** Number of floors */
  private int floors;
  
  /** Number of stair cases */
  private int stairs;
  
  /** Capacity of stairs */
  private int stairsCap;
  
  /** Mean number of tenants per floor */
  private int tenantsMean;
  
  /** Variance of number of tenants per floor */
  private int tenantsVar;
  
  /** The building being used in this experiment */
  private Building buildingSim;
  
  /** Should the simulation stop after a certain number of steps */
  private Boolean shouldStop;
  
  /** The number of steps to stop after (if applicable) */
  private int maxSteps;
  
  /** Main method creates an instance of the class */
  public static void main(String[] args){
    new Simulation();
  }
  
  /** The default constructor */
  public Simulation(){
    // read file
    readText(new String("param.txt"));
    
    runSimulation();
  }
  
  /** Creates a Simulation without running it 
    * @param dontRun Indicates that the program should not run
    */
  public Simulation(int dontRun){
    
  }
  
  /** Runs the simulation */
  public void runSimulation(){
    int trialCount = 20;
    int totalSteps = 0;
    
    for (int i = 0; i < trialCount; i++){
      // create building
      buildingSim = createBuilding(floors,stairs,stairsCap,tenantsMean,tenantsVar);
      
      // run the test
      totalSteps += oneTest();
      if (totalSteps == -1){
        System.out.println("This trial exceeded its step limit!");
      } else {
        System.out.println("trial complete!"); 
      }
    }
    System.out.println("Experiment Complete!");
    System.out.println("Average Steps (Seconds):");
    System.out.println(totalSteps/trialCount);
  }
  
  
  /** Read text file
    * @param fileName the name of the file to be read
    */
  public void readText(String fileName){
    try {
      // create scanner
      Scanner inputScanner = new Scanner(new File(fileName));
      
      // read file
      floors = inputScanner.nextInt();
      stairs = inputScanner.nextInt();
      stairsCap = inputScanner.nextInt();
      tenantsMean = (int)inputScanner.nextDouble();
      tenantsVar = (int)inputScanner.nextDouble();
      int shouldStopInt = inputScanner.nextInt(); // this is to be converted to boolean below
      if (shouldStopInt == 1){
        shouldStop = true;
      } else {
        shouldStop = false;
      }
      maxSteps = inputScanner.nextInt();
      
      System.out.println(floors);
      System.out.println(stairs);
      System.out.println(stairsCap);
      System.out.println(tenantsMean);
      System.out.println(tenantsVar);
    } catch (java.io.FileNotFoundException e){
      System.out.println("File not found!");
    }
  }
  
  /** Create building
    * @param floors The number of floors to build
    * @param staircases the number of staircases to build
    * @param stairsCap that maximum number of people on each stair at a time
    * @param tenantsMean the average number of people per floor
    * @param tenantsVar the variance of people per floor
    * @return The created building
    */
  public Building createBuilding(int floors, int staircases, int stairsCap, int tenantsMean, int tenantsVar){
    return new Building(floors, staircases, stairsCap, tenantsMean,tenantsVar);
  }
  
  /** Runs a time step throughout the program 
    * @return The number of steps it took to evacuate, -1 if it took too long
    */
  public int oneTest(){
    // create an accessor instance
    Person personAccessor = new Person(true);
    int stepCounter = 0;
    
    System.out.println("Test has begun");
    
    // run processes
    while ((personAccessor.getPersonCount() > personAccessor.getPersonExited()) && (!shouldStop || (shouldStop && stepCounter < maxSteps))){
      // run a step
      stepCounter++;
      buildingSim.oneTimeStep();
      //System.out.println("One step has run!" + personAccessor.getPersonExited() + "/" + personAccessor.getPersonCount());
    }
    
    if (shouldStop && stepCounter >= maxSteps){
      System.out.println("THIS EXCEEDS THE MAXIMUM NUMBER OF STEPS");
      return -1;
    }
    
    // output the results
    //outputResults(stepCounter, personAccessor);
    return stepCounter;
  }
  
  /** Outputs the results of the simlulation 
    * @param stepCounter the number of steps required to evacuate the building
    * @param personAccessor a Person instance used for access purposes
    */
  public void outputResults(int stepCounter, Person personAccessor){
    System.out.println("Total persons: " + personAccessor.getPersonCount());
    System.out.println("Exited persons: " + personAccessor.getPersonExited());
    System.out.println("Seconds: " + stepCounter);
    System.out.println("Minutes: " + stepCounter/60);
  }
  
  // SETTERS AND GETTERS
  /** Get floors
    * @return The number of floors */
  public int getFloors(){
    return floors;
  }
  
  /** Gets stairs
    * @return The number of staircases */
  public int getStairs(){
    return stairs;
  }
  
  /** Gets stairsCap
    * @return the maximum number of people allowed on the stairs */
  public int getStairsCap(){
    return stairsCap;
  }
  
  /** Gets tenantsMean
    * @return the average number of people per floor */
  public int getTenantsMean(){
    return tenantsMean;
  }
  
  /** Gets tenantsVariance
    * @return the variance in tenant count */
  public int getTenantsVar(){
    return tenantsVar;
  }
  
  /** See if the program is set to stop 
    * @return whether or not the program stops
    */
  public boolean getShouldStop(){
    return shouldStop;
  }
  
  /** Get the max number of steps the program can run (if applicable) 
    * @return the max number of steps 
    */
  public int getMaxSteps(){
    return maxSteps;
  }
  }