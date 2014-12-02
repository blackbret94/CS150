/** This class handles console commands
  * @author Bret Black */

import java.util.*;
import java.io.*;

public class Console{
  /** Container for data */
  private Data data;
  /** Yelp instance */
  private MiniYelp miniYelp;
  /** Input scanner */
  private Scanner input;
  /** The file the modified database is written to */
  private File outFile;
  /** Determines when the program exits */
  private boolean exit;
  /** Determines if time values will be printed */
  private boolean time;
  
  /** The default constructor */
  public Console(){
    setup();
  }
  
  /** Constructor with parameters
    * @param miniYelp the miniYelp instance to use
    * @param data The Data instance to use
    * @param inFile the name of the input file
    * @param outFile the name of the output file
    */
  public Console(MiniYelp miniYelp, Data data, String inFile, String outFile){
    this.data = data;
    this.miniYelp = miniYelp;
    this.outFile = new File(outFile);
    
    setup();
  }
  
  // setup
  private void setup(){
    // wait for input
    input = new Scanner(System.in);
    time = false;
    System.out.println("Type 'help' for a list of commands");
    
    exit = false;
    while (!exit){
      respondToInput();
    }
    
    // close
    input.close();
  }
  
  /** Responds to the user's input */
  public void respondToInput(){
    // read string
    String s = input.nextLine();
    
    // check for exit
    if (s.equals("quit")){
      // save new database
      data.writeToFile(outFile);
      
      // exit
      exit = true;
      return;
    } else if (s.equals("new review")){
      // start clock
      long startTime = System.nanoTime();
      
      // add review
      addReviewInput();
      
      // stop clock
      long stopTime = System.nanoTime();
      
      // send to file if time is true
      if (time){
        outputResults(stopTime, startTime);
      }
    } else if(s.equals("search")){
      // search
      // start clock
      long startTime = System.nanoTime();
      
      // run program
      searchDatabase();
      
      // stop clock
      long stopTime = System.nanoTime();
      
      // send to file if time is true
      if (time){
        outputResults(stopTime, startTime);
      }
    } else if(s.equals("size")){
      // return database size
      System.out.println(data.getDatabase().size());
      
    } else if(s.equals("new listing")){
      // add restaurant
      addListing();
      System.out.println("A new listing has been added!");
    } else if (s.equals("time on")){
      // enable time prints
      time = true;
      System.out.println("Time measurements are being displayed!");
    } else if (s.equals("time off")){
      // disable time prints
      time = false;
      System.out.println("Time measurements are not being displayed!");
    } else if (s.equals("help")){
      // list commands
      System.out.println("search - search the database");
      System.out.println("help - lists all commands"); 
      System.out.println("new review - add a review");
      System.out.println("new listing - add a restaurant");
      System.out.println("size - gets the size of the database");
      System.out.println("time on - turns on time measurements");
      System.out.println("time off - turns off time measurements");
      System.out.println("quit - exit the program");
      System.out.println("Note: This database includes listings in Philadelphia, New York, Chicago, San Francisco, and Boston.");
    }
  }
  
  // queries for input and adds a listing
  private void addListing(){
    // name
    System.out.println("Name of the listing?");
    String name = input.nextLine();
    // city
    System.out.println("What city?");
    String city = input.nextLine();
    // category
    System.out.println("What categories?  Seperate with commas, no spaces in between.");
    String category = input.nextLine();
    // cost
    System.out.println("Cost?  Please use $, $$, or $$$.");
    String cost = input.nextLine();
    // schedule
    System.out.println("What is the schedule?  List the day, open time, and close time.  Seperate each day with a comma - no space.");
    System.out.println("Example: 'Monday 06:00 18:30,Thursday 07:00 18:30'"); 
    String schedule = input.nextLine();
    
    // add
    data.addListing(name,city,splitSchedule(schedule),splitCategories(category),cost);
  }
  
