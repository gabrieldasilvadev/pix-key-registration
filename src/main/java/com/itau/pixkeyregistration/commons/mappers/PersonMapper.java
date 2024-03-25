package com.itau.pixkeyregistration.commons.mappers;

import com.itau.pixkeyregistration.application.web.dto.CreatePersonRequestDto;
import com.itau.pixkeyregistration.application.web.dto.CreatePersonResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PersonTypeDto;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.valueobjects.Cellphone;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PersonTable;
import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    private final AccountMapper accountMapper;
    private final PixMapper pixMapper;

    public Person toDomain(CreatePersonRequestDto request) {
        return Person.PersonBuilder.aPerson()
                .aId(new ULID().nextULID())
                .aFirstName(request.getFirstName())
                .aLastName(request.getLastName())
                .aBirthDate(request.getBirthDate())
                .aCellphone(new Cellphone(request.getCellphone()))
                .aPersonalDocument(request.getPersonalDocument())
                .aType(PersonType.valueOf(request.getPersonType().name()))
                .build();
    }

    public CreatePersonResponseDto toResponse(Person person) {
        return new CreatePersonResponseDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                PersonTypeDto.valueOf(person.getType().name()),
                person.getBirthDate()
        );
    }

    public PersonTable toTable(Person person, List<AccountTable> accountTables) {
        return PersonTable.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthDate(person.getBirthDate())
                .type(person.getType())
                .cellphone(person.getCellphone().value())
                .personalDocument(person.getPersonalDocument().value())
                .createdAt(LocalDateTime.now())
                .accounts(accountTables)
                .build();
    }

    public PersonTable toTable(Person person) {
        return PersonTable.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthDate(person.getBirthDate())
                .type(person.getType())
                .cellphone(person.getCellphone().value())
                .personalDocument(person.getPersonalDocument().value())
                .accounts(new ArrayList<>())
                .build();
    }

    public Person toDomain(PersonTable personTable) {
        HashSet<Account> accountsDomain = personTable.getAccounts()
                .stream()
                .map(ac -> accountMapper.toDomain(
                        ac,
                        pixMapper.toDomain(new HashSet<>(ac.getPixKeys()))
                ))
                .collect(Collectors.toCollection(HashSet::new));

        return Person.PersonBuilder.aPerson()
                .aId(personTable.getId())
                .aFirstName(personTable.getFirstName())
                .aLastName(personTable.getLastName())
                .aBirthDate(personTable.getBirthDate())
                .aCellphone(new Cellphone(personTable.getCellphone()))
                .aPersonalDocument(personTable.getPersonalDocument())
                .aType(PersonType.valueOf(personTable.getType().name()))
                .aAccounts(accountsDomain)
                .build();
    }
}
