package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.request.output.ParcelMachines;

public interface ParcelMachineService {

    ParcelMachines getParcelMachines(String country);
}
