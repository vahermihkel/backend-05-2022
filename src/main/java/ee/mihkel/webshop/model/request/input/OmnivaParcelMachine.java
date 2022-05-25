package ee.mihkel.webshop.model.request.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OmnivaParcelMachine {
    @JsonProperty("ZIP")
    public String zIP;
    @JsonProperty("NAME")
    public String nAME;
    @JsonProperty("TYPE")
    public String tYPE;
    @JsonProperty("A0_NAME")
    public String a0_NAME;
    @JsonProperty("A1_NAME")
    public String a1_NAME;
    @JsonProperty("A2_NAME")
    public String a2_NAME;
    @JsonProperty("A3_NAME")
    public String a3_NAME;
    @JsonProperty("A4_NAME")
    public String a4_NAME;
    @JsonProperty("A5_NAME")
    public String a5_NAME;
    @JsonProperty("A6_NAME")
    public String a6_NAME;
    @JsonProperty("A7_NAME")
    public String a7_NAME;
    @JsonProperty("A8_NAME")
    public String a8_NAME;
    @JsonProperty("X_COORDINATE")
    public String x_COORDINATE;
    @JsonProperty("Y_COORDINATE")
    public String y_COORDINATE;
    @JsonProperty("SERVICE_HOURS")
    public String sERVICE_HOURS;
    @JsonProperty("TEMP_SERVICE_HOURS")
    public String tEMP_SERVICE_HOURS;
    @JsonProperty("TEMP_SERVICE_HOURS_UNTIL")
    public String tEMP_SERVICE_HOURS_UNTIL;
    @JsonProperty("TEMP_SERVICE_HOURS_2")
    public String tEMP_SERVICE_HOURS_2;
    @JsonProperty("TEMP_SERVICE_HOURS_2_UNTIL")
    public String tEMP_SERVICE_HOURS_2_UNTIL;
    public String comment_est;
    public String comment_eng;
    public String comment_rus;
    public String comment_lav;
    public String comment_lit;
    @JsonProperty("MODIFIED")
    public Date mODIFIED;
}
