package ro.bogdan.web2.controller;

import ro.bogdan.web2.database.Product;

public class CartProduct extends Product {

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
