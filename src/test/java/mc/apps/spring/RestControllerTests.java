package mc.apps.spring;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import mc.apps.spring.db.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RestControllerTests {
    private static final String API_ROOT = "http://localhost:8081/api/todos";

    @Test
    public void whenGetAllTodos_thenOK() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        System.out.println("*******************************");
        System.out.println("Response : "+response.getBody().asString());
        System.out.println("*******************************");
    }

    @Test
    public void whenCreateNewTodo_thenCreated() {
        Todo todo = new Todo(0,"Hello from Spring",true);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(todo)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        System.out.println("*******************************");
        System.out.println("Response : "+response.getBody().asString());
        System.out.println("*******************************");
    }
}
