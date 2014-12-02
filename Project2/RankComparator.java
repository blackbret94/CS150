/**Compares listings by rank
 * @author Bret Black
 */

import java.util.*;

public class RankComparator<I> implements Comparator<I> {  
  // -1 if o1 < o2, 0 if equal, 1 if o1 > o2 
  public int compare(I o1, I o2){
    // ensure that the values recieved are listings
    Listing l1 = (Listing)o1;
    Listing l2 = (Listing)o2;
    
    // check rank
    if (l1.getRank() != l2.getRank()){
      // if not equal use compareTo()
      return (int)((l1.getRank()-l2.getRank())*10);
    }
    
    // check reviewer count
    if (l1.getRevCount() != l2.getRevCount()){
      // if not equal use compareTo()
      return l1.getRevCount()-l2.getRevCount();
    }
    // check alphabetically
    return l1.getName().compareToIgnoreCase(l2.getName());
  }
  
  // true if they are equal
  public boolean equals(Object obj){
    return false;
  }
}