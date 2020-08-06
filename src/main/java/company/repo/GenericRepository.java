package company.repo;



import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<T,ID> {

    T create(T t);

    T update(T t);

    void delete(ID id);

    Optional<T>  read(ID id);


    Collection<T> getAll();
}
