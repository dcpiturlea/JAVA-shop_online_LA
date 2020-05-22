package ro.bogdan.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView removeProduct(@RequestParam("id") Integer productId) {
        //stergem produsul din baza de date
        productDAO.deleteProduct(productId);
        return new ModelAndView("redirect:/admin/products");
    }
}
