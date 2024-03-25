package com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa;

import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import com.itau.pixkeyregistration.commons.mappers.PixMapper;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.exceptions.AccountNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.AccountStorageGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountStorageJpa implements AccountStorageGateway {
    private final AccountRepository accountRepository;
    private final PersonRepository personRepository;
    private final AccountMapper accountMapper;
    private final PixMapper pixMapper;

    @Override
    public void save(Account account) {
        var personTable = personRepository.findById(account.getPersonId()).orElseThrow();
        accountRepository.save(accountMapper.toTable(account, personTable));
    }

    @Override
    public List<Account> getByPersonalDocument(String personalDocument) {
        return accountRepository
                .findAllByPersonalDocument(personalDocument)
                .stream()
                .map(accountTable -> {
                    HashSet<PixKey> pixKeys = accountTable.getPixKeys().stream()
                            .map(pixMapper::toDomain)
                            .collect(Collectors.toCollection(HashSet::new));
                    return accountMapper.toDomain(accountTable, pixKeys);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        var accountTable = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(
                "ACCOUNT_NOT_FOUND",
                String.format("The accountRequest with AccountNumber:%s was not found", accountNumber)
        ));
        var hashPixKey = new HashSet<>(accountTable.getPixKeys());
        var hashPixKeyDomain = pixMapper.toDomain(hashPixKey);
        return accountMapper.toDomain(accountTable, hashPixKeyDomain);
    }

    @Override
    public Account findById(String accountId) {
        var accountTable = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(
                "ACCOUNT_NOT_FOUND",
                String.format("The given account not exists. AccountId[%s]", accountId)
        ));

        HashSet<PixKey> pixKeys = accountTable.getPixKeys().stream()
                .map(pixMapper::toDomain)
                .collect(Collectors.toCollection(HashSet::new));

        return accountMapper.toDomain(accountTable, pixKeys);
    }
}
