import junit.framework.TestCase;
import java.util.*;
import java.io.*;

public class RouteFinderTest extends TestCase {
  private Controller c;
  private RouteFinder r;
  
  public void setUp(){
    c = new Controller("testResources.txt", "testLocations.txt", "testConnectivity.txt", "testSpecification.txt", "testNeeds.txt");
    r = new RouteFinder(c);
  }
  
  public void testGetStores() {
        
    // get stores
    LinkedList<Location> l = r.getStores();
    
    // iterate through list, summing keys
    Iterator<Location> it = l.iterator();
    int cnt = 0;
    while(it.hasNext()){
      cnt += it.next().getName();
    }
    
    //test
    assertTrue(cnt == 20+21+3);
  }
  
  public void testGetWarehouses() {
    // get stores
    LinkedList<Location> l = r.getWarehouses();
    
    // iterate through list, summing keys
    Iterator<Location> it = l.iterator();
    int cnt = 0;
    while(it.hasNext()){
      cnt += it.next().getName();
    }
    
    //test
    assertTrue(cnt == 24+22+23+1);
  }
  
  public void testToQueue(){
    // get list
    LinkedList<Location> l = r.getWarehouses();
    
    // copy to queue
    LineQueueList<Location> queue = r.toQueue(l);
    
    // compare
    assertTrue("Failed checking size",l.size() == queue.size());
    
    Iterator<Location> it1 = l.iterator();
    Iterator<Location> it2 = queue.iterator();

    while (it1.hasNext() && it2.hasNext()){
      assertTrue("Failed comparing keys",it1.next().getName() == it2.next().getName());
    }
  }
  
  public void testCreateTrucks(){
    // call method
    r.createTrucks(r.getWarehouses());
    
    // check that size of trucks is equal to the number of trucks per warehouse * the number of warehouses
    assertTrue(r.getRoute().size() == c.getWarehouseCount() * r.getWarehouses().size());
  }
  
  public void testGetNearestStore(){
    // test for a simple case
    assertTrue(r.getNearestStore(c.getNetwork().getVertex(24).getElement(),r.getStores()).getName() == 20);
    
    // add edges and try again
    c.getNetwork().addEdge(24,21,8);
    c.getNetwork().addEdge(24,3,2);
    assertTrue(r.getNearestStore(c.getNetwork().getVertex(24).getElement(),r.getStores()).getName() == 3);
  }
  
  public void testCheckTruck(){
    // create truck and place on graph
    Truck t = new Truck(10, 10, 15, c.getNetwork().getVertex(21).getElement());
    
    c.getNetwork().addEdge(22,24,4);
    c.getNetwork().addEdge(24,21,8);
    c.getNetwork().addEdge(24,3,2);
    
    // check node that will work
    assertTrue(r.checkTruck(t,c.getNetwork().getVertex(24).getElement(),2));
    
    // check node that will fail due to distance
    assertTrue(!r.checkTruck(t,c.getNetwork().getVertex(24).getElement(),18));
    
    // check node that will fail due to storage
    t = new Truck(10, 2, 15, c.getNetwork().getVertex(21).getElement());
    assertTrue(!r.checkTruck(t,c.getNetwork().getVertex(22).getElement(),3));
  }
  
  public void testVisitStore(){
    // create truck
    Truck t = new Truck(10, 10, 15, c.getNetwork().getVertex(21).getElement());
    assertTrue(t.getCurrentStorage() == 0);
    LinkedList<Location> storeList = r.getStores();
    
    // visit node 
    r.visitStore(t,c.getNetwork().getVertex(22).getElement(),5,storeList);
    
    // check changes
    assertTrue("Failed to remove the store",!storeList.contains(c.getNetwork().getVertex(22).getElement()));
    assertTrue("Failed to correctly modify the storage",t.getCurrentStorage() == 2*1 +7*2);
    assertTrue("Failed to change milage",t.getMilage() == 5);
    assertTrue("Failed to add the store to the truck's list",t.getLocList().contains(c.getNetwork().getVertex(22).getElement()));
  }
}
