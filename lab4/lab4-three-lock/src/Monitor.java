import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor{
    public int maxPortionSize;
    public int maxBuffer;
    public int buffer;
    private int pc = 1;
    ReentrantLock lockP = new ReentrantLock(true); // budzenie wątków, które najdłużej czekają
    ReentrantLock lockC = new ReentrantLock(true);
    ReentrantLock lockCommon = new ReentrantLock(true);
    Condition condCommon = lockCommon.newCondition();

    public Monitor(int bufferSize, int maxPortionSize){
        this.maxBuffer = bufferSize;
        this.maxPortionSize = maxPortionSize;
        this.buffer = 0;
    }

    public void produce(int portion) {
        try {
            lockP.lock();
            try {
                lockCommon.lock();
                while (buffer + portion > maxBuffer) {
                    condCommon.await();
                }
                buffer += portion;
                //System.out.println(" ++" + portion);
                condCommon.signal();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lockCommon.unlock();
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            lockP.unlock();
        }
    }

    public void consume(int portion){
        try {
            lockC.lock();
            try {
                lockCommon.lock();
                while(buffer - portion < 0){
                    condCommon.await();
                }
                buffer -= portion;
                //System.out.println(" --" + portion);
                condCommon.signal();
            } catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                lockCommon.unlock();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            lockC.unlock();
        }
    }
}
