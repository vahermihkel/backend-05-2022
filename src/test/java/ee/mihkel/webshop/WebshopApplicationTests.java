package ee.mihkel.webshop;

import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.database.Subcategory;
import ee.mihkel.webshop.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WebshopApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void checkIfCanGetProducts() {
        List<Product> products = productRepository.findAll();
        Assertions.assertTrue(products.size() > 0);
    }

    @Test
    void checkIfProductAddedIsInRepository() {
        Product addedProduct = new Product(99L,"Nimi", 10.00, null, true,
                null, 10, null);
        Product savedProduct = productRepository.save(addedProduct);
        Product fetchedProduct = productRepository.findById(savedProduct.getId()).get();
        Assertions.assertEquals(addedProduct.getName(), fetchedProduct.getName());
    }
}
