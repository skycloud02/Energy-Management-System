package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
//import ro.tuc.ds2020.entities.Person;
//import ro.tuc.ds2020.services.AuthService;
import ro.tuc.ds2020.dtos.ReqRes;
import ro.tuc.ds2020.services.PersonService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
//asta da exception ca e in conflict cu cors din web security config
//@CrossOrigin("*")
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;

    //private final AuthService authService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        List<PersonDTO> dtos = personService.findPersons();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getAdmins")
    public ResponseEntity<List<PersonDTO>> getAdmins() {
        List<PersonDTO> dtos = personService.findAdmins();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/addPerson")
    public ResponseEntity<Map<String, UUID>> insertProsumer(@Valid @RequestBody PersonDetailsDTO personDTO) {
        UUID personID = personService.insert(personDTO);
        Map<String, UUID> response = new HashMap<>();
        response.put("id", personID);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable("id") UUID personId) {
        PersonDTO dto = personService.findPersonById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UUID> updatePerson(@RequestBody PersonDetailsDTO personDTO) {
        UUID personID = personService.update(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<UUID> deletePerson(@PathVariable("id") UUID personId) {
        personService.delete(personId);
        return new ResponseEntity<>(personId, HttpStatus.OK);
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UUID> register(@Valid @RequestBody PersonDetailsDTO personDTO) {
        UUID personID = personService.register(personDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

    // Login user
    @PostMapping("/loginRole")
    public ResponseEntity<String> loginRole(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("pass");
        try {
            String token = personService.loginRole(username, password);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/loginId")
    public ResponseEntity<ReqRes> loginId(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("pass");
        try {
            ReqRes token = personService.loginId(username, password);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
