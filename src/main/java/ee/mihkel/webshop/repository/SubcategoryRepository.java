package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
