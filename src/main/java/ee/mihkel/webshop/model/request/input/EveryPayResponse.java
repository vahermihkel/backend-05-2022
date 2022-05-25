package ee.mihkel.webshop.model.request.input;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EveryPayResponse {
    private String account_name;
    private String order_reference;
    private String email;
    private String customer_ip;
    private String customer_url;
    private String payment_created_at;
    private double initial_amount;
    private double standing_amount;
    private String payment_reference;
    private String payment_link;
    ArrayList<PaymentMethod> payment_methods = new ArrayList<>();
    private String api_username;
    private String stan;
    private String fraud_score;
    private String payment_state;
    private String payment_method;
}

@Data
class PaymentMethod {
    private String source;
    private String display_name;
    private String country_code;
    private String payment_link;
    private String logo_url;
}
