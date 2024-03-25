package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa;

import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import com.itau.pixkeyregistration.commons.mappers.PersonMapper;
import com.itau.pixkeyregistration.domain.exceptions.PersonNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PersonTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonStorageJpa implements PersonStorageGateway {
    private final PersonRepository repository;
    private final PersonMapper personMapper;
    private final AccountMapper accountMapper;

    @Override
    public void save(Person person) {
        PersonTable personTable = personMapper.toTable(person);
        List<AccountTable> accountTables = filterAccounts(person, personTable);
        personTable.getAccounts().addAll(accountTables);
        personTable.setCreatedAt(LocalDateTime.now());
        repository.save(personTable);
    }

    private List<AccountTable> filterAccounts(Person person, PersonTable personTable) {
        List<AccountTable> accountTables = new ArrayList<>();
        if (person.getAccounts() != null) {
            accountTables = person.getAccounts()
                    .stream()
                    .filter(account -> !account.getId().isBlank())
                    .map(account -> accountMapper.toTable(account, personTable))
                    .toList();
        }
        return accountTables;
    }

    @Override
    public Person findById(String personId) {
        var personTable = repository.findById(personId).orElseThrow(() -> new PersonNotFoundException(
                "PERSON_NOT_FOUND",
                String.format("The given person is not exists with PersonId[%s]", personId)
        ));
        return personMapper.toDomain(personTable);
    }

    @Override
    public Person getPersonalDocument(String personalDocument) {
        var personTable = repository.findByPersonalDocument(personalDocument).orElseThrow(() -> new PersonNotFoundException(
                "PERSON_NOT_FOUND",
                "Customer not found in the system"
        ));

        return personMapper.toDomain(personTable);
    }

    @Override
    public boolean personExists(String personalDocument) {
        return repository.existsByPersonalDocument(personalDocument);
    }
}
