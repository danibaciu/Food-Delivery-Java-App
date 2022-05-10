package pao.Components;

public abstract class Entity<T, A> extends IOStream<A> {
    protected T id;

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
