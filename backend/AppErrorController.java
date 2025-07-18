import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error() {
        return "/";
    }
}
