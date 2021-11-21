// Fastest crush
// CCPPCC(zwiecha) - finalnie dwóch konsumentów na wierzchu, a producent w kolejce czeka na notify


import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 10;
        int maxPortionSize = 5;
        Monitor monitor = new Monitor(bufferSize, maxPortionSize);
        int producerNum = 5;
        int consumerNum = 5;
        int timeslot = 200;


        List<Producent> producers = new LinkedList<>();
        List<Konsument> consumers = new LinkedList<>();
        // consuments starts..
        int i=0;
        for(; i < consumerNum; i++){
            Konsument cons = new Konsument(monitor, i);
            consumers.add(cons);
            cons.start();
        }
        // producer starts..
        for(; i < consumerNum+producerNum; i++){
            Producent prod = new Producent(monitor, i);
            producers.add(prod);
            prod.start();
        }
        Producent prod1 = new Producent(monitor, i, true);
        producers.add(prod1);
        prod1.start();

        System.out.println("Wydruk pokazuje ilość wykonanych operacji przez dany wątek co " +
                +timeslot + "ms: ");
        System.out.println("==================");
        while (true) {
            System.out.println("==============");
            for (Konsument cons : consumers) {
                cons.showCounter();
            }
            for (Producent prod : producers) {
                prod.showCounter();
            }
            Thread.sleep(200);
        }
    }
}