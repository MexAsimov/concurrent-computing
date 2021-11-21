package activeObject;

public abstract class MethodRequest {
    protected Servant servant;
    protected int amount;
    protected CompletionFuture future;

    protected MethodRequest(Servant servant, int amount, CompletionFuture future) {
        this.servant = servant;
        this.amount = amount;
        this.future = future;
    }

    protected abstract boolean guard();
    protected abstract void call();
}
