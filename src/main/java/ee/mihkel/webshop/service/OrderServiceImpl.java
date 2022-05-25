package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.PaymentState;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {


    // JÄRGMINE KORD:
    // 1. Backendis Order salvestada koos isikuga --
    // 2. Tagastada kõik orderid vastavalt kes küsib --
    // 3. Valideerimised backis
    // 4. Frontend käsitleb erroreid
    // 5. Frondis lisades tulevad kategooriad dropdownist

    // Frontendis kui on 403 - siis ütlen et pole sobivaid õigusi


    // KODUS:
    // 1. Frontendis Kategooria - lisada, kustutada, vaadata
    // 2. Frontendis Orderid salvestada --
    // 3. Frontendis selle kasutaja ordereid vaadata --

    // Alates E
    // Erinevaid rolle
    // SMS-de saatmist
    // E-mailide saatmist
    // UNIT testimist
    // Heroku üles - serverisse nii back kui front

    // ????

    // Proovitööd üle

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCache productCache;

    public double calculateOrderSum(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice) // map (asendab) kõik tooted hinnaga (double kujul)
                .sum();

//            double sum = 0;
//            for (Product p: products) {
//                sum += p.getPrice();
//            }
//            return sum;
    }

    public Long saveToDatabase(List<Product> products, double orderSum, Person person) {
        Order order = new Order();
        order.setOrderSum(orderSum);
        order.setCreationDate(new Date());
        order.setProducts(products);
        order.setPaymentState(PaymentState.INITIAL);
        order.setPerson(person);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    public List<Product> getAllProductsFromDb(List<Product> products) {
//        List<Product> originalProducts = new ArrayList<>();
//        for (Product p: products) {
//            Long productId = p.getId();
//            Product originalProduct = productRepository.findById(productId).get();
//            originalProducts.add(originalProduct);
//        }
//        return originalProducts;

        return products.stream()
                .map(p -> {
                    try {
                        return productCache.getProduct(p.getId());
                    } catch (ExecutionException e) {
                        log.error("Cache error {}", e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
