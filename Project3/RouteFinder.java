/** This class is a set of methods used to determine
  * a working delivery schedule
  * @author Bret Black */

import java.util.*;
import java.io.*;

public class RouteFinder{
  /** This saves the main controller for the program */
  private Controller c;
  /** This list holds routes */
  private LinkedList<LinkedList<Truck>> routeList;
  /** This list of trucks represents a route */
  private LinkedList<Truck> route;
  
  /** Default constructor */
  public RouteFinder(){
    System.out.println("You forgot to specify parameters for the route finder!");
  }
  
  /** Constructor with parameters 
    * @param c The main controller for this instance*/
  public RouteFinder(Controller c){
    this.c = c;
    LineQueueList<Location> startingQueue = toQueue(getWarehouses()); // queue holds starting locations
    routeList = new LinkedList<LinkedList<Truck>>();
  }
  
  /** Creates a list of trucks to be used 
    * @param list The list of warehouses to use*/
  public void createTrucks(LinkedList<Location> list){
    // create new route
    route = new LinkedList<Truck>();
    // fill route with blank trucks
    int cnt = 0;
    Iterator<Location> it = list.iterator();
    
    while(it.hasNext()){
      Location loc = it.next();
      for (int j = 0; j < c.getWarehouseCount() ; j++){
        route.add(new Truck(cnt,c.getMaxStorage(),c.getMaxMilage(),loc));
        cnt++;
      }
    }
  }
  
  /** Calls internal methods to find a possible route */
  public void findRoute(){
    // create list of warehouses
    LinkedList<Location> masterList = getWarehouses();
    LinkedList<LinkedList<Truck>> workingList = new LinkedList<LinkedList<Truck>>(); // This list contains routes that work
    
    int i = 0;
    while (i < getWarehouses().size() && workingList.size() == 0){
    //for (int i = 0; i < getWarehouses().size();i++){
      // create trucks
      LinkedList<Location> copyList = new LinkedList<Location>();
      copyList = (LinkedList<Location>)masterList.clone();
      createTrucks(copyList);
      
      // create queue of warehouses to visit and get stores
      LineQueueList<Location> activeQueue = toQueue(copyList);
      LinkedList<Location> storeList = getStores();
      
      // iterate through warehouses and trucks, finding a route
      Iterator<Truck> routeIt = route.iterator();
      while(routeIt.hasNext() && storeList.size() > 0){
        // recursively call method that fills the truck
        Truck t = routeIt.next();
        
        // first node
        Location nearest = getNearestStore(t.getStartingLocation(),storeList);
        double dist = c.getNetwork().getShortestPathLength(nearest.getName(),t.getStartingLocation().getName());
        visitStore(t,nearest,dist,storeList);
        
        // loop until it is impossible for this truck to reach the nearest store and still return home
        while(storeList.size()>0 && checkTruck(t,getNearestStore(t.getLocList().getLast(),storeList),
                                               c.getNetwork().getShortestPathLength(nearest.getName(),t.getLocList().getLast().getName()))
             ){
          nearest = getNearestStore(t.getStartingLocation(),storeList);
          dist = c.getNetwork().getShortestPathLength(nearest.getName(),t.getLocList().getLast().getName());
          visitStore(t,nearest,dist,storeList);
        }
        
        // increment counter
        i++;
      }
      
      // add if the route was successful
      if (storeList.size() == 0){
        workingList.add(route);
      }
      
      // shift list
      masterList.add(masterList.getFirst());
    }
    
    // print best route
    if (workingList.size() > 0){
      System.out.println("A route was found!");
      writeRoute(workingList.getFirst());
    } else {
      System.out.println("A route was NOT found");
    }
  }
  
  /** Writes the working route to a file 
    * @param workingList The list of trucks*/
  public void writeRoute(LinkedList<Truck> route){
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("schedule.txt"));
      
      Iterator<Truck> it = route.iterator();
      
