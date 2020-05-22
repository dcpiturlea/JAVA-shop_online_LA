package ro.bogdan.web2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.bogdan.web2.database.OrderDao;
import ro.bogdan.web2.database.Product;
import ro.bogdan.web2.database.ProductDAO;
import ro.bogdan.web2.database.UserService;
import ro.bogdan.web2.security.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    UserSession userSession;

    @Autowired
    OrderDao orderDao;

    @GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product");

        Product product = productDAO.findById(id);
        modelAndView.addObject("product", product);
        int productCounter = 0;
        for(int quantityForProduct: userSession.getShoppingCart().values()) {
            productCounter = productCounter + quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize", productCounter);

        return modelAndView;
    }

    @PostMapping("/add-to-cart")
    public ModelAndView addToCart(@RequestParam("productId") Integer id) {
        //server-ul trebuie sa tina minte id-urile de produse pe care userul le are in comanda
        userSession.addNewProduct(id);

        return new ModelAndView("redirect:product?id=" + id);
    }

    @GetMapping("shopping-cart")
    public ModelAndView shoppingCart() {

        ModelAndView modelAndView = new ModelAndView("cart");
        List<CartProduct> productsFromCart = new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry: userSession.getShoppingCart().entrySet()) {
            int quantity = entry.getValue();
            int productId = entry.getKey();
            Product productFromDatabase = productDAO.findById(productId);
            CartProduct cartProduct = new CartProduct();
            cartProduct.setQuantity(quantity);
            cartProduct.setId(productFromDatabase.getId());
            cartProduct.setName(productFromDatabase.getName());
            cartProduct.setPhotoFile(productFromDatabase.getPhotoFile());
            cartProduct.setPrice(productFromDatabase.getPrice());

            productsFromCart.add(cartProduct);
        }
        modelAndView.addObject("products", productsFromCart);

        return modelAndView;
    }

    @GetMapping("save-cart")
    public ModelAndView saveCart() {
        //sa salvam in baza de date cosul de cumparaturi
        orderDao.newOrder(userSession.getUserId(), userSession.getShoppingCart());

        //trebuie sa curatam cosul de cumparaturi
        userSession.getShoppingCart().clear();

        //TODO
        //trebuie sa curatam cosul de cumparaturi
        return new ModelAndView("redirect:dashboard");
    }

}
