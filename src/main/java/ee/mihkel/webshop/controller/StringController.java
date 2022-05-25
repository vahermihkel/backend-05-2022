package ee.mihkel.webshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // annoteerimine --- import
public class StringController {

    @GetMapping("hello")   // localhost:8080/hello
    public String getString() {
        return "Hello world";
    }

    // pathvariable  -- urlmuutuja
    @GetMapping("string/{returnString}")   // localhost:8080/string/mingisuvaline
    public String returnString(@PathVariable String returnString) {
        return returnString;
    }

    // pathvariable  -- urlmuutuja
    @GetMapping("liida/{nr1}/{nr2}")   // localhost:8080/liida/3/5
    public int liida(@PathVariable int nr1, @PathVariable int nr2) {
        return nr1 + nr2;
    }
}
