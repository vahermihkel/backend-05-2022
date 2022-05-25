package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.model.request.input.CartProduct;

import java.util.List;

public interface OrderService {

    double calculateOrderSum(List<Product> products);

    Long saveToDatabase(List<Product> products, double orderSum, Person person);

    List<Product> getAllProductsFromDb(List<Product> products);
}
