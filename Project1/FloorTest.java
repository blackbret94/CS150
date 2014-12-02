import junit.framework.TestCase;

public class FloorTest extends TestCase {
  // instance vars
  private Floor instance;
  
  // test filling
  public void testFillFloor() {
    for (int i = 1; i <= 10; i++){
      // create and fill
      instance = new Floor(0,0, false);
      instance.fillFloor(20*i, false);
      
      // test size
      assertTrue(instance.getUnorderedPersons().size() == i*20);
    }
  }
  
  // test that less tenants are calculated for the third floor
  public void testThirdFloor(){
    // create normal floor
    Floor firstFloor = new Floor(50,0,false);
    // create a third floor
    Floor thirdFloor = new Floor(50,0,true);
    
    // check
    assertTrue(firstFloor.getUnorderedPersons().size() > thirdFloor.getUnorderedPersons().size());
  }
  
  // test tenant calculation
  public void testTenantCalculation(){
    for (int i = 1; i <= 10; i++){
      int sum = 0;
      instance = new Floor(0,0,false);
      
      // calculate
      for (int j = 0; j < 10; j++){
        int tenantCount = instance.calculateTenants(i*20,i*5);
        System.out.println("Mean: " + i*20 + " Var: " + i*5);
        sum += tenantCount;
      }
      
      // make sure the average is within a reasonable range
      assertTrue(i*20-i*5 <= (sum/10) && (sum/10) <= i*20+i*5);
    }
  }
}
