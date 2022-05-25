package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    //List<Product> products = new ArrayList<>();

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCache productCache;

    @GetMapping("products") // localhost:8080/products
    public ResponseEntity<List<Product>>  getProducts() {
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @PostMapping("products")
    public ResponseEntity<List<Product>> addProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        productCache.emptyCache();
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ExecutionException {
        return ResponseEntity.ok()
                .body(productCache.getProduct(id));
//        return productCache.getProduct(id);
//        return productRepository.findById(id).get();
    }

    @PutMapping("products")
    public ResponseEntity<List<Product>> editProduct(@RequestBody Product product) {
        productRepository.save(product);
        productCache.emptyCache();
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @DeleteMapping("delete-all-products")
    public ResponseEntity<String> deleteAllProduct() {
        productRepository.flush();
        productCache.emptyCache();
        return ResponseEntity.ok()
                .body("Kõik tooted kustutatud");
    }

    @PostMapping("increase-stock")
    public ResponseEntity<List<Product>> increaseStock(@RequestBody Product product) throws ExecutionException {
        Product updatedProduct = productCache.getProduct(product.getId());
        int productStock = updatedProduct.getStock();
        updatedProduct.setStock(++productStock);
        productRepository.save(updatedProduct);
        productCache.updateCache(updatedProduct);
//        productCache.emptyCache();
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @PostMapping("decrease-stock")
    public ResponseEntity<List<Product>> decreaseStock(@RequestBody Product product) throws ExecutionException {
        Product updatedProduct = productCache.getProduct(product.getId());
        int productStock = updatedProduct.getStock();
        // TODO: ERRORi kuvamine
        if (productStock > 0) {
            updatedProduct.setStock(--productStock);
            productRepository.save(updatedProduct);
            productCache.updateCache(updatedProduct);
//        productCache.emptyCache();
        }
        return ResponseEntity.ok()
                .body(productRepository.getAllByOrderByIdAsc());
    }

    @GetMapping("positive-stock-products") // localhost:8080/products
    public ResponseEntity<List<Product>>  getPositiveStockProducts() {
        return ResponseEntity.ok().body(productRepository
                .getAllByStockGreaterThanOrderByIdAsc(0));
    }

    @GetMapping("active-products") // localhost:8080/products
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok().body(productRepository
                .getAllByStockGreaterThanAndActiveEqualsOrderByIdAsc(0, true));
    }
}
