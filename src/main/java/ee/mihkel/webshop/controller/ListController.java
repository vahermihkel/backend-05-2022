package ee.mihkel.webshop.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ListController {
    List<String> strings = new ArrayList<>(Arrays.asList("Üks", "Kaks", "Kolm"));

    @GetMapping("get-strings") // localhost:8080/get-strings
    public List<String> getStrings() {
        return strings;     // ["Üks", "Kaks", "Kolm"]
    }

    @PostMapping("add-string/{newString}") // localhost:8080/add-string/Neli
    public void addString(@PathVariable String newString) {
        strings.add(newString);
    }

    @DeleteMapping("delete-string/{index}") // localhost:8080/delete-string/1
    public void addString(@PathVariable int index) {
        strings.remove(index);
    }
}
