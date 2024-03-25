package com.itau.pixkeyregistration.application.person;

import com.itau.pixkeyregistration.application.web.dto.CreatePersonRequestDto;
import com.itau.pixkeyregistration.commons.mappers.PersonMapper;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.person.usecases.CreateLegalPersonUseCase;
import com.itau.pixkeyregistration.domain.person.usecases.CreateNaturalPersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonApplicationServiceImpl implements PersonApplicationService {
    private final CreateLegalPersonUseCase legalPersonUseCase;
    private final CreateNaturalPersonUseCase naturalPersonUseCase;
    private final PersonMapper mapper;

    @Override
    public Person create(CreatePersonRequestDto requestDto) {
        if (requestDto.getPersonType().name().equals(PersonType.LEGAL_PERSON.name())) {
            return legalPersonUseCase.create(mapper.toDomain(requestDto));
        } else {
            return naturalPersonUseCase.create(mapper.toDomain(requestDto));
        }
    }
}
