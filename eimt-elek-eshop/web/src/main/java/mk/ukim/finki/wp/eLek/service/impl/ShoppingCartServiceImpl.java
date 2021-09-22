package mk.ukim.finki.wp.eLek.service.impl;

import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.ShoppingCart;
import mk.ukim.finki.wp.eLek.model.User;
import mk.ukim.finki.wp.eLek.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.wp.eLek.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.wp.eLek.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp.eLek.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wp.eLek.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.eLek.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp.eLek.repository.jpa.UserRepository;
import mk.ukim.finki.wp.eLek.service.LekProductService;
import mk.ukim.finki.wp.eLek.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final LekProductService lekProductService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   LekProductService lekProductService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.lekProductService = lekProductService;
    }

    @Override
    public List<LekProduct> listAllProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getLekProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        LekProduct lekProduct = this.lekProductService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getLekProducts()
                .stream().filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getLekProducts().add(lekProduct);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
