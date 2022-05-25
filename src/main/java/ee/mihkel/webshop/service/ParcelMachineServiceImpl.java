package ee.mihkel.webshop.service;

import ee.mihkel.webshop.model.request.input.OmnivaParcelMachine;
import ee.mihkel.webshop.model.request.input.SmartpostParcelMachine;
import ee.mihkel.webshop.model.request.output.ParcelMachines;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ParcelMachineServiceImpl implements ParcelMachineService {
    String omnivaUrl = "https://www.omniva.ee/locations.json";
    String smartpostUrl = "https://www.smartpost.ee/places.json";

    @Autowired
    RestTemplate restTemplate;

    public ParcelMachines getParcelMachines(String country) {
        ParcelMachines parcelMachines = new ParcelMachines(); // parem klõps -> refactor -> rename
        parcelMachines.setOmnivaParcelMachines(fetchOmnivaParcelMachines(country));
        if (country.equals("EE")) {
            parcelMachines.setSmartpostParcelMachines(fetchSmartpostParcelMachines());
        } else {
            parcelMachines.setSmartpostParcelMachines(new ArrayList<>());
        }
        return parcelMachines;
    }

    private List<SmartpostParcelMachine> fetchSmartpostParcelMachines() {
        log.info("Taking Smartpost parcel machines");
        ResponseEntity<SmartpostParcelMachine[]> response;
        List<SmartpostParcelMachine> smartpostParcelMachines = new ArrayList<>();
        try {
            response = restTemplate
                    .exchange(smartpostUrl, HttpMethod.GET, null, SmartpostParcelMachine[].class);
            if (response.getBody() != null) {
                smartpostParcelMachines = Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            log.error("SmartPost API endpointiga ei saanud ühendust");
        }
        return smartpostParcelMachines;
    }

    private List<OmnivaParcelMachine> fetchOmnivaParcelMachines(String country) {
        log.info("Taking Omniva parcel machines");
        ResponseEntity<OmnivaParcelMachine[]> response = restTemplate
                .exchange(omnivaUrl, HttpMethod.GET, null,OmnivaParcelMachine[].class);

        List<OmnivaParcelMachine> omnivaParcelMachines = new ArrayList<>();
        if (response.getBody() != null) {

            omnivaParcelMachines = Arrays.asList(response.getBody());
            omnivaParcelMachines = omnivaParcelMachines.stream()
                    .filter(p -> p.getA0_NAME().equals(country))
                    .collect(Collectors.toList());

//            List<OmnivaParcelMachine> omnivaParcelMachinesFiltered = new ArrayList<>();
//            for (OmnivaParcelMachine p: omnivaParcelMachines) {
//                if (p.getA0_NAME().equals(country)) {
//                    omnivaParcelMachinesFiltered.add(p);
//                }
//            }
        }
        return omnivaParcelMachines;
    }
}
