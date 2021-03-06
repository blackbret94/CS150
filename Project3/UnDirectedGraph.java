/** This class implements an undirected graph
  * @author Bret Black */

import java.util.*;
import java.io.*;

public class UnDirectedGraph<K,E> {
  /** ArrayList keeps track of nodes */
  private HashMap<K,UnDirectedGraphNode<K,E>> nodeList;
  /** Static to track infinity */
  public static final double INFINITY = Double.MAX_VALUE;
  /** The default constructor */
  public UnDirectedGraph(){
    nodeList = new HashMap<K,UnDirectedGraphNode<K,E>>();
  }
  
  /** Adds a node
    * @param k The key for the new node
    * @param e The element of the new node
    * @return True if the node is sucessfully added
    */
  public boolean addNode(K k, E e){
    nodeList.put(k,new UnDirectedGraphNode<K,E>(e,k));
    return true;
  }
  
  /** Adds an edge between two nodes
    * @param k1 The key of the first node
    * @param k2 The key of the second node
    * @param w The weight of the edge 
    * @return True if the edge is added
    */
  public boolean addEdge(K k1, K k2, int w){
    UnDirectedGraphNode<K,E> v1 = nodeList.get(k1);
    UnDirectedGraphNode<K,E> v2 = nodeList.get(k2);
    // make sure verticies already exist
    if (v1 != null && v2 != null){
      v1.addEdge(new UnDirectedGraphEdge<E>(v2,w));
      v2.addEdge(new UnDirectedGraphEdge<E>(v1,w));
      return true;
    } else return false;
  }
  
  /** Gets the length of a given path
    * @@param k1 The key of the start node
    * @param k2 The key of the end node
    * @return The shortest path length */
  public double getShortestPathLength(K k1, K k2){
    // call path find
    dijkstra(k1);
    
    // convert path to array list
    UnDirectedGraphNode<K,E> w = nodeList.get(k2);
    ArrayList<K> al = new ArrayList<K>();
    
    if (w == null){
      throw new NoSuchElementException();
    } else if(w.getDist() == INFINITY){
      return Double.MAX_VALUE;
    } else {
      printPath(w,al);
      
      // return array list
      return w.getDist();
    }
  }
  
  /** This method resets the algorithm-variabels in all the verticies */
  public void clearAll(){
    for (UnDirectedGraphNode n : nodeList.values()){
      n.reset();
    }
  }
  
  /** Finds the shortest path between two nodes
    * @param k1 The key of the start node
    * @param k2 The key of the end node
    * @return The shortest path as an Arraylist */
  public ArrayList<K> findShortestPath(K k1, K k2){
    // call path find
    dijkstra(k1);
    
    // convert path to array list
    UnDirectedGraphNode<K,E> w = nodeList.get(k2);
    ArrayList<K> al = new ArrayList<K>();
    
    if (w == null){
      throw new NoSuchElementException();
    } else if(w.getDist() == INFINITY){
      //System.out.println("Node with key " + k2 + " is unreachable from node " + k1);
      return null;
    } else {
      //System.out.print("(Cost is: " + w.getDist() + ")");
      printPath(w,al);
      //System.out.println();
      
      // return array list
      return al;
    }
  }
  
    // recursively add path to array list
  private void printPath(UnDirectedGraphNode<K,E> dest, ArrayList<K> al){
    if (dest.getPrev() != null){
      printPath(dest.getPrev(),al);
      //System.out.println(" to ");
    } 
    
    // print and add to arrayList
    //System.out.println(dest.getKey());
    al.add(dest.getKey());
  }

  /** Finds the shortest path using the Dijkstra algorithm.
    * @param k1 The starting key */
  public void dijkstra(K k1){
    PriorityQueue<UnDirectedGraphPath<E>> pq = new PriorityQueue<UnDirectedGraphPath<E>>();
    
    // get first node
    UnDirectedGraphNode<K,E> start = nodeList.get(k1);
    if (start == null){
      throw new NoSuchElementException("Starting node not found");
    }
    
    clearAll();
    pq.add(new UnDirectedGraphPath<E>(start,0));
    start.setDist(0);
    
    // loop through the graph and generate paths
    int nodesSeen = 0;
    while(!pq.isEmpty() && nodesSeen < size()){
      // remove from priority queue and get the destination
      UnDirectedGraphPath<E> vrec = pq.remove();
      UnDirectedGraphNode<K,E> v = vrec.getDest();
      if(v.getScratch() != 0) continue;
      v.setScratch(1);
      nodesSeen++;
      
      // loop through edges tied to node v
      for (UnDirectedGraphEdge<E> e : v.getList()){
        UnDirectedGraphNode<K,E> w = e.getDest();
        double cvw = e.getWeight();
        
        // check for negatives
        if (cvw < 0) {
          System.out.println("Graph has negative edges!");
          return;
        }
        
        // find the greater path and add to priority queue
        if (w.getDist() > v.getDist() + cvw){
          w.setDist(v.getDist() + cvw);
          w.setPrev(v);
          pq.add(new UnDirectedGraphPath(w,w.getDist()));
        }
      }
    }
  }
  
  /** This method creates a new path
    * @param n The node the path is attatched to
    * @param w The weight of the path
    * @return The created path */
  public UnDirectedGraphPath<E> addPath(UnDirectedGraphNode<K,E> n, double w){
    return new UnDirectedGraphPath<E>(n,w);
  }
  
