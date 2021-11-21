// Fastest crush
// CCPPCC(zwiecha) - finalnie dwóch konsumentów na wierzchu, a producent w kolejce czeka na notify

public class Main {
    public static void main(String[] args){
        Monitor monitor = new Monitor();
        Thread cons = new Konsument(monitor);
        Thread cons2 = new Konsument(monitor);
        Thread prod = new Producent(monitor);
        cons.start();
        cons2.start();
        prod.start();
    }
}
