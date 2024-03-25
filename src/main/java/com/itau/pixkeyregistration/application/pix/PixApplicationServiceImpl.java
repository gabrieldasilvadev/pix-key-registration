package com.itau.pixkeyregistration.application.pix;

import com.itau.pixkeyregistration.application.web.dto.AccountTypeDto;
import com.itau.pixkeyregistration.application.web.dto.InactivePixKeyResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyRegistrationRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyTypeDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateRequestDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyUpdateResponseDto;
import com.itau.pixkeyregistration.commons.mappers.PixMapper;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;
import com.itau.pixkeyregistration.domain.pix.usecases.InactivePixKeyUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.PixKeyUpdateUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.registerpix.RegisterCellphonePixUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.registerpix.RegisterCnpjPixUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.registerpix.RegisterCpfPixUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.registerpix.RegisterEmailPixUseCase;
import com.itau.pixkeyregistration.domain.pix.usecases.registerpix.RegisterRandomPixUseCase;
import com.itau.pixkeyregistration.domain.valueobjects.PixKeyUpdateTransient;
import com.itau.pixkeyregistration.domain.valueobjects.RegisterPixKeyAccountDetailsTransient;
import com.itau.pixkeyregistration.domain.valueobjects.RegisterPixKeyTransient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class PixApplicationServiceImpl implements PixApplicationService {
    private final RegisterCpfPixUseCase registerCpfPixUseCase;
    private final RegisterEmailPixUseCase registerEmailPixUseCase;
    private final RegisterCellphonePixUseCase registerCellphonePixUseCase;
    private final RegisterCnpjPixUseCase registerCnpjPixUseCase;
    private final RegisterRandomPixUseCase registerRandomPixUseCase;
    private final InactivePixKeyUseCase inactivePixKeyUseCase;
    private final PixKeyUpdateUseCase pixKeyUpdateUseCase;
    private final PixMapper mapper;

    @Override
    public void registerKey(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        selectorPixKeyCase(pixKeyRegistrationRequestDto);
    }

    @Override
    public InactivePixKeyResponseDto inactiveKey(String keyId) {
        var pixKeyInactivated = inactivePixKeyUseCase.inactive(keyId);
        return mapper.toInactiveResponse(pixKeyInactivated);
    }

    @Override
    public PixKeyUpdateResponseDto update(PixKeyUpdateRequestDto pixKeyUpdateRequestDto) {
        var pixKeyUpdated = pixKeyUpdateUseCase.update(new PixKeyUpdateTransient(
                pixKeyUpdateRequestDto.getKeyId(),
                AccountType.valueOf(pixKeyUpdateRequestDto.getAccountType().name()),
                pixKeyUpdateRequestDto.getAgencyNumber(),
                pixKeyUpdateRequestDto.getAccountNumber(),
                pixKeyUpdateRequestDto.getFirstName(),
                pixKeyUpdateRequestDto.getLastName(),
                pixKeyUpdateRequestDto.getKeyValue()
        ));

        return new PixKeyUpdateResponseDto(
                pixKeyUpdated.getKeyId(),
                PixKeyTypeDto.valueOf(pixKeyUpdated.getKeyType().name()),
                pixKeyUpdated.getKeyValue(),
                AccountTypeDto.valueOf(pixKeyUpdated.getAccountType().name()),
                pixKeyUpdated.getAgency(),
                pixKeyUpdated.getAccountNumber(),
                pixKeyUpdated.getFirstName(),
                pixKeyUpdated.getLastName(),
                pixKeyUpdated.getKeyAddedAt().atOffset(ZoneOffset.UTC)
        );
    }

    private void selectorPixKeyCase(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        var registerPixKeyTransient = new RegisterPixKeyTransient(
                PixKeyType.valueOf(pixKeyRegistrationRequestDto.getType().name()),
                pixKeyRegistrationRequestDto.getKeyValue(),
                new RegisterPixKeyAccountDetailsTransient(
                        pixKeyRegistrationRequestDto.getAccountDetails().getFirstName(),
                        pixKeyRegistrationRequestDto.getAccountDetails().getLastName(),
                        pixKeyRegistrationRequestDto.getAccountDetails().getAccountNumber(),
                        AccountType.valueOf(pixKeyRegistrationRequestDto.getAccountDetails().getAccountType().name())
                )

        );
        switch (pixKeyRegistrationRequestDto.getType().name()) {
            case "ALEATORIO" -> registerRandomPixUseCase.register(registerPixKeyTransient);
            case "EMAIL" -> registerEmailPixUseCase.register(registerPixKeyTransient);
            case "CPF" -> registerCpfPixUseCase.register(registerPixKeyTransient);
            case "CNPJ" -> registerCnpjPixUseCase.register(registerPixKeyTransient);
            case "CELULAR" -> registerCellphonePixUseCase.register(registerPixKeyTransient);
            default -> throw new RuntimeException("Invalid PIX key type");
        }
    }
}
