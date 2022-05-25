package ee.mihkel.webshop.model.request.input;

import lombok.Data;

@Data
public class SmartpostParcelMachine {
    public int place_id;
    public String name;
    public String city;
    public String address;
    public String opened;
    public int group_id;
    public String group_name;
    public int group_sort;
    public String description;
    public int active;
    public String inactive_reason;
    public String created_date;
    public String updated_date;
}
