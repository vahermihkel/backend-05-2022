package ee.mihkel.webshop.model.request.input;

import lombok.Data;

@Data
public class EveryPayCheckPaymentResponse {
    private String account_name;
    private String order_reference;
    private String email;
    private String customer_ip;
    private String customer_url;
    private String payment_created_at;
    private float initial_amount;
    private float standing_amount;
    private String payment_reference;
    private String payment_link;
    private String api_username;
    Processing_error Processing_errorObject;
    private String stan;
    private String fraud_score;
    private String payment_state;
    private String payment_method;
    Ob_details Ob_detailsObject;
    private String transaction_time;
    private String acquiring_completed_at;
}

@Data
class Processing_error {
    private float code;
    private String message;
}

@Data
class Ob_details {
    private String debtor_iban;
    private String creditor_iban;
    private String ob_payment_reference;
    private String ob_payment_state;
}
