package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.person.Person;

import java.util.Optional;

public interface PersonStorageGateway {
    Person save(Person person);

    Optional<Person> getById(String personId);

    Optional<Person> getPersonalDocument(String personalDocument);
}
