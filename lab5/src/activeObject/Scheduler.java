package activeObject;

public class Scheduler extends Thread{
    protected ActivationQueue queue;

    protected Scheduler(){
        queue = new ActivationQueue();
    }

    protected void enqueue(MethodRequest request){
        queue.enqueue(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                MethodRequest methodRequest = queue.dequeue();
                methodRequest.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
