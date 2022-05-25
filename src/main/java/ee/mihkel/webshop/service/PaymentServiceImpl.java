package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.database.Order;
import ee.mihkel.webshop.model.database.PaymentState;
import ee.mihkel.webshop.model.request.input.EveryPayCheckPaymentResponse;
import ee.mihkel.webshop.model.request.input.EveryPayResponse;
import ee.mihkel.webshop.model.request.output.EveryPayData;
import ee.mihkel.webshop.model.request.output.EveryPayUrl;
import ee.mihkel.webshop.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Value("${everypay.baseurl}")
    String everyPayBaseUrl;

    @Value("${everypay.credentials}")
    String credentials;

    @Value("${everypay.username}")
    String username;

    @Value("${everypay.account}")
    String account;

    @Value("${everypay.customerurl}")
    String customerUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderRepository orderRepository;

    public EveryPayUrl getPaymentLink(double amount, Long orderId) {
        // aktiveerisin koodibloki
        // ctrl + alt + m
        EveryPayData everyPayData = buildEveryPayData(amount, orderId);

        // urli tõstame application.properties sisse
        String url = everyPayBaseUrl + "/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + credentials);

        HttpEntity<EveryPayData> httpEntity = new HttpEntity<>(everyPayData, headers);

        EveryPayUrl everyPayUrl = new EveryPayUrl();
        // üks ja sama new koguaeg --> @Autowired
        //RestTemplate restTemplate = new RestTemplate(); // võimaldab teha HTTP päringuid
        ResponseEntity<EveryPayResponse> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity, EveryPayResponse.class);
        if (response.getStatusCodeValue() == 201 && response.getBody() != null) {
            everyPayUrl.setUrl(response.getBody().getPayment_link());
        }

        return everyPayUrl;
    }

    private EveryPayData buildEveryPayData(double amount, Long orderId) {
        // castimine
//        long longNumber = 10L;
//        int intNumber = (int) longNumber;
        EveryPayData everyPayData = new EveryPayData();
        everyPayData.setApi_username(username);
        everyPayData.setAccount_name(account);
        everyPayData.setAmount(amount);
        everyPayData.setOrder_reference(orderId.toString());
        everyPayData.setNonce("ad" + Math.random() + new Date());
        everyPayData.setTimestamp(ZonedDateTime.now().toString());
        System.out.println(new Date().toString()); // log4j2
        log.info(new Date().toString());
        log.error("ERROR!!");
        log.debug("adasdasd");
        everyPayData.setCustomer_url(customerUrl); // serverisse üles heroku --
        // java ja front-end (Angular/React)
        return everyPayData;
    }

    public Boolean checkIfOrderPaid(Long orderId, String paymentRef) {

        // restTemplate välised HTTP päringud   --->  autowired (üks mälukoht)
        // 1. URL    2. GET/POST    3.   Body + Headers

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + credentials);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<EveryPayCheckPaymentResponse> response = restTemplate.exchange(everyPayBaseUrl + "/payments/" + paymentRef + "?" + username,
                HttpMethod.GET,httpEntity, EveryPayCheckPaymentResponse.class);

        if (response.getStatusCodeValue() == 200 &&  response.getBody() != null) {
            String paymentState = response.getBody().getPayment_state();
            Order order = orderRepository.findById(orderId).get();
            // switch --> if else
            // switch võimaldab kontrollida ühte muutujat erinevate väärtuste vastu
            switch (paymentState) {
                case "failed":
                    order.setPaymentState(PaymentState.FAILED);
                    return false;
                case "voided":
                    order.setPaymentState(PaymentState.VOIDED);
                    return false;
                case "abandoned":
                    order.setPaymentState(PaymentState.ABANDONED);
                    return true;
                case "settled":
                    order.setPaymentState(PaymentState.SETTLED);
                    return true;
            }
            orderRepository.save(order);
            // 1. tekitame Orderile Enum-i: paymentState
            // 2. kui läheme maksma läheb andmebaasi paymentState - Initial
            // 3. otsime andmebaasist ID alusel -- orderId
            // 4. SIIN - muudame selle Initiali ära ja paneme asemele "failed"   /   "abandoned"  / "settled
            // TODO: cron job
            // 5. HILJEM - cron job otsib kõik Initialid ülesse ja uuesti läheb SIIA funktsiooni

        }

        return false;
    }
}
