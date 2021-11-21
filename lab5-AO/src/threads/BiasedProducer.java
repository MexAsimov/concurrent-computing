package threads;

import activeObject.CompletionFuture;
import activeObject.Proxy;
import activeObject.Task;

public class BiasedProducer extends Producer{
    protected final int amount;

    public BiasedProducer(int id, Proxy proxy, int amount, Task task) {
        super(id, proxy, 0, task);
        this.amount = amount;
    }

    @Override
    public void produce() throws InterruptedException {
        CompletionFuture future = proxy.produce(amount);
        task.run(); // zadanie do wykonania w trakcie
        future.waitUntilCompletion();
        accessCounter++;
    }
}
