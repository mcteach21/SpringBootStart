package mc.apps.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    @Value("${welcome.message}") // injection propriété à partir 'application.properties'
    String message;

    @GetMapping("/") // mapping des requêtes Http GET
    public String index(Model model) {
        model.addAttribute("message", message); // envoi propriété à la vue
        return "welcome";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
