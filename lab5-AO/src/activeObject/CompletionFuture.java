package activeObject;

import java.util.concurrent.locks.Condition;

public class CompletionFuture {
    private boolean operationCompleted;

    protected CompletionFuture() {
        this.operationCompleted = false;
    }

    protected synchronized void complete(){
        operationCompleted = true;
        notify();
    }

    public synchronized void waitUntilCompletion() throws InterruptedException{
        while (!operationCompleted)
            wait();
    }

    public synchronized boolean isCompleted(){
        return operationCompleted;
    }
}
