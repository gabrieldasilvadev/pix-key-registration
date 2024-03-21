package com.itau.pixkeyregistration.infrastructure.inbound.rest.controllers.person;

import com.itau.pixkeyregistration.application.person.PersonApplicationService;
import com.itau.pixkeyregistration.application.web.api.PersonApi;
import com.itau.pixkeyregistration.application.web.dto.CreatePersonRequestDto;
import com.itau.pixkeyregistration.application.web.dto.CreatePersonResponseDto;
import com.itau.pixkeyregistration.commons.mappers.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {

    private final PersonApplicationService service;
    private final PersonMapper mapper;

    @Override
    public ResponseEntity<CreatePersonResponseDto> createPerson(CreatePersonRequestDto createPersonRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.create(createPersonRequestDto)));
    }
}
