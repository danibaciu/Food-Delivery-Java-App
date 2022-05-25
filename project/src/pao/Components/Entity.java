package pao.components;

public abstract class Entity<T> extends IOStream {
    protected T id;

    public Entity() {

    }

    public Entity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
