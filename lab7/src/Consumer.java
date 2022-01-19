import org.jcsp.lang.*;

public class Consumer implements CSProcess {
    private final AltingChannelInputInt channelInput;
    private final SharedChannelOutputInt request;
    private final int id;
    private int counter = 0, failures = 0;

    public Consumer (int id, final SharedChannelOutputInt channel, final One2OneChannelInt answer) {
        this.channelInput = answer.in();
        this.request = channel;
        this.id = id;
    }

    public void run () {
        int acc;
        while(true){
            request.write(-id); // consumer's request
            acc = channelInput.read(); // waiting for confirmation
            if(acc == -1){
                failures++;
            }

            counter++;
            //System.out.println("Consumer " + getId() + ": " + failures + "/" + counter);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public int getId(){ return id; }
    public double getCounter(){
        return counter;
    }
    public double getFailures(){ return failures; }
}