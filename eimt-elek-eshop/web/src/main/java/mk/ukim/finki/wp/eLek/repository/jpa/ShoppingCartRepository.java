package mk.ukim.finki.wp.eLek.repository.jpa;

import mk.ukim.finki.wp.eLek.model.ShoppingCart;
import mk.ukim.finki.wp.eLek.model.User;
import mk.ukim.finki.wp.eLek.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
