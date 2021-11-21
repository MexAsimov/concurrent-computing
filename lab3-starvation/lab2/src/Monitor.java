import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor{
    public int maxPortionSize;
    public int maxBuffer;
    public int buffer;
    private int pc = 1;
    ReentrantLock lock = new ReentrantLock(true);
    Condition fullBuffer = lock.newCondition();
    Condition emptyBuffer = lock.newCondition();

    public Monitor(int bufferSize, int maxPortionSize){
        this.maxBuffer = bufferSize;
        this.maxPortionSize = maxPortionSize;
        this.buffer = 0;
    }

    public void produce(int portion) {
        try {
            lock.lock();
            while(buffer + portion > maxBuffer){
                fullBuffer.await();
            }
            buffer += portion;
            //System.out.println("___ Operation nr " + pc++ + " ___");
            //System.out.println("producing " + portion + " -> " + buffer);
            emptyBuffer.signal();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public void consume(int portion){
        try {
            lock.lock();
            while(buffer - portion < 0){
                emptyBuffer.await();
            }
            buffer -= portion;
            //System.out.println("___ Operation nr " + pc++ + " ___");
            //System.out.println("consuming " + portion + " -> " + buffer);
            fullBuffer.signal();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
