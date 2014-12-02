import junit.framework.TestCase;

public class ResourceTest extends TestCase {
  
  public void testCompareTo() {
    // create three resources
    Resource r1 = new Resource("Goblin",8);
    Resource r2 = new Resource("Goblin",8);
    Resource r3 = new Resource("Elf",8);
    
    // compare
    assertTrue(r1.compareTo(r2) == 0);
    assertTrue(r1.compareTo(r3) > 0);
    assertTrue(r3.compareTo(r1) < 0);
  }
  
}
