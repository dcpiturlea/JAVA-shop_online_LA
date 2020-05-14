package ro.bogdan.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.bogdan.web2.database.InvalidPassword;
import ro.bogdan.web2.database.Product;
import ro.bogdan.web2.database.ProductDAO;
import ro.bogdan.web2.database.User;
import ro.bogdan.web2.database.UserService;
import ro.bogdan.web2.security.UserSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    UserSession userSession;

    //CE TREBUIE SA FACEM AICI???
    // - daca parolele sunt identice
    @GetMapping("/register-form")
    public ModelAndView registerAction(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("password-again") String password2) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (!password.equals(password2)) {
            modelAndView.addObject("message", "Parole nu sunt identice!");
            return modelAndView;
        } else {
            //salvare efectiva in baza de date !!!
            try {
                userService.save(email, password);
            } catch (InvalidPassword invalidPassword) {
                String messageException = invalidPassword.getMessage();
                modelAndView.addObject("message", messageException);
                return modelAndView;
            }
        }

        //redirectionam user-ul catre pagina index.html daca este totul ok
        return new ModelAndView("redirect:/index.html");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<User> userList = userService.findByEmail(email);
        if (userList.size() == 0) {
            modelAndView.addObject("message", "Credentialele nu sunt corecte!");
        }
        if (userList.size() > 1) {
            modelAndView.addObject("message", "Credentialele nu sunt corecte!");
        }
        if (userList.size() == 1) {
            User userFromDatabase = userList.get(0);
            if (!userFromDatabase.getPassword().equals(password)) {
                modelAndView.addObject("message", "Credentialele nu sunt corecte!");
            } else {
                userSession.setUserId(userFromDatabase.getId());
                modelAndView = new ModelAndView("redirect:/dashboard");
            }
        }
        return modelAndView;
    }

    @GetMapping("dashboard")
    public ModelAndView dashboard() {

        List<Product> products = productDAO.findAll();
        for(Product p: products) {
            p.setUrl("product?id=" + p.getId());
        }

        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("products", products);

        return modelAndView;
    }
}
