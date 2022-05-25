package ee.mihkel.webshop.model.request.input;

import ee.mihkel.webshop.model.database.Product;
import lombok.Data;

@Data
public class CartProduct {
    private Product product;
    private int quantity;
}
