package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa;

import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonStorageJpa implements PersonStorageGateway {
    private final PersonRepository repository;
    @Override
    public Person save(Person person) {
        repository.save(person.toTable());
    }
}
