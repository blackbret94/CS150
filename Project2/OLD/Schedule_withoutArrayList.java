/** This class handles the times for a listing 
  * @author Bret Black
  */
import java.util.*;

public class Schedule {
  /** ArrayList containing the days, organized Sunday = 0, Monday = 1, etc. */
  private ArrayList<Day> dayList;
  
  /** The default constructor */
  public Schedule(){
    dayList = new ArrayList<Day>(7);
  }
  
  /** Sets a day in the ArrayList 
    * @param dayName The day of the week
    * @param open The time to open
    * @param close the time to close
    * @return the day that was created
    */
  public Day setDay(String dayName,float open, float close){
    // the numerical equivilent of the day
    
    // add 24 if needed
    if (close < open){
      close += 24.0f;
    }
    
    // create new day
    Day newDay = new Day(dayName,open,close);
    
    // replace on list
    dayList.add(newDay);
    
    return newDay;
  }
  
  /** converts the class into a string
    * @return Class as string */
  public String toString(){
    String scheduleString = new String();
    
    // add first day to string
    if (dayList.size() > 0){
      scheduleString = dayList.get(0).toString();
    } else return null;

    // add commas and additional days to string
    for (int i = 1;i<dayList.size(); i++){
        scheduleString = new String(scheduleString + "," + dayList.get(i).toString());
    }
    
    // return string
    return scheduleString;
  }
  
  /** Gets a day in the ArrayList
    * @param dayName the day being saught 
    * @return the day that was saught
    */
  public Day getDay(String dayName){
    // the numerical equivilent of the day
    int dayNumber;
    
    for (int i = 0; i < dayList.size(); i++){
      if (dayName.equals(dayList.get(i).getDay())){
        return dayList.get(i);
      }
    }
    
    return null;
  }
  
  /** This static method converts a time to a string 
    * @param f The time value
    * @return the time in 00:00 format
    */
  public static String timeToString(float f){
    // subtract 24 if needed
    if (f >= 24.0f){
      f -= 24.0f;
    }
    
    // convert to String
    String floatAsString = String.valueOf(f);
    
    // instanciate output string
    String r = new String();
    
    // split string into groups
    char[] floatAsArray = floatAsString.toCharArray();
    
    // check to see if the second value is a .
    if (floatAsArray[1] == '.'){
      r = new String("0" + String.valueOf(floatAsArray[0]) + ":");
      
      // check size of array
      if (floatAsArray.length < 4){
        // must add zero at the end
        r = new String(r + String.valueOf(floatAsArray[2]) + "0");
      } else {
        // do not add zero
        r = new String(r + String.valueOf(floatAsArray[2]) + String.valueOf(floatAsArray[3]));
      }
    } else {
      r = new String(String.valueOf(floatAsArray[0]) + String.valueOf(floatAsArray[1]) + ":");
      
      // check size of array
      if (floatAsArray.length < 5){
        // must add zero at the end
        r = new String(r + String.valueOf(floatAsArray[3]) + "0");
      } else {
        // do not add zero
        r = new String(r + String.valueOf(floatAsArray[3]) + String.valueOf(floatAsArray[4]));
      }
    }
    
    // return string
    return r;
  }
  
  /** This static method converts a string to a time 
    * @param s The string time
    * @return the time in 00.00 format
    */
  public static float stringToTime(String s){
    if (s.equals("*")){
      // wildcard
      return 0;
    }
    
    // split into groups
    String[] timeArray = s.split(":");
    
    // multiply second group by .01 and add
    float floatTime = Float.parseFloat(timeArray[0]) + (Float.parseFloat(timeArray[1])*0.01f);
    return floatTime;
  }
  
  // DAY CLASS
  /** This class handles the times for a single day */
  public class Day {
    // instance variables
    /** Day */
    private String day;
    /** Open time */
    private float openTime;
    /** Close time */
    private float closeTime;
    
    /** Default constructor */
    public Day(){
      day = new String("Sunday");
      openTime = 0.0f;
      closeTime = 0.0f;
    }
    
    /** Day with param
      * @param dayName The day of the week
      * @param open The time to open
      * @param close the time to close */
    public Day(String dayName,float open, float close){
      day = dayName;
      openTime = open;
      closeTime = close;
    }
    
    /** Returns the day as a string
      * @return Day as string */
    public String toString(){
      // make string
      String dayString = new String();
      
      // add
      dayString = new String(day + " " + Schedule.timeToString(openTime) + " " + Schedule.timeToString(closeTime));
      
      // return
      return dayString;
    }
    
    // setters and getters
    /** Gets the day 
      * @return the associated day
      */
    public String getDay(){
      return day;
    }
    
    /** Gets the opening time 
      * @return The time the listing opens
      */
    public float getOpenTime(){
      return openTime;
    }
    /** Gets the close time 
      * @return The time the listing closes
      */
    public float getCloseTime(){
      return closeTime;
    }
  }
}