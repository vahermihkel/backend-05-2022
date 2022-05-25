package ee.mihkel.webshop.model.request.output;

import ee.mihkel.webshop.model.request.input.OmnivaParcelMachine;
import ee.mihkel.webshop.model.request.input.SmartpostParcelMachine;
import lombok.Data;

import java.util.List;

@Data
public class ParcelMachines {
    private List<OmnivaParcelMachine> omnivaParcelMachines;
    private List<SmartpostParcelMachine> smartpostParcelMachines;
}
