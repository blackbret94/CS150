/** This class controls the simulation
  * @author Bret Black */

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Controller {
  /** Graph containing ALL nodes */
  private UnDirectedGraph<Integer,Location> network;
  /** Number of trucks per warehouse */
  private int warehouseCount;
  /** Maximum storage for a truck */
  private int maxStorage;
  /** Max milage of trucks */
  private int maxMilage;
  /** ArrayList of resources */
  private HashMap<String,Resource> resourceList;
  
  // main
  public static void main(String[] args){
    // check size of args
    if (args.length >= 5){
      // start clock
      long startTime = System.nanoTime();
      
      Controller c = new Controller(args[0],args[1],args[2],args[3],args[4]);
      c.runSimulation();
      
      // end clock and print results
      long stopTime = System.nanoTime();
      
      System.out.println("Time: " + (stopTime - startTime));
    } else System.out.println("You were missing some parameters");
  }
  
  /** Default constructor */
  public Controller(){
    
  }
  
  // CHANGE THIS SO THAT THE CONSTRUCTOR WORKS WITHOUT CALLING THESE???
  /** Constructor with parameters
    * @param resourceIn The file to read resources from
    * @param townIn The file to read towns from
    * @param connectIn The file to read connectivity from
    * @param specIn The file to read specification from
    * @param needIn The file to read the needs of each store from */
  public Controller(String resourceIn, String townIn, String connectIn, String specIn, String needIn){
    // read resources
    resourceList = readResources(new File(resourceIn));
    
    // create graph
    network = readNetwork(new File(townIn),new File(connectIn),new File(specIn),new File(needIn));
  }
  
  /** Run simulation */
  public void runSimulation(){
    // run sim
    RouteFinder r = new RouteFinder(this);
    r.findRoute();
  }
  
  /** Read in resources.  Store them in an ArrayList, which will later be used to
    * create supply lists.
    * @param in The name of the file to read in
    * @return The list of resources and storage costs as an ArrayList */
  public HashMap<String,Resource> readResources(File in){
    // create arrayList
    HashMap<String,Resource> al = new HashMap<String,Resource>();
    
    try {
    // create scanner and pattern
    Scanner s = new Scanner(in);
    Pattern p = Pattern.compile("(\\D*)(\\:)(\\s)(\\d*)(\\s?)(\\d*)");
    
    // read in file
    while (s.hasNext()){
      // get pattern and key
      s.findInLine(p);
      MatchResult match = s.match();
      String key = match.group(1);
      
      if (key.equals("warehouse")){
        // read warehouse count
        warehouseCount = Integer.parseInt(match.group(4));
      } else if (key.equals("truck")){
        // read truck count
        maxStorage = Integer.parseInt(match.group(4));
        // read milage
        maxMilage = Integer.parseInt(match.group(6));
      } else {
        // read resources
        // create new resource and add
        Resource r = new Resource(key,Integer.parseInt(match.group(4)));
        al.put(key,r);
      }
      
      // go to next line
      s.nextLine();
    }
    
    // save reference and return
    resourceList = al;
    return al;
    
    } catch (java.io.FileNotFoundException e){
      System.out.println("These are not the files you are looking for...");
      System.out.println("Please fix your resource parameter");
      return null;
    }
  }
  
  /** Create the graph that represents the transportation network
    * @param townIn The file to read towns from
    * @param connectIn The file to read connectivity from
    * @param specIn The file to read specification from
    * @param needIn The file to read the needs of each store from
    * @return The transportation graph */
  public UnDirectedGraph readNetwork(File townIn, File connectIn, File specIn, File needIn){
    // create graph
    UnDirectedGraph<Integer,Location> g = new UnDirectedGraph<Integer,Location>();
    
    // read towns
    readTowns(g,townIn);
    
    // connect towns
    readConnectivity(g,connectIn);
    
    // specify warehouses and stores
    readSpecification(g,specIn);
    
    // add needs to stores
    readNeeds(g,needIn);
    
    // return graph
    return g;
  }
  
  /** Read towns
    * @param network The graph to use for the network
    * @param file The file to read from */
  public void readTowns(UnDirectedGraph<Integer,Location> network, File file){
    try {
      // create scanner and pattern
      Scanner s = new Scanner(file);
      Pattern p = Pattern.compile("(\\d*)(\\s)(\\D)(\\d*)(\\D)(\\s)(\\d*)");
      
      // read in file
      while (s.hasNext()){
        // get pattern and key
        s.findInLine(p);
        MatchResult match = s.match();
        int name = Integer.parseInt(match.group(1));
        
        // create node and add to graph
        Location newL = new Location(name);
        // add to graph
        network.addNode(name,newL);
        
        s.nextLine();
      }
    } catch (java.io.FileNotFoundException e){
      System.out.println("These are not the files you are looking for...");
      System.out.println("Please fix your locations parameter");
    }
  }
  
  /** Read connectivity
    * @param network The graph to use for the network
    * @param file The file to read from */
  public void readConnectivity(UnDirectedGraph<Integer,Location> network, File file){
    try {
      // create scanner and pattern
      Scanner s = new Scanner(file);
      Pattern p = Pattern.compile("(\\d*)(\\s)(\\D*)(\\s)(\\d*)(\\s)(\\d*)");
      
      // read in file
      while (s.hasNext()){
        // get pattern and key
        s.findInLine(p);
        MatchResult match = s.match();
        int n1 = Integer.parseInt(match.group(1)); // first node
        int n2 = Integer.parseInt(match.group(5)); // second node
        int w = Integer.parseInt(match.group(7)); // weight
        
        // add edge
        if (network.addEdge(n1,n2,w)){
        } else System.out.println("An edge could not be added");
        
        if (s.hasNext()) s.nextLine();
      }
    } catch (java.io.FileNotFoundException e){
      System.out.println("These are not the files you are looking for...");
      System.out.println("Please fix your connectivity parameter");
    }
  }
  
  /** Read specifications
    * @param network The graph to use for the network
    * @param file The file to read from */
  public void readSpecification(UnDirectedGraph<Integer,Location> network, File file){
    try {
      // create scanner and pattern
      Scanner s = new Scanner(file);
      Pattern p = Pattern.compile("(\\D*)(\\s)(\\d*)");
      
      // read in file
      while (s.hasNext()){
        // get pattern and key
        s.findInLine(p);
        MatchResult match = s.match();
        int name = Integer.parseInt(match.group(3));
        String spec = match.group(1);
        
        // find location and change specification
        network.getVertex(name).getElement().setSpec(spec);
        
        if (s.hasNext()) s.nextLine();
      }
    } catch (java.io.FileNotFoundException e){
      System.out.println("These are not the files you are looking for...");
      System.out.println("Please fix your locations parameter");
    }
  }
  
  /** Read needs
    * @param network The graph to use for the network
    * @param file The file to read from */
  public void readNeeds(UnDirectedGraph<Integer,Location> network, File file){
    try {
      // create scanner and pattern
      Scanner s = new Scanner(file);
      Pattern p = Pattern.compile("(\\d*)(\\s)(\\D*)(\\s)(\\d*)");
      
      // read in file
      while (s.hasNext()){
        // get pattern and key
        s.findInLine(p);
        MatchResult match = s.match();
        int name = Integer.parseInt(match.group(1));
        String resName = match.group(3);
        int max = Integer.parseInt(match.group(5));
        
        // find location and add to supply list
        // get supply list
        SupplyList netList = network.getVertex(name).getElement().getSupplyList();
        // get resource
        Resource r = resourceList.get(resName);
        // add
        if (r != null){
          netList.addResourceSpace(r,max); // need way to compare resources
        } else System.out.println("An undefined resource was almost added - error caught!");
        
        if (s.hasNext()) s.nextLine();
      }
    } catch (java.io.FileNotFoundException e){
      System.out.println("These are not the files you are looking for...");
      System.out.println("Please fix your needs parameter");
    }
  }
  
  // setters and getters
  /** Gets the number of trucks per warehouse
    * @return The number of trucks per warehouse */
  public int getWarehouseCount(){
    return warehouseCount;
  }
  
  /** Gets the truck count
    * @return Truck count */
  public int getMaxStorage(){
    return maxStorage;
  }
  
  /** Get the maximum milage
    * @return Maximum milage for the trucks */
  public int getMaxMilage(){
    return maxMilage;
  }
  
  /** Gets the list of resources 
    * @return The HashMap list of resources*/
  public HashMap<String,Resource> getResourceList(){
    return resourceList;
  }
  
  /** Gets the network
    * @return the Network graph */
  public UnDirectedGraph<Integer,Location> getNetwork(){
    return network;
  }
}