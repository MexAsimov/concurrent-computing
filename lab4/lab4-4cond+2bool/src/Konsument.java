import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Konsument extends Thread{
    public Monitor buffer;
    public int id;
    private int counter = 0;
    private long timeReal = 0;
    private long timeCPU;
    private boolean heavy;
    private ThreadMXBean thread = ManagementFactory.getThreadMXBean();

    public Konsument(Monitor monitor, int id) {
        this.buffer = monitor;
        this.id = id;
    }

    public Konsument(Monitor monitor, int id, boolean heavy){
        this.buffer = monitor;
        this.id = id;
        this.heavy = heavy;
    }

    public void run(){
        while(true){
            int portion;
            if(!heavy) {
                portion = 1 + (int) (Math.random() * (this.buffer.maxPortionSize - 1));
            }
            else {
                portion = this.buffer.maxPortionSize;
            }
            // Time measuring here...
            long start = System.nanoTime();

            this.buffer.consume(portion);

            long finish = System.nanoTime();
            timeReal += (finish-start);

            counter++;
            timeCPU = this.thread.getCurrentThreadCpuTime();
        }
    }

    private long showActualRealTime(){
        return timeReal;
    }

    private long showActualCPUTime(){
        return timeCPU;
    }

    public void showCounter(){
        System.out.println("[-" + ((this.heavy) ? "H" : "") + id + "-] - Operations: " + counter +
                " - REAL TIME: " + showActualRealTime() +" - CPU TIME: " + showActualCPUTime());
    }
}
