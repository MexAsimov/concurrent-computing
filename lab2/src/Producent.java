public class Producent extends Thread{
    private Monitor buffer;

    public Producent(Monitor monitor){
        this.buffer = monitor;
    }
    public void run(){
        while(true){
            this.buffer.produce();
        }
    }
}
