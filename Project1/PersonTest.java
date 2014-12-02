import junit.framework.TestCase;

public class PersonTest extends TestCase {
  private Person instance;
  
  public void setUp(){
    instance = new Person(15);
  }
  
  // test the increment function
  public void testDec(){
    // make sure the wait time starts at 0
    assertTrue(instance.getWaitTime() == 15);
    
    // make sure the wait time is increasing
    for (int i = 0; i < 15; i++){
      instance.decWaitTime();
      assertTrue(instance.getWaitTime() == 14-i);
    }
  }
  
  // test the reset function
  public void testReset() {
    // increment the wait a few times
    for (int i = 0; i < 15; i++){
      instance.decWaitTime();
    }
    
    // make sure the wait time is not 0
    assertTrue(instance.getWaitTime() != 15);
    
    // reset the wait time to 0 and check
    instance.resetWaitTime();
    assertTrue(instance.getWaitTime() == 0);
  }
  
}
