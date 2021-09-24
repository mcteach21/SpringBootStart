package mc.apps.spring;

import mc.apps.spring.db.Todo;
import mc.apps.spring.db.TodoRepository;
import mc.apps.spring.exceptions.TodoIdMismatchException;
import mc.apps.spring.exceptions.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoRepository TodoRepository;

    @GetMapping
    public Iterable findAll() {
        return TodoRepository.findAll();
    }

    @GetMapping("/title/{TodoTitle}")
    public List findByTitle(@PathVariable String TodoTitle) {
        return (List) TodoRepository.findByTitle(TodoTitle);
    }

    @GetMapping("/{id}")
    public Todo findOne(@PathVariable Long id) throws TodoNotFoundException {
        return TodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody @Valid Todo todo) {
        return TodoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws TodoNotFoundException {
        TodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        TodoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@RequestBody Todo Todo, @PathVariable Long id) throws TodoIdMismatchException, TodoNotFoundException {
        if (Todo.getId() != id) {
            throw new TodoIdMismatchException();
        }
        TodoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        return TodoRepository.save(Todo);
    }
    
}
