import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    private final AltingChannelInputInt channelIn;
    private final ChannelOutputInt channelOut;
    private final int size;
    private final int id;
    private int noElements = 0;
    private int counter = 0, failures = 0;


    public Buffer(final int id, final One2OneChannelInt channel, final One2OneChannelInt answer, int size){
        this.channelIn = channel.in();
        this.channelOut = answer.out();
        this.size = size;
        this.id = id;
    }

    @Override
    public void run() {
        int request;
        while(true){
            request = channelIn.read(); // waiting for data
            if(request == 1){
                if(this.canProduce()) {
                    channelOut.write(1); // success
                    noElements++;
                } else {
                    channelOut.write(-1); // failure
                    failures++;
                }
            } else {
                if(this.canConsume()) {
                    channelOut.write(1); // success
                    noElements--;
                } else {
                    channelOut.write(-1); // failure
                    failures++;
                }
            }
            counter++;
        }
    }

    public double getCounter(){
        return counter;
    }

    public double getFailures(){
        return failures;
    }

    public boolean canProduce() { return noElements != size; }

    public boolean canConsume() { return noElements != 0; }
}
