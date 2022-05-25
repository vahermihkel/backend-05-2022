package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.request.output.EveryPayUrl;

public interface PaymentService {

    EveryPayUrl getPaymentLink(double amount, Long orderId);

    Boolean checkIfOrderPaid(Long orderId, String paymentRef);
}

