package ee.mihkel.webshop.model.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String imgSrc;
    private boolean active;
    private String description;
    private int stock;

    @OneToOne
    private Subcategory category;
}

// 1 Product
// 2 Product
// 3 Product

// 1 Order
// 2 Order

// 4 Product
