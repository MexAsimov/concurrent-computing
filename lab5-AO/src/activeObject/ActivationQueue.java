package activeObject;

import java.util.LinkedList;

public class ActivationQueue {
    protected LinkedList<MethodRequest> priorityQueue;
    protected LinkedList<MethodRequest> queue;

    protected ActivationQueue(){
        queue = new LinkedList<>();
        priorityQueue = new LinkedList<>();
    }

    protected synchronized void enqueue(MethodRequest methodRequest){
        queue.add(methodRequest);
        notify();
    }

    protected synchronized MethodRequest dequeue() throws InterruptedException {
        // jezeli w kolejce o wysokim priorytecie sa elementy
        if (!priorityQueue.isEmpty()){
            //jezeli wystarczy zasobu dla pierwszego elementu kolejki, zwracamy go
            MethodRequest priorityRequest = priorityQueue.peek();
            if(priorityRequest.guard())
                return priorityQueue.poll();

            // jezeli nie ma, to wszystkie requesty o tej samej klasie z początku kolejki o nizszym priorytecie
            // sa przenoszone na koniec kolejki o wyzszym priorytecie
            Class<? extends MethodRequest> priorityQueueClass = priorityRequest.getClass();

            while (!queue.isEmpty() && priorityQueueClass.isInstance(queue.peek()))
                priorityQueue.add(queue.poll());

            // jezeli kolejka o nizszym priorytecie jest pusta powies scheduler
            while (queue.isEmpty()){
                wait();

                // wszystkie requesty o tej samej klasie co request w kolejce priorytetowej
                // sa przenoszone z początku kolejki o nizszym priorytecie na koniec kolejki o wyzszym priorytecie
                while (!queue.isEmpty() && priorityQueueClass.isInstance(queue.peek()))
                    priorityQueue.add(queue.poll());
            }

            // zwracamy pierwszy element kolejki o nizszym priorytecie innego typu niz te z kolejki priorytetowej
            return queue.poll();
        }

        // jezeli kolejka o wysokim priorytecie jest pusta

        // jezeli kolejka o nizszym prioytecie jest takze pusta
        while (queue.isEmpty())
            wait();

        //jezeli nie ma zasobu dla pierwszego requestu umieszczamy go w kolejce priorytetowej, wpp zwracamy go
        if (!queue.peek().guard()){
            priorityQueue.add(queue.poll());
            return dequeue();
        }
        return queue.poll();
    }

}
