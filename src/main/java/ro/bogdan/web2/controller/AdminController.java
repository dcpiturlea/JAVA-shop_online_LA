package ro.bogdan.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ro.bogdan.web2.database.Product;
import ro.bogdan.web2.database.ProductDAO;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/admin/products")
    public ModelAndView viewProducts() {
        ModelAndView modelAndView = new ModelAndView("admin/products");
        List<Product> products = productDAO.findAll();

        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @PostMapping("/admin/remove-product")
    @ResponseBody
    public String removeProduct(@RequestParam("id") Integer productId) {
        //stergem produsul din baza de date
        productDAO.deleteProduct(productId);
        return "ok";
    }

    @PostMapping("/admin/save-product")
    @ResponseBody
    public String saveProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") Double price) {
        productDAO.saveProduct(name, description, price);
        return "ok";
    }
}
