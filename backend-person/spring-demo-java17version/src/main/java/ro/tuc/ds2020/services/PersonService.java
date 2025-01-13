package ro.tuc.ds2020.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.dtos.ReqRes;
import ro.tuc.ds2020.dtos.builders.PersonBuilder;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.enums.UserRole;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;
    private final String secretKey = "mySecretKey";
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtils jwtUtils;

    @Value("${device.service.url}")
    private String deviceServiceUrl;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate, JwtUtils jwtUtils) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.jwtUtils = jwtUtils;
    }

    public List<PersonDTO> findPersons() {
        List<Person> personList = personRepository.findByUserRole(UserRole.PERSON);
        return personList.stream()
                .map(PersonBuilder::toPersonDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findPersonById(UUID id) {
        Optional<Person> prosumerOptional = personRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return PersonBuilder.toPersonDTO(prosumerOptional.get());
    }

    public UUID update(PersonDetailsDTO personDTO) {
        Person oldPerson = personRepository.findById(personDTO.getId())
                .orElseThrow(() -> {
                    LOGGER.error("Person with id {} was not found in db", personDTO.getId());
                    throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + personDTO.getId());
                });

        if(personDTO.getName() != null) {
            oldPerson.setName(personDTO.getName());
        }

        if(personDTO.getUsername() != null) {
            oldPerson.setUsername(personDTO.getUsername());
        }

        if(personDTO.getAddress() != null) {
            oldPerson.setAddress(personDTO.getAddress());
        }

        if(personDTO.getPass() != null) {
            oldPerson.setPass(personDTO.getPass());
        }

        if(personDTO.getAge() != 0){
            oldPerson.setAge(personDTO.getAge());
        }
        if(personDTO.getUserRole() != null) {
            oldPerson.setUserRole(personDTO.getUserRole());
        }

        oldPerson = personRepository.save(oldPerson);
        LOGGER.debug("Person with id {} was updated in db", oldPerson.getId());
        return oldPerson.getId();
    }

    public void delete(UUID id) {
        try{
			// in cadrul docker-compose nu il recunoaste ca localhost, trb container name
            String deleteDevicesUrl = "http://devices.localhost/spring-demo/userDevice/deleteByUser/" + id;
            restTemplate.delete(deleteDevicesUrl);
            restTemplate.delete(deleteDevicesUrl);
            LOGGER.info("Successfully deleted devices associated with userId {}", id);
            personRepository.deleteById(id);
            LOGGER.debug("Person with id {} was deleted in db", id);
        }
        catch(Exception e){
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
    }

    public UUID insert(PersonDetailsDTO personDTO) {
        personDTO.setPass(passwordEncoder.encode(personDTO.getPass()));
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);

        UUID id = person.getId();

        try{
            // in cadrul docker-compose nu il recunoaste ca localhost, trb container name
            restTemplate.postForEntity("http://backend-devices:8080/spring-demo/userDevice/addUser", id, UUID.class);
            LOGGER.info("Successfully added userId {} to UserDevice in Device microservice", id);
        } catch (Exception e) {
            LOGGER.error("Failed to add userId {} to UserDevice table in Device microservice", id, e);
        }

        return id;
    }

    // Register new user
    public UUID register(PersonDetailsDTO personDTO) {
        // Hash the password using BCrypt before saving
        personDTO.setPass(passwordEncoder.encode(personDTO.getPass()));
        Person person = PersonBuilder.toEntity(personDTO);
        person = personRepository.save(person);

        UUID id = person.getId();

        try{
			// in cadrul docker-compose nu il recunoaste ca localhost, trb container name
            restTemplate.postForEntity("http://backend-devices:8080/spring-demo/userDevice/addUser", id, UUID.class);
            LOGGER.info("Successfully added userId {} to UserDevice in Device microservice", id);
        } catch (Exception e) {
            LOGGER.error("Failed to add userId {} to UserDevice table in Device microservice", id, e);
        }

        return id;
    }

    // Login user and return JWT token if successful
    public String loginRole(String username, String password) {
        Optional<Person> personOptional = personRepository.findByUsername(username);
        if (personOptional.isPresent() && passwordEncoder.matches(password, personOptional.get().getPass())) {
            // Generate JWT
            return personOptional.get().getUserRole().toString();
        }
        throw new RuntimeException("Invalid credentials");
    }

    public ReqRes loginId(String username, String password) {
        Optional<Person> personOptional = personRepository.findByUsername(username);
        if (personOptional.isPresent() && passwordEncoder.matches(password, personOptional.get().getPass())) {
            // Generate JWT
            String token = jwtUtils.generateToken(personOptional.get().getUsername(), personOptional.get().getUserRole().name());
            return new ReqRes(token, personOptional.get().getId(), personOptional.get().getName(), personOptional.get().getUsername(), personOptional.get().getUserRole());
        }
        throw new RuntimeException("Invalid credentials");
    }

    // Simple password hashing
    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // Use a better algorithm in production
    }

    // Check password
    private boolean checkPassword(String rawPassword, String hashedPassword) {
        return hashPassword(rawPassword).equals(hashedPassword);
    }

    // Generate JWT token
    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Method to verify JWT
    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
