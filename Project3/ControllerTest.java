import junit.framework.TestCase;
import java.io.*;
import java.util.*;

public class ControllerTest extends TestCase {
  
  public void testReadResources() {
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("testResources.txt"));
      
      // write to file
      writer.write("warehouse: 3");
      writer.newLine();
      writer.write("truck: 50 350");
      writer.newLine();
      writer.write("bananas: 2");
      writer.newLine();
      writer.write("beef: 1");
      writer.newLine();
      writer.write("towels: 3");
      writer.newLine();
      writer.write("soda: 1");
      writer.newLine();
      writer.write("vegetables: 2");
      writer.newLine();
      
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the test file.");
      fail();
    }
    // read file
    Controller c = new Controller();
    HashMap<String,Resource> al = c.readResources(new File("testResources.txt"));
    assertTrue("Failed at getting the warehouse count",c.getWarehouseCount()==3);
    assertTrue("Failed at getting the truck count",c.getMaxStorage()==50);
    assertTrue("Failed at getting the maximum milage",c.getMaxMilage()==350);
    
    // test resources
    assertTrue("Failed when reading resources",c.getResourceList().get("bananas").getCost() == 2);
    assertTrue("Failed when reading resources",c.getResourceList().get("beef").getCost() == 1);
    assertTrue("Failed when reading resources",c.getResourceList().get("towels").getCost() == 3);
    assertTrue("Failed when reading resources",c.getResourceList().get("soda").getCost() == 1);
    assertTrue("Failed when reading resources",c.getResourceList().get("vegetables").getCost() == 2);
  }
  
  public void testLocations(){
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("testLocations.txt"));
      
      // write to file
      writer.write("24 (88, 73)");
      writer.newLine();
      writer.write("20 (85, 12)");
      writer.newLine();
      writer.write("21 (45, 91)");
      writer.newLine();
      writer.write("22 (6, 12)");
      writer.newLine();
      writer.write("23 (31, 87)");
      writer.newLine();
      writer.write("1 (16, 77)");
      writer.newLine();
      writer.write("3 (87, 83)");
      writer.newLine();
            
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the test file.");
      fail();
    }
    // read file
    Controller c = new Controller();
    UnDirectedGraph<Integer,Location> g = new UnDirectedGraph<Integer,Location>();
    c.readTowns(g,new File("testLocations.txt"));
    assertTrue("Failed at getting the size of the location graph",g.size() == 7);
    assertTrue("Failed at getting the 20th node",g.getVertex(20).getElement().getName() == 20);
    assertTrue("Failed at getting the 3rd node",g.getVertex(3).getElement().getName() == 3);
  }
  
  public void testReadSpecification(){
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("testSpecification.txt"));
      
      // write to file
      writer.write("warehouse 24");
      writer.newLine();
      writer.write("store 20");
      writer.newLine();
      writer.write("store 21");
      writer.newLine();
      writer.write("warehouse 22");
      writer.newLine();
      writer.write("warehouse 23");
      writer.newLine();
      writer.write("warehouse 1");
      writer.newLine();
      writer.write("store 3");
      writer.newLine();
      
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the test file.");
      fail();
    }
    // read file
    Controller c = new Controller();
    UnDirectedGraph<Integer,Location> g = new UnDirectedGraph<Integer,Location>();
    c.readTowns(g,new File("testLocations.txt"));
    c.readSpecification(g,new File("testSpecification.txt"));
    assertTrue(g.getVertex(24).getElement().getSpec() == Location.Type.WAREHOUSE);
    assertTrue(g.getVertex(20).getElement().getSpec() == Location.Type.STORE);
    assertTrue(g.getVertex(21).getElement().getSpec() == Location.Type.STORE);
    assertTrue(g.getVertex(22).getElement().getSpec() == Location.Type.WAREHOUSE);
    assertTrue(g.getVertex(23).getElement().getSpec() == Location.Type.WAREHOUSE);
    assertTrue(g.getVertex(1).getElement().getSpec() == Location.Type.WAREHOUSE);
    assertTrue(g.getVertex(3).getElement().getSpec() == Location.Type.STORE);
  }
  
  public void testReadNeeds(){
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("testNeeds.txt"));
      
      // write to file
      writer.write("22 vegetables 7");
      writer.newLine();
      writer.write("22 beef 2");
      writer.newLine();
      writer.write("1 vegetables 8");
      writer.newLine();
      writer.write("1 beef 3");
      writer.newLine();
      writer.write("1 towels 10");
      writer.newLine();
      writer.write("21 vegetables 5");
      writer.newLine();
      
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the test file.");
      fail();
    }
    // read file
    Controller c = new Controller();
    UnDirectedGraph<Integer,Location> g = new UnDirectedGraph<Integer,Location>();
    c.readResources(new File("testResources.txt"));
    c.readTowns(g,new File("testLocations.txt"));
    c.readSpecification(g,new File("testSpecification.txt"));
    c.readNeeds(g,new File("testNeeds.txt"));
    assertTrue(g.getVertex(22).getElement().getSupplyList().getNode("vegetables").getMax() == 7);
    assertTrue(g.getVertex(22).getElement().getSupplyList().getNode("beef").getMax() == 2);
    assertTrue(g.getVertex(1).getElement().getSupplyList().getNode("vegetables").getMax() == 8);
    assertTrue(g.getVertex(1).getElement().getSupplyList().getNode("beef").getMax() == 3);
    assertTrue(g.getVertex(1).getElement().getSupplyList().getNode("towels").getMax() == 10);
    assertTrue(g.getVertex(21).getElement().getSupplyList().getNode("vegetables").getMax() == 5);
  }
  
  public void testReadConnectivity(){
    // create file
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter("testConnectivity.txt"));
      
      // write to file
      writer.write("20 - 24 5");
      writer.newLine();
      writer.write("1 - 21 8");
      writer.newLine();
      writer.write("22 - 24 10");
      writer.newLine();
      
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not write the test file.");
      fail();
    }
    // read file
    Controller c = new Controller();
    UnDirectedGraph<Integer,Location> g = new UnDirectedGraph<Integer,Location>();
    c.readResources(new File("testResources.txt"));
    c.readTowns(g,new File("testLocations.txt"));
    c.readConnectivity(g,new File("testConnectivity.txt"));
    c.readNeeds(g,new File("testNeeds.txt"));
    assertTrue(g.getVertex(20).getList().peek().getDest().getKey() == 24);
    assertTrue(g.getVertex(1).getList().peek().getDest().getKey() == 21);
    assertTrue(g.getVertex(21).getList().peek().getDest().getKey() == 1);
    assertTrue(g.getVertex(20).getList().peek().getWeight() == 5);
    assertTrue(g.getVertex(1).getList().peek().getWeight() == 8);
  }
  
  // uses previously created files to test the program's ability to create a graph
  public void testReadNetwork(){
    Controller c = new Controller("testResources.txt", "testLocations.txt", "testConnectivity.txt", "testSpecification.txt", "testNeeds.txt");
    
    // run assertions from previous tests
    assertTrue("Failed when reading resources",c.getResourceList().get("bananas").getCost() == 2);
    assertTrue("Failed when reading resources",c.getResourceList().get("beef").getCost() == 1);
    assertTrue("Failed at getting the 20th node",c.getNetwork().getVertex(20).getElement().getName() == 20);
    assertTrue(c.getNetwork().getVertex(24).getElement().getSpec() == Location.Type.WAREHOUSE);
    assertTrue(c.getNetwork().getVertex(20).getElement().getSpec() == Location.Type.STORE);
    assertTrue(c.getNetwork().getVertex(22).getElement().getSupplyList().getNode("vegetables").getMax() == 7);
    assertTrue(c.getNetwork().getVertex(22).getElement().getSupplyList().getNode("beef").getMax() == 2);
    assertTrue(c.getNetwork().getVertex(21).getElement().getSupplyList().getNode("vegetables").getMax() == 5);
    assertTrue(c.getNetwork().getVertex(1).getList().peek().getDest().getKey() == 21);
    assertTrue(c.getNetwork().getVertex(21).getList().peek().getDest().getKey() == 1);
    assertTrue(c.getNetwork().getVertex(20).getList().peek().getWeight() == 5);
    assertTrue(c.getNetwork().getVertex(1).getList().peek().getWeight() == 8);
  }
}
