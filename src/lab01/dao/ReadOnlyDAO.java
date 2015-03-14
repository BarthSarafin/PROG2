package lab01.dao;

import java.util.*;

public interface ReadOnlyDAO<T> 
{
    public T findById(int id);
    public Collection<T> findAll();
    public int count();
}
