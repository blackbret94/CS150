import junit.framework.TestCase;

public class ScheduleTest extends TestCase {
  
  public void testStringToTime() {
    // convert string to float
    String s = new String("08:30");
    float sFloat = Schedule.stringToTime(s);
    
    // check
    assertTrue(sFloat == 8.30f);
    
    // convert string to float
    s = new String("15:45");
    sFloat = Schedule.stringToTime(s);
    
    // check
    assertTrue(sFloat == 15.45f);
    
    // convert string to float
    s = new String("02:00");
    sFloat = Schedule.stringToTime(s);
    
    // check
    assertTrue(sFloat == 2.00f);
  }
  
  public void testTimeToString(){
    // convert string to float
    float f = 03.45f;
    String fString = Schedule.timeToString(f);
    
    // check
    assertTrue(fString.equals("03:45"));
    
    // convert string to float
     f = 10.30f;
     fString = Schedule.timeToString(f);
    
    // check
    assertTrue(fString.equals("10:30"));
    
    // convert string to float
     f = 05.20f;
     fString = Schedule.timeToString(f);
    
    // check
    assertTrue(fString.equals("05:20"));
    
    // convert string to float
     f = 10.37f;
     fString = Schedule.timeToString(f);
    
    // check
    assertTrue(fString.equals("10:37"));
  }
  
  public void testToString(){
    // create
    Schedule s = new Schedule();
    
    // add day
    s.setDay("Monday",5.30f,23.0f);
    
    // check
    assertTrue(s.toString().equals(new String("Monday 05:30 23:00")));
    
    // add more days
    s.setDay("Sunday",5.30f,23.0f);
    s.setDay("Tuesday",5.30f,23.0f);
    s.setDay("Wednesday",5.30f,23.0f);
    
    // check
    assertTrue(s.toString().equals(new String("Monday 05:30 23:00,Sunday 05:30 23:00,Tuesday 05:30 23:00,Wednesday 05:30 23:00")));
  }
  
  public void testMatchDay(){
    // create
    Schedule s = new Schedule();
    
    // add day
    s.setDay("Monday",5.30f,23.0f);
    
    // check
    assertTrue(s.matchDay("Monday"));
    assertTrue(!s.matchDay("Tuesday"));
    
    // add more days
    s.setDay("Sunday",5.30f,23.0f);
    s.setDay("Tuesday",5.30f,23.0f);
    s.setDay("Wednesday",5.30f,23.0f);
    
    // check
    assertTrue(s.matchDay("Monday") && s.matchDay("Wednesday") && s.matchDay("Tuesday") && !s.matchDay("Friday"));
  }
  
  public void testMatchTime(){
    // create
    Schedule s = new Schedule();
    
    // add day
    s.setDay("Monday",5.30f,23.0f);
    
    // check
    assertTrue(s.matchTime("08:00"));
    assertTrue(!s.matchTime("02:00"));
    
    // add more days
    s.setDay("Sunday",5.30f,23.0f);
    s.setDay("Tuesday",8.30f,22.0f);
    s.setDay("Wednesday",10.30f,15.0f);
    
    // check
    assertTrue(s.matchTime("06:00") && s.matchTime("22:30") && s.matchTime("12:00") && !s.matchTime("02:00"));
  }
  
  public void testMatchDayTime(){
    // create
    Schedule s = new Schedule();
    
    // add day
    s.setDay("Monday",5.30f,23.0f);
    
    // check
    assertTrue(s.matchDayTime("Monday","07:00"));
    assertTrue(!s.matchDayTime("Tuesday","07:00") && !s.matchDayTime("Monday","03:00") && !s.matchDayTime("Tuesday","03:00"));
  }
  
  // tests the 'match' methods for cases where there are multiple days for the same day of the week
  public void testMultipleDays(){
    // create
    Schedule s = new Schedule();
    
    // add day
    s.setDay("Monday",5.30f,12.0f);
    s.setDay("Monday",13.30f,23.0f);
    
    // check
    assertTrue(s.matchDayTime("Monday","07:00"));
    assertTrue(s.matchDayTime("Monday","15:00"));
    assertTrue(!s.matchDayTime("Monday","12:30"));
    assertTrue(s.matchDay("Monday"));
    assertTrue(!s.matchDay("Tuesday"));
    assertTrue(s.matchTime("07:00"));
    assertTrue(s.matchTime("18:00"));
    assertTrue(!s.matchTime("12:30"));
  }
}
