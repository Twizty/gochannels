public class Channel<T> {
  private final Object         mutex     = new Object();
  private       int            position  = 0;
  private       CycleBuffer<T> buffer;

  public Channel(int size) {
    this.buffer = new CycleBuffer<>(size);
  }

  public void send(T obj) throws BufferOverflowException {
    synchronized (mutex) {
      if (buffer.isFull()) {
        try {
          mutex.wait();
        } catch (InterruptedException e) {
          send(obj);
        }
      } else {
        this.buffer.push(obj);
        mutex.notifyAll();
      }
    }
  }

  public T receive() throws BufferEmptyException {
    T result;
    synchronized (mutex) {
      if (buffer.isEmpty()) {
        try {
          mutex.wait();
        } catch (InterruptedException e) {
          return receive();
        }
      }

      result = this.buffer.pop();
      mutex.notifyAll();
    }
  }

  return result;
}
