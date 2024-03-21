package com.itau.pixkeyregistration.application.account;

import com.itau.pixkeyregistration.application.web.dto.*;
import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.account.usecases.*;
import com.itau.pixkeyregistration.domain.account.usecases.create.CreateCheckingAccountUseCase;
import com.itau.pixkeyregistration.domain.account.usecases.create.CreateSavingsAccountUseCase;
import com.itau.pixkeyregistration.domain.account.usecases.registerpix.*;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.PersonStorageJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountApplicationServiceImpl implements AccountApplicationService {

    private final CreateCheckingAccountUseCase createCheckingAccountUseCase;
    private final CreateSavingsAccountUseCase createSavingsAccountUseCase;
    private final RegisterAddressToAccountUseCase registerAddressToAccountUseCase;
    private final RegisterCpfPixUseCase registerCpfPixUseCase;
    private final RegisterEmailPixUseCase registerEmailPixUseCase;
    private final RegisterCellphonePixUseCase registerCellphonePixUseCase;
    private final RegisterCnpjPixUseCase registerCnpjPixUseCase;
    private final RegisterRandomPixUseCase registerRandomPixUseCase;
    private final PersonStorageJpa personStorageJpa;
    private final PixStorageJpa pixStorageJpa;
    private final UpdatePiXKeyUseCase updatePiXKeyUseCase;
    private final AccountMapper mapper;

    @Override
    public Account create(CreateAccountRequestDto requestDto) {
        if (requestDto.getAccountType().name().equals(AccountType.CORRENTE.name())) {
            return createCheckingAccountUseCase.create(mapper.toDomain(requestDto));
        } else {
            return createSavingsAccountUseCase.create(mapper.toDomain(requestDto));
        }
    }

    @Override
    public CreateAddressResponseDto registerAddress(CreateAddressRequestDto createAddressRequestDto) {
        Account account = registerAddressToAccountUseCase
                .register(mapper.toAddress(createAddressRequestDto), createAddressRequestDto.getAccountId());
        return CreateAddressResponseDto(

        );
    }

    @Override
    public PixKeyRegistrationResponseDto registerPix(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        Account account = selectorPixKeyCase(pixKeyRegistrationRequestDto);
        Person person = retrievePersonDetails(pixKeyRegistrationRequestDto.getAccountDetails().getAccountNumber());
        // implementing pix storage to get status

        return new PixKeyRegistrationResponseDto(
                pixKeyRegistrationRequestDto.getType(),
                pixKeyRegistrationRequestDto.getKeyValue(),
                new AccountDetailsResponseDto(
                        account.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        account.getAgencyNumber(),
                        AccountTypeDto.fromValue(account.getType().name())
                ),
                PixKeyStatusDto.fromValue("PROMISE_ACTIVE")
        );
    }

    @Override
    public PixKeyUpdateResponseDto updatePixKey(PixKeyUpdateAccountRequestDto pixKeyUpdateAccountRequestDto) {
        updatePiXKeyUseCase.update();
    }

    @Override
    public InactivePixKeyResponseDto inactivePixKey(String keyId) {
        return null;
    }

    private Account selectorPixKeyCase(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        return switch (pixKeyRegistrationRequestDto.getType().name()) {
            case "ALEATORIO" -> registerRandomPixUseCase.register();
            case "EMAIL" -> registerEmailPixUseCase.register();
            case "CPF" -> registerCpfPixUseCase.register();
            case "CNPJ" -> registerCnpjPixUseCase.register();
            case "CELULAR" -> registerCellphonePixUseCase.register();
            default -> throw new RuntimeException("Unable process");
        };
    }

    private Person retrievePersonDetails(String accountNumber) {
        return personStorageJpa.getByAccountNumber(accountNumber);
    }
}
