  /** This class represents a resource
    * @author Bret Black */

  public class Resource {
    /** The name of the resource */
    private String name;
    /** The cost of the resource in storage space */
    private int cost;
    
    /** The default constructor */
    public Resource(){
      System.out.print("You did not specify the name or storage cost of a resource!  Defaulting to filler values");
    }
    
    /** Constructor with parameters
      * @param name The name of the resource
      * @param cost The storage cost of the resource */
    public Resource(String name, int cost){
      this.name = name;
      this.cost = cost;
    }
    
    /** Compares to another resource
      * @param r The other resource
      * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object. This is determined by the alphabetical order of the name of the objects.*/
    public int compareTo(Resource r){
      return getName().compareTo(r.getName());
    }
    
    // setters and getters
    /** This gets the name
      * @return The name of the resource */
    public String getName(){
      return name;
    }
    
    /** this gets the storage cost
      * @return The storage cost of the resource */
    public int getCost(){
      return cost;
    }
  }