package com.itau.pixkeyregistration.domain.person.usecases;

import com.itau.pixkeyregistration.domain.account.enums.PersonalDocumentTypeEnum;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.exceptions.PersonAlreadyExistsException;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNaturalPersonUseCase {
    private final PersonStorageGateway personStorage;

    public Person create(Person person) {
        validatePersonAlreadyExists(person);
        validatePersonDocumentAndPersonTypeCohesion(person);
        personStorage.save(person);
        return person;
    }

    private void validatePersonAlreadyExists(Person person) {
        boolean hasPerson = personStorage.personExists(person.getId());
        if (hasPerson) {
            throw new PersonAlreadyExistsException(
                    "PERSON_ALREADY_EXISTS",
                    "Customer already registered in the system"
            );
        }
    }

    private void validatePersonDocumentAndPersonTypeCohesion(Person person) {
        PersonalDocumentTypeEnum personalDocumentType = person.getPersonalDocument().getType();
        boolean isValidNaturalPerson = person.getType().equals(PersonType.NATURAL_PERSON) && personalDocumentType.equals(PersonalDocumentTypeEnum.CPF);

        if(!isValidNaturalPerson) {
            throw new InvalidPersonTypeException(
                    "INVALID_PERSON_TYPE",
                    "The personal document provided is not valid for a natural person"
            );
        }
    }
}
