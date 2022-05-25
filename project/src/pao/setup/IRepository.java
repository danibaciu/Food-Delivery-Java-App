package pao.setup;

import pao.components.Entity;

import java.util.List;

public interface IRepository<T, E extends Entity<T>> {
    public void save(E elem);
    public void delete(E elem);
    public void update(E elem);
    public E findOne(T id);
    public List<E> findAll();
}
