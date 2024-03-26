package com.itau.pixkeyregistration.application.account;

import com.itau.pixkeyregistration.application.web.dto.CreateAccountRequestDto;
import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountStatus;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.account.usecases.CreateCheckingAccountUseCase;
import com.itau.pixkeyregistration.domain.account.usecases.CreateSavingsAccountUseCase;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountApplicationServiceImpl implements AccountApplicationService {
    private final CreateCheckingAccountUseCase createCheckingAccountUseCase;
    private final CreateSavingsAccountUseCase createSavingsAccountUseCase;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    public Account create(CreateAccountRequestDto requestDto) {
        String accountNumber = generateNextAccountNumber();
        if (requestDto.getAccountType().name().equals(AccountType.CORRENTE.name())) {
            return createCheckingAccountUseCase.create(mapper.toDomain(requestDto, AccountStatus.ACTIVE, accountNumber));
        } else {
            return createSavingsAccountUseCase.create(mapper.toDomain(requestDto, AccountStatus.ACTIVE, accountNumber));
        }
    }

    private String generateNextAccountNumber() {
        String lastNumber = accountRepository.findLastAccountNumber();
        int nextNumber = lastNumber == null ? 1 : Integer.parseInt(lastNumber) + 1;
        return String.format("%08d", nextNumber);
    }
}
