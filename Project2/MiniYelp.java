/** This class contains the console commands and any containers for the data
  * @author Bret Black
  */
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class MiniYelp {
  // instance variables
  /** Data container */
  private Data data;
  /** Console for commands */
  private Console console;
  
  public static void main(String[] args){
    if (args.length != 2){
      new MiniYelp();
    } else new MiniYelp(args[0],args[1]);
  }
  
  /** Default constructor */
  public MiniYelp(){
    // instanciate data
    data = new Data();
    
    // warn user that no param have been specified
    System.out.println("WARNING: No files have been specified.  The program will read from restaurants.txt and write to output.txt");
    
    // fill database
    System.out.println("The selected database consists of " + readFile(new File("restaurants.txt")) + " listings.");
    
    // instanciate console
    console = new Console(this,data,"restaurants.txt","output.txt");
  }
  
  /** Constructor taking params from the console
    * @param inFile The database to read
    * @param outFile the database to write to
    */
  public MiniYelp(String inFile,String outFile){
    // instanciate data
    data = new Data();
    
    // fill database
    System.out.println("The selected database consists of " +  readFile(new File(inFile)) + " listings.");
    
    // instanciate console
    console = new Console(this,data,inFile,outFile);
    
  }
  
  /** Constructor without setup
    * @param b indicates that the program should not run automatic setup.  No console will be instanciated */
  public MiniYelp(boolean b){
    data = new Data();
    //console = new Console(this,data,"abridged.txt","output.txt");
  }
  
  /** Read file 
    * @param input The file to read
    * @return The number of listings added
    */
  public int readFile(File f){
    try {
      // scanner
      Scanner inScan = new Scanner(f);
      
      // count additions
      int count = 0;
      
      // this pattern splits input strings
      Pattern stringPattern = Pattern.compile("(\\w*)(\\: )(.*)");
      
      while (inScan.hasNext()){
        
        String name = new String("<NAME>");
        String city = new String("<CITY>");
        String cost = new String("<COST>");
        ArrayList<String> categories = new ArrayList<String>();
        Schedule schedule = new Schedule();
        float rank = 0;
        int revCount = 0;
        
        // loop 7 times
        for (int i = 0; i < 7; i++){
          // get string and match
          inScan.findInLine(stringPattern);
          MatchResult matchString = inScan.match();
          
          String title = matchString.group(1);
          
          // Read text in
          if (title.equals("name")){
            // name
            name = matchString.group(3);
          } else if (title.equals("city")){
            // city
            city = matchString.group(3);
          } else if (title.equals("cost")){
            // cost
            cost = matchString.group(3);
          } else if (title.equals("category")){
            // categories
            // split string into array
            String[] catArray = matchString.group(3).split(",");
            
            // create array list
            ArrayList<String> catList = new ArrayList<String>();
            
            // add elements to list
            for (int j = 0; j<catArray.length;j++){
              catList.add(catArray[j]);
            }
            
            categories = catList;
          } else if (title.equals("open")){
            // schedule
            // split string into array
            String[] dayArray = matchString.group(3).split(",");
            
            // create schedule
            schedule = new Schedule();
            
            // add days to schedule
            for (int j = 0; j<dayArray.length;j++){
              // split element into day, open, and close
              String[] timeArray = dayArray[j].split(" ");
              
              // add day to schedule
              schedule.setDay(timeArray[0],Schedule.stringToTime(timeArray[1]),Schedule.stringToTime(timeArray[2]));
            }
          } else if (title.equals("rank")){
            // rank
            rank = Float.parseFloat(matchString.group(3));
          } else if (title.equals("reviewers")){
            // reviewers
            revCount = Integer.parseInt(matchString.group(3));
          }
          
          // skip a line
          inScan.nextLine();
        }
        
        // create new listing and add to database
        Listing newListing = new Listing(name, city, schedule, categories, cost, rank, revCount);
        data.getDatabase().add(newListing);
        
        // increment counter
        count++;
        
        // go to next line, if statement prevents the program from crashing when there is no blank
        // line at the end of the txt file
        if (inScan.hasNext()){
          inScan.nextLine();
        }
      }
      
      // return count
      return count;
    } catch (java.io.FileNotFoundException e){
      System.out.println("File not found!");
      return 0;
    }
  }
  
  // setters and getters
  /** Gets the data container
    * @return The data container */
  public Data getData(){
    return data;
  }
}