package activeObject;

public class ProduceRequest extends MethodRequest{
    protected ProduceRequest(Servant servant, int amount, CompletionFuture future) {
        super(servant, amount, future);
    }

    @Override
    protected boolean guard() {
        return servant.freeSpace() >= amount;
    }

    @Override
    protected void call() {
        servant.produce(amount, future);
    }
}
