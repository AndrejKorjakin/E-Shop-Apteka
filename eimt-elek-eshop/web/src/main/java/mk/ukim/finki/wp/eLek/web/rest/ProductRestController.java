package mk.ukim.finki.wp.eLek.web.rest;

import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.model.dto.ProductDto;
import mk.ukim.finki.wp.eLek.service.LekProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/lekovi")
public class ProductRestController {

    private final LekProductService lekProductService;

    public ProductRestController(LekProductService lekProductService) {
        this.lekProductService = lekProductService;
    }

    @GetMapping
    private List<LekProduct> findAll() {
        return this.lekProductService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LekProduct> findById(@PathVariable Long id) {
        return this.lekProductService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<LekProduct> save(@RequestBody ProductDto productDto) {
        return this.lekProductService.save(productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<LekProduct> save(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return this.lekProductService.edit(id, productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.lekProductService.deleteById(id);
        if(this.lekProductService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
