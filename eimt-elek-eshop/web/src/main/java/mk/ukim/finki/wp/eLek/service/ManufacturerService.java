package mk.ukim.finki.wp.eLek.service;

import mk.ukim.finki.wp.eLek.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    Optional<Manufacturer> findById(Long id);
    List<Manufacturer> findAll();
    Optional<Manufacturer> save(String name, String address);
    void deleteById(Long id);
}