      while (it.hasNext()){
        Truck t = it.next();
        
        // node and truck number
        writer.write(t.getStartingLocation().getName() + " " + t.getNumber() + " "); 
        
        // iterator for nodes
        Iterator<Location> nodeIt = t.getLocList().iterator(); 
        
        while(nodeIt.hasNext()){
          writer.write(nodeIt.next().getName() + " "); // nodes to visit
        }
        
        // iterator for resources
        Iterator<SupplyList.SupplyNode> resIt = t.getSupplyList().getMap().values().iterator();
        while(resIt.hasNext()){
          SupplyList.SupplyNode next = resIt.next();
          writer.write(next.getResource().getName() + " "); // resource name and ammount
          writer.write(next.getCurrent() + " ");
        }
        writer.newLine();
      }
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the output.");
    }
  }
  
  /** Checks to see if the truck can handle the new milage and space 
    * @param t The truck being used
    * @param n The store the truck is considering adding to its route
    * @param dist The distance between the truck's current location and the next node
    */
  public boolean checkTruck(Truck t, Location n,double dist){
    // compare store's needs to the truck's storage
    if(t.getCurrentStorage() + n.getSupplyList().getSpace() > t.getMaxStorage()){
      return false; // The storage would exceed the truck's maximum
    }
    
    // compare the new distance to the truck's gas
    if(t.getMilage() + dist + c.getNetwork().getShortestPathLength(n.getName(),t.getStartingLocation().getName()) > t.getMaxMilage()){
      return false; // The distance would exceed the truck's maximum gas
    }
    
    return true;
  }
  
  /** Add store to route
    * @param t The truck visiting the store
    * @param s The store being visited 
    * @param dist The distance between the truck's current location and the next node
    * @param storeList the list of stores that have to be visited
    */
  public void visitStore(Truck t, Location s, double dist, LinkedList<Location> storeList){
    // add supplies
    t.getSupplyList().combine(s.getSupplyList());
    
    // add location
    t.getLocList().add(s);
    
    // add milage
    t.setMilage((float)(t.getMilage() + dist));
    
    // mark store as visited by removing it from the list
    storeList.remove(s);
  }
  
  /** Gets the store nearest to the specified node
    * @param n The location to start from
    * @return The nearest store node */
  public Location getNearestStore(Location n, LinkedList<Location> s){
    // get node for specified location's key
    Iterator<Location> itStore = s.iterator();
    Location next = itStore.next();
    double closeDist = c.getNetwork().getShortestPathLength(n.getName(),next.getName());
    int closeKey = next.getName();
    
    // get distance to every store in need
    while (itStore.hasNext()){
      next = itStore.next();
      
      // update the closest store if this node is closer
      if(c.getNetwork().getShortestPathLength(n.getName(),next.getName()) < closeDist){
        closeDist = c.getNetwork().getShortestPathLength(n.getName(),next.getName());
        closeKey = next.getName();
      }
    }
    
    // return the one that is closest
    return c.getNetwork().getVertex(closeKey).getElement();
  }
  
  /** Converts a LinkedList into a LineQueueList
    * @param ll The Linked List to convert
    * @return A new queue */
  public LineQueueList<Location> toQueue(LinkedList<Location> ll){
    Iterator<Location> it = ll.iterator();
    LineQueueList<Location> queue = new LineQueueList<Location>();
    
    while(it.hasNext()){
      queue.add(it.next());
    }
    
    return queue;
  }
  
  /** Get a list of warehouses
    * @return The warehouses as a LinkedList */
  public LinkedList<Location> getWarehouses(){
    // create linked list
    LinkedList<Location> l = new LinkedList<Location>();
    Iterator<UnDirectedGraph<Integer,Location>.UnDirectedGraphNode<Integer,Location>> it = c.getNetwork().iterator();
    
    // iterate and add
    while(it.hasNext()){
      // check to see if the location should be added
      Location nextLoc = it.next().getElement();
      if(nextLoc.getSpec() == Location.Type.WAREHOUSE){
        l.add(nextLoc);
      }
    }
    
    // return
    return l;
  }
  
  /** Gets a list of stores
    * @return The stores as a LinkedList */
  public LinkedList<Location> getStores(){
    // create linked list
    LinkedList<Location> l = new LinkedList<Location>();
    Iterator<UnDirectedGraph<Integer,Location>.UnDirectedGraphNode<Integer,Location>> it = c.getNetwork().iterator();
    
    // iterate and add
    while(it.hasNext()){
      // check to see if the location should be added
      Location nextLoc = it.next().getElement();
      if(nextLoc.getSpec() == Location.Type.STORE){
        l.add(nextLoc);
      }
    }
    
    // return
    return l;
  }
  
  /** Gets the controller being used by this instance 
    * @return The controller being used by this instance
    */
  public Controller getController(){
    return c;
  }
  
  /** Get route 
    * @return The latest route for the trucks*/
  public LinkedList<Truck> getRoute(){
    return route;
  }
  
  /** Get route list 
    * @return A LinkedList containing the possible routes */
  public LinkedList<LinkedList<Truck>> getRouteList(){
    return routeList;
  }
}