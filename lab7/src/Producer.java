import org.jcsp.lang.*;

public class Producer implements CSProcess {
    private final SharedChannelOutputInt request;
    private final AltingChannelInputInt channelInput;
    private final int id;
    private int counter = 0, failures = 0;

    public Producer (int id, final SharedChannelOutputInt channel, final One2OneChannelInt answer) {
        this.channelInput = answer.in();
        this.request = channel;
        this.id = id;
    }
    public void run (){
        while(true){
            request.write(id); // producer's request
            int acc = channelInput.read(); // waiting for confirmation
            if(acc == -1){
                failures++;
            }
            counter++;
            //System.out.println("Producer " + getId() + ": " + failures + "/" + counter);

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

