import activeObject.Proxy;
import activeObject.Task;
import threads.Consumer;
import threads.Producer;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Task servantTask = new Task() {
            @Override
            public void run() {
                double a = Math.sin(12.65);
            }
        };

        Task producerTask = new Task() {
            @Override
            public void run() {
                double a = Math.sin(83.55);
            }
        };

        Task consumerTask = new Task() {
            @Override
            public void run() {
                double a = Math.sin(42.64);
            }
        };

        int noOfProducers = 10;
        int noOfConsumers = 10;
        int bufferCapacity = 10;

        Proxy proxy = new Proxy(bufferCapacity, servantTask);

        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();

        for (int i = 0; i < noOfProducers; i++)
            producers.add(new Producer(i, proxy, bufferCapacity / 2, producerTask));
        for (int i = 0; i < noOfConsumers; i++)
            consumers.add(new Consumer(i, proxy, bufferCapacity / 2, consumerTask));

        for (Producer producer : producers)
            producer.start();
        for (Consumer consumer : consumers)
            consumer.start();
    }
}
