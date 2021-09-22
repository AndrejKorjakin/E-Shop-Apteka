package mk.ukim.finki.wp.eLek.service.impl;

import mk.ukim.finki.wp.eLek.model.Category;
import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.dto.ProductDto;
import mk.ukim.finki.wp.eLek.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.eLek.model.Manufacturer;
import mk.ukim.finki.wp.eLek.model.exceptions.ManufacturerNotFoundException;
import mk.ukim.finki.wp.eLek.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.wp.eLek.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp.eLek.repository.jpa.ManufacturerRepository;
import mk.ukim.finki.wp.eLek.repository.jpa.LekProductRepository;
import mk.ukim.finki.wp.eLek.service.LekProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LekProductServiceImpl implements LekProductService {

    private final LekProductRepository lekProductRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CategoryRepository categoryRepository;

    public LekProductServiceImpl(LekProductRepository lekProductRepository,
                                 ManufacturerRepository manufacturerRepository,
                                 CategoryRepository categoryRepository) {
        this.lekProductRepository = lekProductRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<LekProduct> findAll() {
        return this.lekProductRepository.findAll();
    }

    @Override
    public Optional<LekProduct> findById(Long id) {
        return this.lekProductRepository.findById(id);
    }

    @Override
    public Optional<LekProduct> findByName(String name) {
        return this.lekProductRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<LekProduct> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        this.lekProductRepository.deleteByName(name);
        return Optional.of(this.lekProductRepository.save(new LekProduct(name, price, quantity, category, manufacturer)));
    }

    @Override
    public Optional<LekProduct> save(ProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));

        this.lekProductRepository.deleteByName(productDto.getName());
        return Optional.of(this.lekProductRepository.save(new LekProduct(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category, manufacturer)));
    }

    @Override
    @Transactional
    public Optional<LekProduct> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {

        LekProduct lekProduct = this.lekProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        lekProduct.setName(name);
        lekProduct.setPrice(price);
        lekProduct.setQuantity(quantity);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        lekProduct.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        lekProduct.setManufacturer(manufacturer);

        return Optional.of(this.lekProductRepository.save(lekProduct));
    }

    @Override
    public Optional<LekProduct> edit(Long id, ProductDto productDto) {
        LekProduct lekProduct = this.lekProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        lekProduct.setName(productDto.getName());
        lekProduct.setPrice(productDto.getPrice());
        lekProduct.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        lekProduct.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));
        lekProduct.setManufacturer(manufacturer);

        return Optional.of(this.lekProductRepository.save(lekProduct));
    }

    @Override
    public void deleteById(Long id) {
        this.lekProductRepository.deleteById(id);
    }
}
