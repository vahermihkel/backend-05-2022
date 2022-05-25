package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.model.database.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Person getByEmail(String email);
}
