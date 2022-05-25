package ee.mihkel.webshop.model.request.output;

import lombok.Data;

// https://www.site24x7.com/tools/json-to-java.html
@Data // getterid & setterid & no args constructori
public class EveryPayData {
    private String api_username;
    private String account_name;
    private double amount;
    private String order_reference;
    private String nonce;
    private String timestamp;
    private String customer_url;
}
