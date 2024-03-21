package com.itau.pixkeyregistration.application.person;

import com.itau.pixkeyregistration.application.web.dto.CreatePersonRequestDto;
import com.itau.pixkeyregistration.domain.person.Person;

public interface PersonApplicationService {
    Person create(CreatePersonRequestDto requestDto);
}
