import junit.framework.TestCase;

public class LineQueueListTest extends TestCase {
  // instance to use
  private LineQueueList<Integer> testQueue;
  
  public void setUp(){
    // create a new instance of the queue
    testQueue = new LineQueueList<Integer>();
  }
  
  // test removing an element
  public void testPoll() {
    // add data
    addData();
    
    // run method
    int front = testQueue.peek();
    int frontRemoved =  testQueue.poll();
    
    // make sure the removed elment is equal to the peeked elment
    assertTrue(front == frontRemoved);
    
    // make sure the element is gone
    assertTrue(testQueue.peek() != frontRemoved);
  }
  
  public void testClone(){
    // create queue
    testQueue.add(19);
    testQueue.add(15);
    testQueue.add(2);
    
    // test size and elements
    assertTrue(testQueue.size() == 3);
    assertTrue(testQueue.peek() == 19);
    
    // clone
    LineQueueList<Integer> l = testQueue.clone();
    
    // test size and elements
    assertTrue(l.size() == 3);
    assertTrue(l.poll() == 19);
    assertTrue(l.poll() == 15);
    assertTrue(l.poll() == 2);
  }
  
  public void testPeek(){
    // add data
    addData();
    
    // run method
    int front = testQueue.peek();
    
    // make sure element is still there
    assertTrue(testQueue.peek() == front);
  }
  
  public void testAdd(){
    // front   
    // add element
    testQueue.add(19);
    
    // make sure element is in place
    assertTrue(testQueue.peek() == 19);
    
    // second
    // add element
    testQueue.add(99);
    
    // remove front
    testQueue.poll();
    
    // make sure element is in place
    assertTrue(testQueue.peek() == 99);
  }
  
  public void testSize(){
    // test when empty
    assertTrue(testQueue.isEmpty() && testQueue.size() == 0);
    
    // test with a larger number
    int dataSize = addData();
    assertTrue(testQueue.size() == dataSize);
  }
  
  // add data to the queue
  public int addData(){
    // save data size
    int dataSize = 20;
    
    // add data
    for (int i = 0; i < dataSize; i++){
      testQueue.add(i*2);
    }
    
    // return data size
    return dataSize;
  }
}
