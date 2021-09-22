package mk.ukim.finki.wp.eLek.web.controller;

import mk.ukim.finki.wp.eLek.model.Category;
import mk.ukim.finki.wp.eLek.model.Manufacturer;
import mk.ukim.finki.wp.eLek.model.LekProduct;
import mk.ukim.finki.wp.eLek.service.CategoryService;
import mk.ukim.finki.wp.eLek.service.LekProductService;
import mk.ukim.finki.wp.eLek.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lekovi")
public class LekProductController {

    private final LekProductService lekProductService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public LekProductController(LekProductService lekProductService,
                                CategoryService categoryService,
                                ManufacturerService manufacturerService) {
        this.lekProductService = lekProductService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<LekProduct> lekProducts = this.lekProductService.findAll();
        model.addAttribute("products", lekProducts);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.lekProductService.deleteById(id);
        return "redirect:/lekovi";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.lekProductService.findById(id).isPresent()) {
            LekProduct lekProduct = this.lekProductService.findById(id).get();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("categories", categories);
            model.addAttribute("product", lekProduct);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/lekovi?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam Long category,
            @RequestParam Long manufacturer) {
        if (id != null) {
            this.lekProductService.edit(id, name, price, quantity, category, manufacturer);
        } else {
            this.lekProductService.save(name, price, quantity, category, manufacturer);
        }
        return "redirect:/lekovi";
    }
}
