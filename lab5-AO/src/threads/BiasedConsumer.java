package threads;

import activeObject.CompletionFuture;
import activeObject.Proxy;
import activeObject.Task;

public class BiasedConsumer extends Consumer{
    protected final int amount;

    public BiasedConsumer(int id, Proxy proxy, int amount, Task task) {
        super(id, proxy, 0, task);
        this.amount = amount;
    }

    @Override
    public void consume() throws InterruptedException {
        CompletionFuture future =  proxy.consume(amount);
        task.run(); // zadanie do wykonania w trakcie
        future.waitUntilCompletion();
        accessCounter++;
    }
}
