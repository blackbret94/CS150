import junit.framework.TestCase;
import java.util.*;

public class RankComparatorTest extends TestCase {
  private Listing l1;
  private Listing l2;
  private RankComparator<Listing> comparator;
  
  public void setUp(){
    comparator = new RankComparator<Listing>();
  }
  
  public void testCompare() {
    // compare first listings (check rank)
    l1 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 30);
    l2 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 3.0f, 30);
    
    assertTrue(comparator.compare(l1,l2) > 0);
    
    l2 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 5.0f, 30);
    
    assertTrue(comparator.compare(l1,l2) < 0);
    
    // compare second listings (check reviewer count)
    l1 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 300);
    l2 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 400);
    
    assertTrue(comparator.compare(l1,l2) < 0);
    
    l2 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 200);
    
    assertTrue(comparator.compare(l1,l2) > 0);
    
    // compare third listings (check name)
    l2 = new Listing("Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 30);
    l1 = new Listing("Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 30);
    
    assertTrue(comparator.compare(l1,l2) > 0);
    
    l1 = new Listing("A Big Pizza Place","New York", new Schedule(), new ArrayList<String>(),"$$", 4.0f, 30);
    
    assertTrue(comparator.compare(l1,l2) < 0);
  }
  
}