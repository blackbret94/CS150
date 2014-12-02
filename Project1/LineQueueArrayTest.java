import junit.framework.TestCase;

public class LineQueueArrayTest extends TestCase {
  // instance to use
  private LineQueueArray<Integer> testQueue;
  
  public void setUp(){
    // create a new instance of the queue
    testQueue = new LineQueueArray<Integer>();
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
  
  public void testPeek(){
    // add data
    addData();
    
    // run method
    int front = testQueue.peek();
    
    // make sure element is still there
    assertTrue(testQueue.peek() == front);
  }
  
  // make sure ShouldRemove is flipping
  public void testFlip(){
    // add data
    addData();
    
    // read state
    boolean removeState = testQueue.getShouldRemove();
    
    // run method
    testQueue.oneTimeStep();
    
    // check that state has changed
    assertTrue(removeState != testQueue.getShouldRemove());
    
    // read again
    removeState = testQueue.getShouldRemove();
    
    // run method again
    testQueue.oneTimeStep();
    
    // check that the state has changed again
    assertTrue(removeState != testQueue.getShouldRemove());
  }
  
  // test that the state does not flip with an empty queue
  public void testFlipEmpty(){
    for (int i = 0; i < 20; i++){
      // make sure it is empty
      assertTrue(testQueue.isEmpty());
      
      // get state
      boolean removeState = testQueue.getShouldRemove();
      
      // run step
      testQueue.oneTimeStep();
      
      // make sure the state hasn't changed
      assertTrue(removeState == testQueue.getShouldRemove());
    }
    
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
