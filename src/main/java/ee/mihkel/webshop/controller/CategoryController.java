package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.database.Subcategory;
import ee.mihkel.webshop.repository.CategoryRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import ee.mihkel.webshop.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @GetMapping("category") // localhost:8080/category
    public ResponseEntity<List<Category>>  getCategories() {
        return ResponseEntity.ok()
                .body(categoryRepository.findAll());
    }

    @GetMapping("sub-category") // localhost:8080/category
    public ResponseEntity<List<Subcategory>>  getSubcategories() {
        return ResponseEntity.ok()
                .body(subcategoryRepository.findAll());
    }

    @PostMapping("category")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryRepository.findAll());
    }

    @PostMapping("sub-category") // localhost:8080/sub-category
    public ResponseEntity<List<Subcategory>> addSubcategory(@RequestBody Subcategory subcategory) {
        subcategoryRepository.save(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subcategoryRepository.findAll());
    }

}
