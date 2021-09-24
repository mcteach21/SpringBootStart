package mc.apps.spring.db;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo,Long> {
    Iterable<Todo> findByTitle(String title);

}
