import org.jcsp.lang.*;


public class PCMain {
    public static void main (String[] args) throws InterruptedException {
        new PCMain();
    }

    public PCMain() throws InterruptedException {
        int noProducers = 8;
        int noConsumers = 3;
        int noBuffers = 2;
        int buffersSize = 5;
        // Create channel objects
        Any2OneChannelInt sharedChannel = Channel.any2oneInt();
        One2OneChannelInt[] producerAnswer = new One2OneChannelInt[noProducers];
        for (int i=0; i<noProducers; i++){
            producerAnswer[i] = Channel.one2oneInt();
        }
        One2OneChannelInt[] consumerAnswer = new One2OneChannelInt[noConsumers];
        for (int i=0; i<noConsumers; i++){
            consumerAnswer[i] = Channel.one2oneInt();
        }
        One2OneChannelInt[] bufferChannels = new One2OneChannelInt[noBuffers];
        One2OneChannelInt[] bufferAnswer = new One2OneChannelInt[noBuffers];
        for (int i=0; i<noBuffers; i++){
            bufferChannels[i] = Channel.one2oneInt();
            bufferAnswer[i] = Channel.one2oneInt();
        }

        CSProcess[] procList = new CSProcess[noConsumers + noProducers + noBuffers + 2];
        procList[0] = new Controller(0, sharedChannel.in(), bufferChannels, producerAnswer, consumerAnswer, bufferAnswer);

        Producer[] producers = new Producer[noProducers];
        for (int i=0; i<noProducers; i++){
            Producer prod = new Producer(i+1, sharedChannel.out(), producerAnswer[i]);
            procList[i+1] = prod;
            producers[i] = prod;
        }

        Consumer[] consumers = new Consumer[noConsumers];
        for (int i=0; i<noConsumers; i++){
            Consumer cons = new Consumer(i+1, sharedChannel.out(), consumerAnswer[i]);
            procList[noProducers+i+1] = cons;
            consumers[i] = cons;
        }

        Buffer[] buffers = new Buffer[noBuffers];
        for (int i=0; i<noBuffers; i++){
            Buffer buff = new Buffer(i, bufferChannels[i], bufferAnswer[i], buffersSize);
            procList[noConsumers+noProducers+i+1] = buff;
            buffers[i] = buff;
        }

        procList[noConsumers+noProducers+noBuffers+1] = new InformationModule(producers, consumers, buffers);

        // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel

    }
}
