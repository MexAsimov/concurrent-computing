import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor{
    public int maxPortionSize;
    public int maxBuffer;
    public int buffer;
    private int pc = 1;
    ReentrantLock lock = new ReentrantLock(true); // budzenie wątków, które najdłużej czekają
    Condition producersCond = lock.newCondition();
    Condition consumersCond = lock.newCondition();
    Condition firstProducerCond = lock.newCondition();
    Condition firstConsumerCond = lock.newCondition();

    public Monitor(int bufferSize, int maxPortionSize){
        this.maxBuffer = bufferSize;
        this.maxPortionSize = maxPortionSize;
        this.buffer = 0;
    }

    public void produce(int portion) {
        try {
            lock.lock();
            while(lock.hasWaiters(firstProducerCond)){
                producersCond.await();
            }
            while(buffer + portion > maxBuffer){
                firstProducerCond.await();
            }

            buffer += portion;
//            System.out.println("___ Operation nr " + pc++ + " ___");
//            System.out.println("producing " + portion + " -> " + buffer);

            firstConsumerCond.signal();
            producersCond.signal();
            
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

            while(lock.hasWaiters(firstConsumerCond)){
                consumersCond.await();
            }

            while(buffer - portion < 0){
                firstConsumerCond.await();
            }

            buffer -= portion;
//            System.out.println("___ Operation nr " + pc++ + " ___");
//            System.out.println("consuming " + portion + " -> " + buffer);

            firstProducerCond.signal();
            consumersCond.signal();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
