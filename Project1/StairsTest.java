import junit.framework.TestCase;

public class StairsTest extends TestCase {
  /** Instance of the stair */
  private Stairs stair;
  
  public void setUp(){
    
  }
  
  // test the clock
  public void testClock() {
    for (int i=1; i<=5;i++){
      // create stair
      stair = new Stairs(i*15);
      
      // add person
      stair.addPerson(new Person(stair.getMax()));
      
      // run clock
      for (int j=0; j<i*15-1; j++){
        stair.clock();
        assertTrue(stair.removePerson() == null);
      }
      
      // run again and check remove
      stair.clock();
      assertTrue(stair.removePerson() != null);
    }
  }
  
  // test if a person will be added until the queue is full
  public void testAddPerson(){
    for (int i=1; i<=5; i++){
      // create stair with a longer travel time than will be reached
      stair = new Stairs(i*4);
      
      // test that persons are added
      for (int j=0; j < i*4; j++){
        assertTrue(stair.addPerson(new Person()));
      }
      
      // test that persons are not added when the list is full
      assertTrue(!stair.addPerson(new Person()));
    }
  }
  
  // test that a person is removed when it is their time  
  public void testRemovePerson(){
    for (int i=1; i<=5; i++){
      // create stair with a max that will be reached
      stair = new Stairs(i*4);
      
      // test that persons are not removed before their time
      for (int j=0; j < i*4-1; j++){
        stair.addPerson(new Person(i*4));
        stair.clock();
        assertTrue(stair.removePerson() == null);
      }
      
      // test that a person is removed when it is their time
      stair.clock();
      assertTrue(stair.removePerson() != null);
    }
  }
  
  // ensure that the program runs correctly with every step
  public void testOneTimeStep(){
    for (int i=1; i<=5; i++){
      // create stair with a max that will be reached
      stair = new Stairs(i*4);
      
      // test that persons are not removed before their time
      for (int j=0; j < i*4-1; j++){
        stair.addPerson(new Person(i*4));
        assertTrue(stair.oneTimeStep() == null);
      }
      
      // test that a person is removed when it is their time
      stair.addPerson(new Person(i*4));
      assertTrue(stair.oneTimeStep() != null);
    }
  }
}
