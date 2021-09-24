package mc.apps.spring.db;
import javax.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "boolean default false")
    private Boolean done;

    public Todo(long id, String title, Boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }
    public Todo(String title) {
        this.title = title;
    }

    public Todo() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
