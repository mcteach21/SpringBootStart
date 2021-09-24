package mc.apps.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import mc.apps.spring.db.Todo;
import mc.apps.spring.db.TodoRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    TodoRepository todoRepository;

    static Long cpt=1L;

    private Todo createRandomTodo() {
        return new Todo(cpt++, RandomStringUtils.randomAlphabetic(15), false);
    }

    /**
     * Tests Unitaires
     */
    List<Todo> todos = new ArrayList<>(Arrays.asList(
            createRandomTodo(), createRandomTodo(), createRandomTodo())
    );
    @Test
    public void getAllTodos_success() throws Exception {

        // todoRepository "mocké"!
        // lors du test, tout appel à la méthode findAll() retournera todos
        // sans que la base ne soit réellement interrogée
        Mockito.when(todoRepository.findAll()).thenReturn(todos);


        // MockMvc (module de SpringTest) permet de simplifier la création de tests Rest
        // SANS dépendance explicite à un conteneur web.
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
                //.andExpect(jsonPath("$[0].id", is(1)));
    }
    @Test
    public void getTodoById_success() throws Exception {
        Todo todo = todos.get(0);
        Mockito.when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/todos/"+todo.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(todo.getTitle())));
    }

    @Test
    public void createTodo_success() throws Exception {
        Todo todo = new Todo("Spring!");

        Mockito.when(todoRepository.save(todo)).thenReturn(todo);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(todo));

        //MvcResult result =
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$", notNullValue()))
                .andDo(print());
                //.andReturn();
    }

}
