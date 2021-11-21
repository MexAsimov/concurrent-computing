public class Konsument extends Thread{
    public Monitor buffer;

    public Konsument(Monitor monitor) {
        this.buffer = monitor;
    }
    public void run(){
        while(true){
            this.buffer.consume();
        }

    }
}
