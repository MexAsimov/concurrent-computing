public class Monitor{
    public int buffer = 0;
    private int pc = 1;
    public Monitor(){

    }

    public synchronized void produce() {
        while(buffer == 1){
            System.out.println("Waiting for slot..");
            try {
                wait();
            }
            catch(Exception e) {

            }
        }
        System.out.println("___ Operation nr " + pc++ + " ___");
        System.out.println("producing..");
        buffer++;
        notify();
    }

    public synchronized void consume(){
        while(buffer == 0){
            System.out.println("Waiting for product..");
            try {
                wait();
            }
            catch(Exception e){

            }
        }
        System.out.println("___ Operation nr " + pc++ + " ___");
        System.out.println("consuming..");
        buffer--;
        notify();
    }
}
