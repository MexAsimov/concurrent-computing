import org.jcsp.lang.CSProcess;

public class InformationModule implements CSProcess {

    private final Producer[] producers;
    private final Consumer[] consumers;
    private final Buffer[] buffers;
    private final int noProducers;
    private final int noConsumers;
    private final int noBuffers;

    public InformationModule(Producer[] producers, Consumer[] consumers, Buffer[] buffers){
        this.producers = producers;
        this.consumers = consumers;
        this.buffers = buffers;
        this.noProducers = producers.length;
        this.noConsumers = consumers.length;
        this.noBuffers = buffers.length;
    }

    @Override
    public void run() {
        // time for metrics
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i=0; i<noProducers; i++){
            Producer prod = producers[i];
            System.out.println("P" + i + " " + prod.getFailures()/prod.getCounter()*100);
            //System.out.println("P" + i + " " + prod.getCounter());
        }
        for (int i=0; i<noConsumers; i++){
            Consumer cons = consumers[i];
            System.out.println("C" + i + " " + cons.getFailures()/cons.getCounter()*100);
            //System.out.println("C" + i + " " + cons.getCounter());
        }
        for (int i=0; i<noBuffers; i++){
            Buffer buff = buffers[i];
            System.out.println("B" + i + " " + buff.getFailures()/buff.getCounter()*100);
            //System.out.println("B" + i + " " + buff.getCounter());
        }
    }
}
