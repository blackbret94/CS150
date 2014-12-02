/** This class holds all databasees 
  * @author Bret Black */

import java.util.*;
import java.io.*;

public class Data {
  /** This HashSet stores all the restaurants */
  private HashSet<Listing> database;
  /** This HashSet only stores restaurants that meet the search criteria.  Order is ignored */
  private HashSet<Listing> databaseCity;
  /** This structure contains the results as sorted by rank, reviewers, and then alphabetically */
  private TreeSet<Listing> databaseSorted;
  
  /** The default constructor */
  public Data(){
    // add database
    database = new HashSet<Listing>();
  }
  
  /** create a database filled with search results and return it
    * @param city The name of the city 
    * @param category The category being searched for
    * @param cost The cost of the listing
    * @param day The day to check
    * @param time The time to check */
  public void buildSearchDatabase(String city, ArrayList<String> category, String cost, String day, String time){
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
      if (nextListing.getCity().equals(city) || city.equals("*")){
        // check if time matches
        if ((nextListing.getSchedule().matchDayTime(day,time)) || (time.equals("*")&&nextListing.getSchedule().matchDay(day)) || (day.equals("*")&&nextListing.getSchedule().matchTime(time))|| (day.equals("*") && time.equals("*"))) {
          // check if cost matches
          if (nextListing.getCost().equals(cost) || cost.equals("*")){
            // check to see if categories match by nesting a for-loop
            boolean categoryMatch = false;
            int matchCount = 0; // track the number of matching categories
            
            for(int i = 0; i < nextListing.getCategories().size();i++){
              for(int j = 0; j < category.size();j++){
                if (nextListing.getCategories().get(i).equals(category.get(j))){
                  matchCount++;
                }
              }
            }
            
            // check match
            if (matchCount == category.size()){
              categoryMatch = true;
            }
            
            // reset
            matchCount = 0;
            
            if (categoryMatch || category.get(0).equals("*")){
              // copy into new database
              databaseCity.add(nextListing);
            }
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
  
  /** Writes the database to a file
    * @param file the name of the output file */
  public void writeToFile(File file){
    // get iterator
    Iterator<Listing> iterator = database.iterator();
    
    try {
      // buffered writer
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      
      // go through database and write
      while (iterator.hasNext()){
        Listing nextListing = iterator.next();
        
        // name
        writer.write("name: " + nextListing.getName());
        writer.newLine();
        
        // city
        writer.write("city: " + nextListing.getCity());
        writer.newLine();
        
        // category
        writer.write("category: " + nextListing.categoriesToString());
        writer.newLine();
        
        // open hours
        writer.write("open: " + nextListing.getSchedule().toString());
        writer.newLine();
        
        // cost
        writer.write("cost: " + nextListing.getCost());
        writer.newLine();
        
        // rank
        writer.write("rank: " + nextListing.getRank());
        writer.newLine();
        
        // revcount
        writer.write("reviewers: " + nextListing.getRevCount());
        writer.newLine();
        
        // blank line
        writer.newLine();
      }
      
      // close
      writer.newLine();
      writer.close();
    } catch (java.io.IOException e){
      System.out.println("An error occured!  Could not save file.");
    }
    
  }
  
  /** Adds a listing 
    * @param name the name of the listing
    * @param city the location of the listing
    * @param schedule the schedule of the listing
    * @param categories the categories the listing falls under
    * @param cost the cost of the listing
    * @return the new listing
    */
  public Listing addListing(String name, String city, Schedule schedule, ArrayList<String> categories,String cost){
    // add the listing to the database
    Listing newListing = new Listing(name, city, schedule, categories, cost, 0, 0);
    database.add(newListing);
    return newListing;
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