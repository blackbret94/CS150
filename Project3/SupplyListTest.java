import junit.framework.TestCase;

public class SupplyListTest extends TestCase {
  public void testAddResource() {
    // create supply list
    SupplyList sl = new SupplyList();
    
    // add resource
    sl.addResource(new Resource("Orc",25),10);
    
    // check resource
    assertTrue(sl.getNode("Orc").getMax()==10);
    assertTrue(sl.getNode("Orc").getCurrent()==0);
    
    // try null
    assertTrue(sl.getNode("Elf") == null);
  }
  
  public void testAdjustValue() {
    // create supply list
    SupplyList sl = new SupplyList();
    
    // add resource
    sl.addResource(new Resource("Orc",25),10);
    assertTrue(sl.getNode("Orc").getCurrent()==0);
    
    // adjust value
    sl.adjustValue("Orc",7);
    
    // check resource
    assertTrue(sl.getNode("Orc").getCurrent()==7);
  }
  
  public void testGetSpace(){
    // add some resources
    Resource r1 = new Resource("Elf", 3);
    Resource r2 = new Resource("Orc", 2);
    Resource r3 = new Resource("Drake",5);
    
    // create supply list and fill
    SupplyList s = new SupplyList();
    s.addResourceSpace(r1,10);
    s.addResourceSpace(r2,15);
    s.addResourceSpace(r3,1);
    
    // get space
    assertTrue(s.getSpace() == 10*3+15*2+1*5);
    
    // create supply list and fill
    s = new SupplyList();
    s.addResourceSpace(r1,5);
    s.addResourceSpace(r3,1);
    
    // get space
    assertTrue(s.getSpace() == 5*3+1*5);
    
    // create supply list and fill
    s = new SupplyList();
    s.addResourceSpace(r1,15);
    s.addResourceSpace(r2,20);
    s.addResourceSpace(r3,10);
    
    // get space
    assertTrue(s.getSpace() == 15*3+20*2+10*5);
  }
  
  public void testCombine(){
    // create resources
    Resource r1 = new Resource("Elf", 3);
    Resource r2 = new Resource("Orc", 2);
    Resource r3 = new Resource("Drake",5);
    Resource r4 = new Resource("Dragon",15);
    
    // create first supply list
    SupplyList s1 = new SupplyList();
    s1.addResourceSpace(r1,10);
    s1.addResourceSpace(r2,15);
    s1.addResourceSpace(r3,1);
    
    // create second supply list
    SupplyList s2 = new SupplyList();
    s2.addResourceSpace(r2,10);
    s2.addResourceSpace(r3,15);
    s2.addResourceSpace(r4,1);
    
    // combine
    s1.combine(s2);
    
    // check
    assertTrue(s1.getSpace() == 10*3+25*2+16*5+1*15);
  }
}
