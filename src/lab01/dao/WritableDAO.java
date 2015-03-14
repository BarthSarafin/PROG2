package lab01.dao;

public interface WritableDAO<T> extends ReadOnlyDAO<T>
{
    public void insert(T item);
    public void update(T item);
    public void delete(T item);
}
