package mk.ukim.finki.wp.eLek.service;

import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<LekProduct> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
}
