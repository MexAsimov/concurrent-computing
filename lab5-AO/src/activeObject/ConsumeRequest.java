package activeObject;

public class ConsumeRequest extends  MethodRequest{
    protected ConsumeRequest(Servant servant, int amount, CompletionFuture future) {
        super(servant, amount, future);
    }

    @Override
    protected boolean guard() {
        return servant.elementsInBuffer() >= amount;
    }

    @Override
    protected void call() {
        servant.consume(amount, future);
    }
}
