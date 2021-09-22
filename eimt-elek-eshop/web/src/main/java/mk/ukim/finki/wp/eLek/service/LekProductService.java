package mk.ukim.finki.wp.eLek.service;

import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface LekProductService {

    List<LekProduct> findAll();

    Optional<LekProduct> findById(Long id);

    Optional<LekProduct> findByName(String name);

    Optional<LekProduct> save(String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<LekProduct> save(ProductDto productDto);

    Optional<LekProduct> edit(Long id, String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<LekProduct> edit(Long id, ProductDto productDto);

    void deleteById(Long id);
}
