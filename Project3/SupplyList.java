/** This class contains a list of resources, as well as the ammount contained and the ammount needed.
  * This can be used by both the trucks and the stores
  * @author Bret Black
  */

import java.util.*;

public class SupplyList {
  /** This data structure is used to hold resources */
  private HashMap<String,SupplyNode> t;
  
  /** The default constructor */
  public SupplyList(){
    t = new HashMap<String,SupplyNode>();
  }
  
  /** Adds a resource to the tree
    * @param r The resource to add 
    * @param max The maximum value of the resource
    */
  public void addResource(Resource r,int max){
    t.put(r.getName(),new SupplyNode(r,max));
  }
  
  /** Adds a resource to the tree with a starting value
    * @param r The resource to add 
    * @param srt The starting value of the resource
    */
  public void addResourceSpace(Resource r,int srt){
    t.put(r.getName(),new SupplyNode(r,srt));
    t.get(r.getName()).setCurrent(srt);
  }
  
  /** Gets a node from the list
    * @param name The name of the resource to pull */
  public SupplyNode getNode(String name){
    return t.get(name);
  }
  
  /** Adjusts the current value of a resource 
    * @param name The name of the resource
    * @param v The new value for the resource
    */
  public void adjustValue(String name, int v){
    t.get(name).setCurrent(v);
  }
  
  /** Get the total space consumed by this list
    * @return The space required to hold these supplies */
  public int getSpace(){
    // iterate through nodes
    int totalSpace = 0;
    Collection<SupplyNode> nodeCol = t.values();
    Iterator<SupplyNode> it = nodeCol.iterator();
    
    // multiply resource count by resource space
    while (it.hasNext()){
      SupplyNode s = it.next();
      totalSpace += (s.getCurrent()*s.getResource().getCost());
    }
    
    // return
    return totalSpace;
  }
  
  /** Adds the resources from another supply list to this list
    * @param s The supply list to add */
  public void combine(SupplyList s){
    Iterator<SupplyNode> it = s.getMap().values().iterator();
    
    while(it.hasNext()){
      SupplyNode r = it.next();
      
      if(t.get(r.getResource().getName()) == null){
        // new resource
        addResourceSpace(r.getResource(),r.getMax());
      } else {
        // adjust resource
        adjustValue(r.getResource().getName(),r.getMax()+getNode(r.getResource().getName()).getCurrent());
      }
    }
  }
  /** Gets the hashmap that holds the nodes
    * @return The underlying hashmap */
  public HashMap<String,SupplyNode> getMap(){
    return t;
  }
  
  /** This inner-class represents a resource, it also describes how many are contained and how many are needed */
  public class SupplyNode {
    /** The associated resource */
    private Resource res;
    /** The ammount needed */
    private int max;
    /** The ammount currently held */
    private int cur;
    
    /** Default constructor */
    public SupplyNode(){
    }
    
    /** Constructor with parameters
      * @param res The associated resource
      * @param max The ammount needed
      * @param srt The ammount to start with */
    public SupplyNode(Resource res, int max){
      this.res = res;
      this.max = max;
      this.cur = 0;
    }
    
    // Setters and getters */
    /** Get resource
      * @return The associated resource */
    public Resource getResource(){
      return res;
    }
    
    /** Get max
      * @return the max ammount needed */
    public int getMax(){
      return max;
    }
    
    /** Get current
      * @return the current resource count */
    public int getCurrent(){
      return cur;
    }
    
    /** Set current
      * @param n New count for the resource */
    public void setCurrent(int n){
      this.cur = n;
    }
  }
}