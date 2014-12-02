import junit.framework.TestCase;

public class StaircaseTest extends TestCase {
  private Staircase staircase;
  
  // make sure all stairs are being added
  public void testAddStair() {
    for (int i = 1; i <= 10; i++){
      // create stair case
      staircase = new Staircase();
      
      for (int j = 0; j < i*50; j++){
        // add stairs
        staircase.addStair(new Stairs(5));
      }
      
      // check size
      assertTrue(staircase.size() == i*50);
    }
  }
  
  // make sure a person is added
  public void testAddPerson(){
    for (int i = 5; i < 15; i++){
      // create stair
      staircase = new Staircase(i*10,15);
      
      // add person to top floor
      staircase.addToStair(new Person(),staircase.size()-1);
      
      // check
      assertTrue(staircase.getStair(staircase.size()-1).getSize()==1);
      
      // add person to middle floor
      staircase.addToStair(new Person(),staircase.size()/2);
      
      // check    
      assertTrue(staircase.getStair(staircase.size()/2).getSize()==1);
    }
  }
  
}
