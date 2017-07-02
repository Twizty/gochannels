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
        mutex.notifyAll();
        this.buffer.push(obj);
      }
    }
  }

  public T receive() throws BufferEmptyException {
    synchronized (mutex) {
      if (buffer.isEmpty()) {
        try {
          mutex.wait();
        } catch (InterruptedException e) {
          return receive();
        }
      }

      mutex.notifyAll();
      return this.buffer.pop();
    }
  }
}
