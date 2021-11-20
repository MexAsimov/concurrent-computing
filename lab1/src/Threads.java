import java.util.ArrayList;

public class Threads extends Thread {
    public static int amount = 0;
    public static int noThreads = 15;
    public static int val = 10;
    public static void main(String[] args) throws InterruptedException {

        ArrayList<Threads> threads = new ArrayList<Threads>();

        for(int i=0; i<noThreads; i++){
            threads.add(new Threads());
            threads.get(i).start();
        }

       for(int i=0; i<noThreads; i++){
          threads.get(i).join();
       }
        System.out.println(amount);
    }

    public void run(){
        for(int i=0; i<val; i++) {
            try {
                increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                decrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void increment() throws InterruptedException {
        amount++;
        Thread.sleep(100);
    }

    public void decrement() throws InterruptedException {
        amount--;
        Thread.sleep(100);
    }
}