  // split up a string of categories into an array
  private ArrayList<String> splitCategories(String s){
    String[] array = s.split(",");
    ArrayList<String> catArray = new ArrayList<String>();
    
    // add elements
    for (int i = 0; i < array.length; i++){
      catArray.add(array[i]);
    }
    
    return catArray;
  }
  // split up a string schedule into a Schedule instance
  private Schedule splitSchedule(String s){
    // split string into array
    String[] dayArray = s.split(",");
    
    // create schedule
    Schedule schedule = new Schedule();
    
    // add days to schedule
    for (int j = 0; j<dayArray.length;j++){
      // split element into day, open, and close
      String[] timeArray = dayArray[j].split(" ");
      
      // add day to schedule
      schedule.setDay(timeArray[0],Schedule.stringToTime(timeArray[1]),Schedule.stringToTime(timeArray[2]));
    }
    
    return schedule;
  }
  
  // searches the database
  private void searchDatabase(){
    System.out.println("City?");
    String city = input.nextLine();
    System.out.println("Category?");
    String category = input.nextLine();
    System.out.println("Cost?");
    String cost = input.nextLine();
    System.out.println("Day?");
    String day = input.nextLine();
    System.out.println("Time (00:00 - 23:59)?");
    String time = input.nextLine();
    System.out.println("Searching...");
    
    // split categories
    ArrayList<String> catArray = splitCategories(category);
    
    // build database
    data.buildSearchDatabase(city, catArray, cost, day, time);
    
    System.out.println("Search complete!");
    if (data.getSortedDatabase().size() != 0){
      System.out.println("We have found " + data.getSortedDatabase().size() + " related listings!");
      
      // now list the results
      Iterator<Listing> sortedIterator = data.getSortedDatabase().iterator();
      
      // iterate through new database and print listings
      while (sortedIterator.hasNext()){
        sortedIterator.next().toSystem();
        System.out.println();
      }
    } else {
      System.out.println("Your search did not return any listings.  Check your spelling or try something else.");
    }
  }
  
  // gets input for a review
  private void addReviewInput(){
    // get name
    System.out.println("Name?");
    String name = input.nextLine();
    
    // get city
    System.out.println("City?");
    String city = input.nextLine();
    
    // get score
    System.out.println("Score? (0-5)");
    int score = Integer.parseInt(input.nextLine());
    
    // add review
    float newRank = addReview(name, city, score);
    if (newRank > 0){
      System.out.println(name + " now has an average raiting of " + newRank);
    }
  }
  
  /** Adds a review to a restaraunt
    * @return the new rank */
  public float addReview(String name, String city, int score){
    // iterate through database
    Iterator<Listing> iterator = data.getDatabase().iterator();
    
    // count duplicates
    int count = 0;
    // save the last updated review
    float lastUpdate = 0.0f;
    
    while(iterator.hasNext()){
      Listing nextListing = iterator.next();
      
      // check if the city matches
      if (nextListing.getCity().equals(city)){
        // check if the name matches
        if (nextListing.getName().equals(name)) {
          // update review and return
          count++;
          lastUpdate = nextListing.addReview(score);
        }
      }
    }
    
    if (count > 0){
      System.out.println("There were " + count + " listings found under this name!");
      return lastUpdate;
    } else {
      // announce that nothing was found
      System.out.println("No listings were found!  Please check your spelling and try again.");
      return 0;
    }
  }
  
  /** Calculates the change in time
    * @param stopTime the end of the program
    * @param startTime the start of the program
    * @return The difference in time */
  public long calculateDeltaTime(long stopTime, long startTime){
    return stopTime - startTime;
  }
  
  /** This method sends the time to the console 
    * @param stopTime the end of the program
    * @param startTime the start of the program
    */
  public void outputResults(long stopTime, long startTime){
    // print difference
    System.out.println(calculateDeltaTime(stopTime,startTime));
  }
}