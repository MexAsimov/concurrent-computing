package threads;

import activeObject.CompletionFuture;
import activeObject.Proxy;
import activeObject.Task;

public class Producer extends Client {

    public Producer(int id, Proxy proxy, int maxPortion, Task task) {
        super(id, proxy, maxPortion, task);
    }

    public void produce() throws InterruptedException {
        int amount = random.nextInt(maxPortion) + 1;
        CompletionFuture future = proxy.produce(amount);
        task.run(); // zadanie do wykonania w trakcie
        future.waitUntilCompletion();
        accessCounter++;
    }

    @Override
    public void run() {
        while (!interrupted()){
            try {
                produce();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
