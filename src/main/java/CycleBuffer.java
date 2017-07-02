import java.util.ArrayList;

class BufferOverflowException extends Exception {}
class BufferEmptyException extends Exception {}

public class CycleBuffer<T> {
  private int          start  = 0;
  private int          end    = 0;
  private int          length = 0;
  private int          capacity;
  private ArrayList<T> buffer;

  public CycleBuffer(int size) {
    this.buffer   = new ArrayList<>(size);
    this.capacity = size;
    for (int i = 0; i < size; i++) {
      this.buffer.add(null);
    }
  }

  public void push(T obj) throws BufferOverflowException {
    if (!canPush()) { throw new BufferOverflowException(); }

    int p = end;
    incrementEnd();
    length++;
    buffer.set(p, obj);
  }

  public T pop() throws BufferEmptyException {
    if (!canPop()) { throw new BufferEmptyException(); }

    int p = start;
    incrementStart();
    length--;
    return buffer.get(p);
  }

  public boolean isFull() {
    return this.capacity == this.length;
  }

  public boolean isEmpty() {
    return this.length == 0;
  }

  public int getCapacity() {
    return this.capacity;
  }

  private int incrementStart() {
    if (start < capacity - 1) {
      return ++start;
    } else {
      return start = 0;
    }
  }

  private int incrementEnd() {
    if (end < capacity - 1) {
      return ++end;
    } else {
      return end = 0;
    }
  }

  private boolean canPop() {
    return start != end || length == capacity;
  }

  private boolean canPush() {
    return !((end == capacity && start == 0) || (end != capacity && start != 0 && start == end));
  }
}
