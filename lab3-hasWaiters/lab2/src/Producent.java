public class Producent extends Thread{
    private Monitor buffer;
    public int id;
    private int counter = 0;
    private boolean heavy;

    public Producent(Monitor monitor, int id){
        this.buffer = monitor;
        this.id = id;
    }

    public Producent(Monitor monitor, int id, boolean heavy){
        this.buffer = monitor;
        this.id = id;
        this.heavy = heavy;
    }

    public void run(){
        while(true){
            int portion;
            if(!heavy) {
                portion = 1 + (int) (Math.random() * (this.buffer.maxPortionSize - 1));
            }
            else {
                portion = this.buffer.maxPortionSize;
            }
            this.buffer.produce(portion);
            counter++;
        }
    }

    public void showCounter(){
        System.out.println("[+" + ((this.heavy) ? "H" : "") + id + "+] - " + counter);
    }
}

