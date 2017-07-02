import org.junit.Assert;
import org.junit.Test;

public class CycleBufferTest {
  @Test
  public void testPushPop() throws BufferOverflowException, BufferEmptyException {
    CycleBuffer<Integer> cycleBuffer = new CycleBuffer<>(5);
    cycleBuffer.push(1);
    cycleBuffer.push(1);
    cycleBuffer.push(1);
    cycleBuffer.push(1);
    cycleBuffer.push(1);
    Assert.assertEquals((long)1, (long)cycleBuffer.pop());
  }

  @Test
  public void testPushOverflow() throws BufferOverflowException, BufferEmptyException {
    CycleBuffer<Integer> cycleBuffer = new CycleBuffer<>(5);
    cycleBuffer.push(1);
    cycleBuffer.push(2);
    cycleBuffer.pop();
    cycleBuffer.push(3);
    cycleBuffer.push(4);
    cycleBuffer.push(5);
    cycleBuffer.push(6);
    Assert.assertEquals((long)2, (long)cycleBuffer.pop());
    Assert.assertEquals((long)3, (long)cycleBuffer.pop());
    Assert.assertEquals((long)4, (long)cycleBuffer.pop());
    Assert.assertEquals((long)5, (long)cycleBuffer.pop());
    Assert.assertEquals((long)6, (long)cycleBuffer.pop());
  }

  @Test(expected = BufferOverflowException.class)
  public void testPushOverflowException() throws BufferOverflowException, BufferEmptyException {
    CycleBuffer<Integer> cycleBuffer = new CycleBuffer<>(5);
    cycleBuffer.push(1);
    cycleBuffer.push(2);
    cycleBuffer.pop();
    cycleBuffer.push(3);
    cycleBuffer.push(4);
    cycleBuffer.push(5);
    cycleBuffer.push(6);
    cycleBuffer.push(7);
  }

  @Test(expected = BufferEmptyException.class)
  public void testPopEmptyException() throws BufferOverflowException, BufferEmptyException {
    CycleBuffer<Integer> cycleBuffer = new CycleBuffer<>(5);
    cycleBuffer.push(1);
    cycleBuffer.push(2);
    cycleBuffer.pop();
    cycleBuffer.push(3);
    cycleBuffer.push(4);
    cycleBuffer.push(5);
    cycleBuffer.push(6);
    cycleBuffer.pop();
    cycleBuffer.pop();
    cycleBuffer.pop();
    cycleBuffer.pop();
    cycleBuffer.pop();
    cycleBuffer.pop();
  }
}
