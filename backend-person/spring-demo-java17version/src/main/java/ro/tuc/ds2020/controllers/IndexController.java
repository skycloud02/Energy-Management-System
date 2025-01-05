package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//asta da exception ca e in conflict cu cors din web security config
//@CrossOrigin
public class IndexController {

    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("City APP Service is running...", HttpStatus.OK);
    }
}
