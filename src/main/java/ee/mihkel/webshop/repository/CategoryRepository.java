package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Category;
import ee.mihkel.webshop.model.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
