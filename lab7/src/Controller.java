import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Controller implements CSProcess {

    private final AltingChannelInputInt input;
    private final One2OneChannelInt[] buffers;
    private final One2OneChannelInt[] answerP;
    private final One2OneChannelInt[] answerC;
    private final One2OneChannelInt[] answerB;
    private int lastP = 0;
    private int lastC = 0;
    private int timeout;
    private final int id;

    public Controller(final int id, AltingChannelInputInt input, One2OneChannelInt[] buffers, One2OneChannelInt[] answerP, One2OneChannelInt[] answerC, One2OneChannelInt[] answerB) {
        this.input = input;
        this.buffers = buffers;
        this.answerP = answerP;
        this.answerC = answerC;
        this.answerB = answerB;
        this.id = id;
    }

    public void run(){
        System.out.println("Initializing controller...");
        timeout = this.buffers.length;
        boolean success;
        int client;
        while(true){
            int type = input.read();

            if (type > 0){ // PRODUCE
                success = false;
                client = type-1;
                for(int i=0; i<timeout; i++){
                    buffers[(lastP+i)%timeout].out().write(1);
                    if(answerB[(lastP+i)%timeout].in().read() == 1){
                        //System.out.println(client + " Writing into " + (lastP+i)%timeout + " buffer");
                        answerP[client].out().write(1);
                        lastP = (lastP+i+1)%timeout;
                        success = true;
                        break;
                    }
                }
                if(!success){
                    answerP[client].out().write(-1);
                }
            } else if (type < 0){ // CONSUME
                success = false;
                client = Math.abs(type)-1;
                for(int i=0; i<timeout; i++){
                    buffers[(lastC+i)%timeout].out().write(-1);
                    if(answerB[(lastC+i)%timeout].in().read() == 1){
                        //System.out.println(client + " Taking from " + (lastC+i)%timeout + " buffer");
                        answerC[client].out().write(1);
                        lastC = (lastC+i+1)%timeout;
                        success = true;
                        break;
                    }
                }
                if(!success){
                    answerC[client].out().write(-1);
                }
            } else {
                //System.out.println("Input ignored..");
            }
        }
    }
}
