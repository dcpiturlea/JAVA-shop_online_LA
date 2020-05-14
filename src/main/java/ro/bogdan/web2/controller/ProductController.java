package ro.bogdan.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.bogdan.web2.database.Product;
import ro.bogdan.web2.database.ProductDAO;

@Controller
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product");

        Product product = productDAO.findById(id);
        modelAndView.addObject("product", product);

        return modelAndView;
    }

}
