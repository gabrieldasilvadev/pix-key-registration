package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.person.Person;

public interface PersonStorageGateway {
    void save(Person person);

    Person findById(String personId);

    Person getPersonalDocument(String personalDocument);

    boolean personExists(String personalDocument);
}
