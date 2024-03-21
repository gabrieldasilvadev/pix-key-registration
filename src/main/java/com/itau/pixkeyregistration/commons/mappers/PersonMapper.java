package com.itau.pixkeyregistration.commons.mappers;

import com.itau.pixkeyregistration.application.web.dto.CreatePersonRequestDto;
import com.itau.pixkeyregistration.application.web.dto.CreatePersonResponseDto;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.valueobjects.Cellphone;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public Person toDomain(CreatePersonRequestDto request) {
        return Person.PersonBuilder.aPerson()
                .aFirstName(request.getFirstName())
                .aLastName(request.getLastName())
                .aBirthDate(request.getBirthDate())
                .aCellphone(Cellphone
                        .builder()
                        .value(request.getCellphone())
                        .build())
                .aPersonalDocument(request.getPersonalDocument())
                .aType(PersonType.valueOf(request.getPersonType().name()))
                .build();
    }

    public CreatePersonResponseDto toResponse(Person person) {
        return new CreatePersonResponseDto(
                // TODO("not yet implemented")
        );
    }
}
