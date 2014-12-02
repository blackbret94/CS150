/** This class contains the console commands and any containers for the data
  * @author Bret Black
  */
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class MiniYelp {
  // instance variables
  /** Input scanner */
  private Scanner input;
  /** Determines when the program exits */
  private boolean exit;
  /** This HashSet stores all the restaurants */
  private HashSet<Listing> database;
  /** This HashSet only stores restaurants that meet the search criteria.  Order is ignored */
  private HashSet<Listing> databaseCity;
  /** This structure contains the results as sorted by rank, reviewers, and then alphabetically */
  private TreeSet<Listing> databaseSorted;
  
  public static void main(String[] args){
    new MiniYelp();
  }
  
  /** Default constructor */
  public MiniYelp(){
    // add database
    database = new HashSet<Listing>();
    
    // fill database
    System.out.println(readFile(new File("abridged.txt")));
    
    // wait for input
    input = new Scanner(System.in);
    System.out.println("Type 'help' for a list of commands");
    
    while (!exit){
      respondToInput();
    }
    
    // close
    input.close();
  }
  
  /** Constructor without setup
    * @param b indicates that the program should not run automatic setup */
  public MiniYelp(boolean b){
    // add database
    database = new HashSet<Listing>();
  }
  
  /** Responds to the user's input */
  public void respondToInput(){
    // read string
    String s = input.nextLine();
    
    // check for exit
    if (s.equals("quit")){
      exit = true;
      return;
    } else if (s.equals("new review")){
      // add review
      addReviewInput();
    } else if(s.equals("search")){
      // search
      searchDatabase();
    } else if(s.equals("size")){
      // return database size
      System.out.println(database.size());
    } else if(s.equals("new listing")){
      // add restaurant
      System.out.println("This feature is under development");
    } else if (s.equals("help")){
      // list commands
      System.out.println("search - search the database");
      System.out.println("new review - add a review");
      System.out.println("new listing - add a restaurant");
      System.out.println("size - gets the size of the database");
      System.out.println("quit - exit the program");
      System.out.println("Note: This database includes listings in Philadelphia, New York, Chicago, San Francisco, and Boston.");
    }
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
    
    // build database
    buildSearchDatabase(city, category, cost, day, time);
    
    System.out.println("Search complete!");
    if (databaseCity.size() != 0){
      System.out.println("We have found " + databaseSorted.size() + " related listings!");
      
      // now list the results
      Iterator<Listing> sortedIterator = databaseSorted.iterator();
      
      // iterate through new database and print listings
      while (sortedIterator.hasNext()){
        sortedIterator.next().toSystem();
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
    System.out.println(name + " now has an average raiting of " + addReview(name, city, score));
  }
  
  /** Adds a review to a restaraunt
    * @return the new rank */
  public float addReview(String name, String city, int score){
    // iterate through database
    Iterator<Listing> iterator = database.iterator();
    
    while(iterator.hasNext()){
      Listing nextListing = iterator.next();
      
      // check if the city matches
      if (nextListing.getCity().equals(city)){
        // check if the name matches
        if (nextListing.getName().equals(name)) {
          // update review and return
          return nextListing.addReview(score);
        }
      }
    }
    // announce that nothing was found
    System.out.println("No listings were found!  Please check your spelling and try again.");
    return 0;
  }
  
  /** create a database filled with search results and return it
    * @param city The name of the city 
    * @param category The category being searched for
    * @param cost The cost of the listing
    * @param day The day to check
    * @param time The time to check */
  public void buildSearchDatabase(String city, String category, String cost, String day, String time){
    // create iterator
    Iterator<Listing> iterator = database.iterator();
    // instanciate database
    databaseCity = new HashSet<Listing>();
    // convert time to float
    float timeFloat = Schedule.stringToTime(time);
    
    // loop through all restaraunts
    while(iterator.hasNext()){
      Listing nextListing = iterator.next();
      
      // add if the city and time matches ASSUME FOR NOW THAT ALL CLOSE TIMES ARE BEFORE MIDNIGHT
      if (nextListing.getCity().equals(city)){
        // check if time matches
        float openTime = nextListing.getSchedule().getDay(day).getOpenTime();
        float closeTime = nextListing.getSchedule().getDay(day).getCloseTime();
        if (openTime <= timeFloat && closeTime >= timeFloat) {
          // check if cost matches
          if (nextListing.getCost().equals(cost)){
            // copy into new database, sorting by the other matching criteria
            databaseCity.add(nextListing);
          }
        }
      }
    }
    
    // sort
    sortDatabase();
  }
  
  // rework read method to interpret what the first word is so that it can handle a randomized database
  // get times to work for 24 hours
  
  // sorts the listings that are related to the search
  private void sortDatabase(){
    // instanciate sorted database
    databaseSorted = new TreeSet<Listing>(new RankComparator<Listing>());
    
    // fill sorted database
    Iterator<Listing> dbCityIterator = databaseCity.iterator();
    
    while (dbCityIterator.hasNext()){
      databaseSorted.add(dbCityIterator.next());
    }
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
        // get name
        String name = findName(inScan,stringPattern);
        
        // get city
        String city = findCity(inScan,stringPattern);
        
        // categories
        ArrayList<String> categories = findCategories(inScan,stringPattern);
        
        // schedule
        Schedule schedule = findSchedule(inScan,stringPattern);
        
        // cost
        String cost = findCost(inScan,stringPattern);
        
        // rank
        int rank = findRank(inScan,stringPattern);
        
        // reviewers
        int revCount = findReviewers(inScan,stringPattern);
        
        // create new listing and add to database
        Listing newListing = new Listing(name, city, schedule, categories, cost, rank, revCount);
        database.add(newListing);
        
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
      return 0;
    }
  }
  
  // EXTRACT DATA FROM FILE
  // gets the name for a listing
  private String findName(Scanner inScan,Pattern stringPattern){
    // get string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // skip a line
    inScan.nextLine();
    
    //System.out.println(matchString.group(3)); // remove later
    return matchString.group(3);
  }
  
  // gets the city for a listing
  private String findCity(Scanner inScan,Pattern stringPattern){
    // get the string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // go to new line
    inScan.nextLine();
    
    //System.out.println(matchString.group(3));// remove later
    return matchString.group(3);
  }
  
  // get the categories for a listing
  private ArrayList<String> findCategories(Scanner inScan,Pattern stringPattern){
    // get the string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // split string into array
    String[] catArray = matchString.group(3).split(",");
    
    // create array list
    ArrayList<String> catList = new ArrayList<String>();
    
    // add elements to list
    for (int i = 0; i<catArray.length;i++){
      catList.add(catArray[i]);
      //System.out.println(catArray[i]);// remove later
    }
    
    // go to new line
    inScan.nextLine();
    
    return catList;
  }
  
  // get the schedule for a listing
  private Schedule findSchedule(Scanner inScan,Pattern stringPattern){
    // get the string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // split string into array
    String[] dayArray = matchString.group(3).split(",");
    
    // create schedule
    Schedule schedule = new Schedule();
    
    // add days to schedule
    for (int i = 0; i<dayArray.length;i++){
      // split element into day, open, and close
      String[] timeArray = dayArray[i].split(" ");
      
      // add day to schedule
      schedule.setDay(timeArray[0],Schedule.stringToTime(timeArray[1]),Schedule.stringToTime(timeArray[2]));
    }
    
    // go to new line
    inScan.nextLine();
    
    return schedule;
  }
  
  // get cost
  private String findCost(Scanner inScan,Pattern stringPattern){
    // get string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // skip a line
    inScan.nextLine();
    
    //System.out.println(matchString.group(3)); // remove later
    return matchString.group(3);
  }
  
  // get rank
  private int findRank(Scanner inScan,Pattern stringPattern){
    // get string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // skip a line
    inScan.nextLine();
    
    //System.out.println(matchString.group(3)); // remove later
    return Integer.parseInt(matchString.group(3));
  }
  
  // get the number of reviewers
  private int findReviewers(Scanner inScan,Pattern stringPattern){
    // get string and match
    inScan.findInLine(stringPattern);
    MatchResult matchString = inScan.match();
    
    // skip a line
    inScan.nextLine();
    
    //System.out.println(matchString.group(3)); // remove later
    return Integer.parseInt(matchString.group(3));
  }
  
  // setters and getters
  /** Gets the database
    * @return the database of listings */
  public HashSet<Listing> getDatabase(){
    return database;
  }
  
  /** Gets the city database
    * @return the database of listings related to the search */
  public HashSet<Listing> getCityDatabase(){
    return databaseCity;
  }
  
  /** Gets the city database
    * @return the database of sorted listings related to the search */
  public TreeSet<Listing> getSortedDatabase(){
    return databaseSorted;
  }
}