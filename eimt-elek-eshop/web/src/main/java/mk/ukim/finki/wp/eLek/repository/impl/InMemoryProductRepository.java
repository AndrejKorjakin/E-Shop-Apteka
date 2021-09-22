package mk.ukim.finki.wp.eLek.repository.impl;

import mk.ukim.finki.wp.eLek.bootstrap.DataHolder;
import mk.ukim.finki.wp.eLek.model.Category;
import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {

    public List<LekProduct> findAll() {
        return DataHolder.lekProducts;
    }

    public Optional<LekProduct> findById(Long id) {
        return DataHolder.lekProducts.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Optional<LekProduct> findByName(String name) {
        return DataHolder.lekProducts.stream().filter(i -> i.getName().equals(name)).findFirst();
    }

    public void deleteById(Long id) {
        DataHolder.lekProducts.removeIf(i -> i.getId().equals(id));
    }

    public Optional<LekProduct> save(String name, Double price, Integer quantity,
                                     Category category, Manufacturer manufacturer) {
        DataHolder.lekProducts.removeIf(i -> i.getName().equals(name));
        LekProduct lekProduct = new LekProduct(name, price, quantity, category, manufacturer);
        DataHolder.lekProducts.add(lekProduct);
        return Optional.of(lekProduct);
    }
}