  // converts the path into an ArrayList
  private ArrayList<K> pathToArrayList(UnDirectedGraphPath<E> path){
    return null;
  }
  /** Gets the number of vertecies in the graph
    * @Return the vertex count */
  public int size(){
    return nodeList.size();
  }
    
  /** Gets a vertex
    * @param k The key to search for
    * @return The vertex with the given key */
  public UnDirectedGraphNode<K,E> getVertex(K k){
    return nodeList.get(k);
  }
  
  /** Gets the list of nodes 
    * @return The list of nodes */
  public HashMap<K,UnDirectedGraphNode<K,E>> getNodeList(){
    return nodeList;
  }
  
  /** Gets an iterator for the nodes 
    * @return An iterator for the nodes*/
  public Iterator<UnDirectedGraphNode<K,E>> iterator(){
    return nodeList.values().iterator();
  }
  
  /** Sub-class represents the nodes */
  public class UnDirectedGraphNode<K,E>{
    /** Linked list holds edges */
    private LinkedList<UnDirectedGraphEdge<E>> list;
    /** The element of the node */
    private E e;
    /** The name of the node (key) */
    private K k;
    /** The cost of the node while on a path */
    private double dist;
    /** The previous node on a path*/
    private UnDirectedGraphNode<K,E> prev;
    /** Extra variable for path algorithm*/
    private int scratch;
    
    /** The default constructor */
    public UnDirectedGraphNode(){
      System.out.println("You forgot to specify parameters for a node!");
    }
    
    /** Constructor with parameters 
      * @param k The key of the new node
      * @param e The element of the node */
    public UnDirectedGraphNode(E e, K k){
      // instanciate list
      list = new LinkedList<UnDirectedGraphEdge<E>>();
      this.e = e;
      this.k = k;
    }
    
    /** Adds an edge
      * @param edge The edge to add */
    public void addEdge(UnDirectedGraphEdge<E> edge){
      list.add(edge);
    }
    
    public void reset(){
      dist = UnDirectedGraph.INFINITY; prev = null; scratch = 0;
    }
    
    // SETTERS AND GETTERS
    /** Gets the element
      * @return The element of the node */
    public E getElement(){
      return e;
    }
    
    /** Gets the list of edges
      * @return the list of edges */
    public LinkedList<UnDirectedGraphEdge<E>> getList(){
      return list;
    }
    
    /** Gets the dist
      * @return The dist */
    public double getDist(){
      return dist;
    }
    
    /** Sets the dist
      * @param d the new dist value */
    public void setDist(double d){
      dist = d;
    }
    
    /** Gets the prev
      * @return prev */
    public UnDirectedGraphNode<K,E> getPrev(){
      return prev;
    }
    
    /** Sets the prev
      * @param p New prev value */
    public void setPrev(UnDirectedGraphNode<K,E> p){
      prev = p;
    }
    
    /** Gets the scratch
      * @return The scratch */
    public int getScratch(){
      return scratch;
    }
    
    /** Sets the scratch
      * @param s The new scratch */
    public void setScratch(int s){
      scratch = s;
    }
    
    /** Gets the key of the node
      * @return The key of the node */
    public K getKey(){
      return k;
    }
  }
  
  
  /** Sub-class represents the edges */
  public class UnDirectedGraphEdge<E> {
    /** The node this graph is attatched to */
    private UnDirectedGraphNode<K,E> dest;
    /** The weight of the node */
    private double weight;
    
    /** The default constructor */
    public UnDirectedGraphEdge(){
    }
    
    /** Constructor with param
      * @param k The key of the destination node
      * @param w The weight of the edge */
    public UnDirectedGraphEdge(UnDirectedGraphNode<K,E> v, double w){
      dest = v;
      weight = w;
    }
    
    /** Gets the weight
      * @return The weight of the node */
    public double getWeight(){
      return weight;
    }
    
    /** Gets the destination
      * @return The destination node */
    public UnDirectedGraphNode<K,E> getDest(){
      return dest;
    }
  }
  
  /** Sub-class represents a path */
  public class UnDirectedGraphPath<E> implements Comparable<UnDirectedGraphPath<E>> {
    private UnDirectedGraphNode<K,E> dest; // the destination
    private double cost; // weight of the journey to the dest
    
    /** Default constructor */
    public UnDirectedGraphPath(){
      System.out.println("You forgot to specify parameters for a path!");
    }
    
    /** Constructor
      * @param d The destination
      * @param c The cost */
    public UnDirectedGraphPath(UnDirectedGraphNode<K,E> d, double c){
      dest = d;
      cost = c;
    }
    
    /** Compares to a different path 
      * @param rhs The path to compare to
      * @return -1 getCostif this path is of less cost, 0 if they are equal, or 1 if this path is of greater cost */
    public int compareTo(UnDirectedGraphPath<E> rhs){
      double otherCost = rhs.getCost();
      
      return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
    }
    
    /** Gets the cost
      * @return The cost of the path */
    public double getCost(){
      return cost;
    }
    
    /** Gets the destination of the path
      * @return The destination node of the path */
    public UnDirectedGraphNode<K,E> getDest(){
      return dest;
    }
  }
}
