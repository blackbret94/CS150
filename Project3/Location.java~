/** This class represents a a location on the graph
  * @author Bret Black */

public class Location {
  /** Id/name */
  private int name;
  /** Supply list tracks what is needed by this store */
  private SupplyList sl;
  /** Enum used to specify town, warehouse, or store */
  public enum Type {
    TOWN, WAREHOUSE, STORE
  }
  /** Uses the Type enum to specify the node */
  private Type spec;
  
  /** The default constructor */
  public Location(){
    System.out.println("You forgot to specify parameters for a location!");
  }
  
  /** Constructor with parameters
    * @param name The name of the location */
  public Location(int name){
    this.name = name;
    spec = Type.TOWN;
    sl = new SupplyList();
  }
  
  /** Changes the specification of the location 
    * @param t The type be specified as
    */
  public void setSpec(String t){
    if (t.equals("warehouse")){
      spec = Type.WAREHOUSE;
    } else if (t.equals("store")){
      spec = Type.STORE;
    } else if (t.equals("town")){
      spec = Type.TOWN;
    }
  }
  
  /** Resets the SupplyList */
  public void resetSupplyList(){
    sl = new SupplyList();
  }
  
  /** gets the specification of the location 
    * @return The specification of this location
    */
  public Type getSpec(){
    return spec;
  }
  
  /** Gets the ID
    * @return The ID */
  public int getName(){
    return name;
  }
  
  /** Gets the underlying SupplyList
    * @return The supply list */
  public SupplyList getSupplyList(){
    return sl;
  }
}