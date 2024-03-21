package com.itau.pixkeyregistration.domain.person.usecases;

import com.itau.pixkeyregistration.domain.exceptions.PersonAlreadyExistsException;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateLegalPersonUseCase {
    private final PersonStorageGateway personStorage;

    public Person create(Person person) {
        boolean hasPerson = personStorage.getPersonalDocument(person.getPersonalDocument()).isPresent();
        if(hasPerson) {
            throw new PersonAlreadyExistsException(
                    "PERSON_ALREADY_EXISTS",
                    "Customer already registered in the system"
            );
        }
        return personStorage.save(person);
    }
}
