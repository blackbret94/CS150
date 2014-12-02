import junit.framework.TestCase;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
public class ListingTest extends TestCase {

  public void testAddReview() {
    // create instance
    Listing l = new Listing();
    
    // check review count and rank
    assertTrue(l.getRank() == 0.0);
    assertTrue(l.getRevCount() == 0);
    
    // add review
    l.addReview(4);
    
    // check
    assertTrue(l.getRank() == 4);
    
    // add and check
    l.addReview(2);
    assertTrue(l.getRank() == 3);
    l.addReview(3);
    assertTrue(l.getRank() == 3);
  }
  
}
