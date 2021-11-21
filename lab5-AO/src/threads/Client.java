package threads;

import activeObject.Proxy;
import activeObject.Task;

import javax.swing.text.TabableView;
import java.util.Random;

public abstract class Client extends Thread{
    protected final int id;
    protected final Proxy proxy;
    protected final int maxPortion;
    protected final Random random;
    protected int accessCounter;
    protected Task task;

    public Client(int id, Proxy proxy, int maxPortion, Task task) {
        this.id = id;
        this.proxy = proxy;
        this.maxPortion = maxPortion;
        this.random = new Random(id);
        this.task = task;
    }

    public int getAccessCounter() {
        return accessCounter;
    }


}
