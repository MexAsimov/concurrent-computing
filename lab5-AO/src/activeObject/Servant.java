package activeObject;

public class Servant {
    protected int buffer;
    protected final int bufferCapacity;
    protected Task task;

    protected Servant(int bufferCapacity, Task task) {
        this.bufferCapacity = bufferCapacity;
        this.buffer = 0;
        this.task = task;
    }

    protected void produce(int amount, CompletionFuture future){
        buffer += amount;
        task.run(); // zadanie do wykonania w trakcie
        System.out.println("wyprodukowano " + amount + " el w buforze " + buffer);
        future.complete();
    }

    protected void consume(int amount, CompletionFuture future){
        buffer -= amount;
        task.run(); // zadanie do wykonania w trakcie
        System.out.println("skonsumowano " + amount + " el w buforze " + buffer);
        future.complete();
    }

    protected int freeSpace(){
        return bufferCapacity-buffer;
    }

    protected int elementsInBuffer(){
        return buffer;
    }

    protected boolean isEmpty(){
        return buffer == 0;
    }

    protected boolean isFull(){
        return buffer == bufferCapacity;
    }
}
