package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.PersonDTO;
import ro.tuc.ds2020.dtos.PersonDetailsDTO;
import ro.tuc.ds2020.entities.Person;

public class PersonBuilder {

    private PersonBuilder() {
    }

    public static PersonDTO toPersonDTO(Person person) {
        return new PersonDTO(person.getId(), person.getName(), person.getUsername(), person.getAddress(), person.getAge(), person.getUserRole());
    }

    public static PersonDetailsDTO toPersonDetailsDTO(Person person) {
        return new PersonDetailsDTO(person.getId(), person.getName(), person.getUsername(), person.getAddress(), person.getPass(), person.getAge(), person.getUserRole());
    }

    public static Person toEntity(PersonDetailsDTO personDetailsDTO) {
        return new Person(personDetailsDTO.getName(),
                personDetailsDTO.getUsername(),
                personDetailsDTO.getAddress(),
                personDetailsDTO.getPass(),
                personDetailsDTO.getAge(),
                personDetailsDTO.getUserRole());
    }

    public static Person toEntityWithId(PersonDetailsDTO personDetailsDTO) {
        return new Person(personDetailsDTO.getId(), personDetailsDTO.getName(), personDetailsDTO.getUsername(), personDetailsDTO.getAddress(), personDetailsDTO.getPass(), personDetailsDTO.getAge(), personDetailsDTO.getUserRole());
    }
}
