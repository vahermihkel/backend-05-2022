package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.database.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Log4j2
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    @GetMapping("orders") // localhost:8080/orders
    public ResponseEntity<List<Order>>  getOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Person person = personRepository.getByEmail(email);
        log.info("Getting orders from {}", person.getPersonCode());

        return ResponseEntity.ok()
                .body(orderRepository.getOrdersByPersonOrderByCreationDateDesc(person));
    }

}
