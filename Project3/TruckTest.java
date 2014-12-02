import junit.framework.TestCase;

public class TruckTest extends TestCase {
  
  // tests that the truck stops when it has run out of gas
  public void testStop() {
    //  create truck
    Truck t = new Truck(1,10,10.0f, null);
    
    // check that is has not stopped
    assertTrue(!t.getStop());
    
    // add gas without exceeding max
    assertTrue(t.setMilage(8.0f));
    
    // exceed max
    assertTrue(!t.setMilage(t.getMilage()+8.0f));
  }
  
}
