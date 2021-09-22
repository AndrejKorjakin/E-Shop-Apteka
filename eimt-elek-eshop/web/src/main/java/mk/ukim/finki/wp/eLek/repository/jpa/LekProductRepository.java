package mk.ukim.finki.wp.eLek.repository.jpa;

import mk.ukim.finki.wp.eLek.model.LekProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LekProductRepository extends JpaRepository<LekProduct, Long> {
    Optional<LekProduct> findByName(String name);
    void deleteByName(String name);
}
