import junit.framework.TestCase;
import java.io.*;
import java.util.*;

public class MiniYelpTest extends TestCase {
  
  // create a file with two listings, import, and make sure they match
  public void testReadFile() {
    // create file
    File file = new File("newFile.txt");
    try {
      // instanciate
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      
      // add listing 1
      writer.write("name: The Lyre of Orpheus");
      writer.newLine();
      writer.write("city: San Francisco");
      writer.newLine();
      writer.write("category: Greek,Breakfast & Brunch,Seafood,Salad,Soup");
      writer.newLine();
      writer.write("open: Sunday 8:00 16:00,Monday 6:00 20:00,Tuesday 6:00 20:00,Wednesday 6:00 20:00,Thursday 6:00 20:00,Friday 6:00 20:00,Saturday 6:00 01:00");
      writer.newLine();
      writer.write("cost: $$");
      writer.newLine();
      writer.write("rank: 0");
      writer.newLine();
      writer.write("reviewers: 0");
      writer.newLine();
      writer.newLine();
      
      // add listing 2
      writer.write("name: Renan's Turkish Pizzeria");
      writer.newLine();
      writer.write("city: San Francisco");
      writer.newLine();
      writer.write("category: Turkish,Soup,Salad,Pizza");
      writer.newLine();
      writer.write("open: Sunday 10:00 16:00,Monday 7:00 20:00,Tuesday 7:00 20:00,Wednesday 7:00 20:00,Thursday 7:00 20:00,Friday 7:00 20:00,Saturday 7:00 20:00");
      writer.newLine();
      writer.write("cost: $$$");
      writer.newLine();
      writer.write("rank: 0");
      writer.newLine();
      writer.write("reviewers: 0");
      writer.newLine();
      
      // close the file
      writer.close();
      
      // create instance of MiniYelp
      MiniYelp instance = new MiniYelp(false);
      
      // import file
      instance.readFile(file);
      
      // build search database
      ArrayList<String> catArray = new ArrayList<String>();
      catArray.add("Turkish");
      instance.getData().buildSearchDatabase("San Francisco",catArray,"$$$","Monday","08:00");
      
      // check size of databases
      assertTrue(instance.getData().getDatabase().size() == 2);
      assertTrue(instance.getData().getCityDatabase().size() == instance.getData().getSortedDatabase().size());
      
    } catch (java.io.IOException e){
      System.out.println("FILE NOT CREATED");
      fail();
    }
  }
}
