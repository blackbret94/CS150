import junit.framework.TestCase;
import java.util.*;
import java.io.*;

public class SimulationTest extends TestCase {
  /** 
   * THIS CLASS DID NOT REQUIRE FULL TESTING
   * Most of the methods in this class were simply creating instances and calling
   * methods of other classes.  These methods had already been tested in their respective
   * classes.  I decided to test the program's ability to read a text file,
   * as an error there could cause some serious problems and this has not been
   * tested elsewhere in the program.
   * 
   /**
   * Tests the program's ability to properly read a textfile
   */
  public void testReadText() {
    try {
    // create file
    File outputFile = new File("testFile.txt");
    
    // create a buffered writer to write to the file
    BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
    
    // add string and double
    out.write("2");
    out.newLine();
    out.write("2");
    out.newLine();
    out.write("15");
    out.newLine();
    out.write("20.0 7.0");
    out.newLine();
    out.write("1 15");
    
    // close the file
    out.close();
    
    // read file
    Simulation testSim = new Simulation(0);
    testSim.readText("testFile.txt");
    
    // compare with expected results
    assertTrue(2 == testSim.getFloors());
    assertTrue(2 == testSim.getStairs());
    assertTrue(15 == testSim.getStairsCap());
    assertTrue(20 == testSim.getTenantsMean());
    assertTrue(7 == testSim.getTenantsVar());
    assertTrue(testSim.getShouldStop());
    assertTrue(15 == testSim.getMaxSteps());
    
    } catch(java.io.IOException e){
    }
  }
}
