package com.itau.pixkeyregistration.domain.account.usecases.create;

import com.itau.pixkeyregistration.application.config.AccountProperties;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.exceptions.AccountAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.AccountLimitExceededException;
import com.itau.pixkeyregistration.domain.exceptions.PersonNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.AccountStorageGateway;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCheckingAccountUseCase {

    private final AccountStorageGateway accountStorage;
    private final PersonStorageGateway personStorage;
    private final AccountProperties accountProperties;

    public Account create(Account account) {
        Person person = personStorage.getById(account.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(
                        "PERSON_NOT_FOUND",
                        String.format("The given person is not exists with PersonId[%s]", account.getPersonId())
                ));
        List<Account> accounts = accountStorage.getByPersonalDocument(account.getPersonalDocument());
        validateCheckingAccountCreation(accounts, person);
        validateCustomerCanCreateAccount(accounts, person);
        person.addAccount(account);
        return accountStorage.save(account);
    }

    private void validateCustomerCanCreateAccount(List<Account> accounts, Person person) {
        validateNaturalPerson(accounts, person);
        validateLegalPerson(person);
    }

    private void validateNaturalPerson(List<Account> accounts, Person person) {
        boolean isNaturalPerson = person.getType().name().equals(PersonType.NATURAL_PERSON.name());
        boolean hasMoreThanOneAccount = accounts.size() > accountProperties.getNaturalPersonLimitAccount();
        if (hasMoreThanOneAccount && isNaturalPerson) {
            throw new AccountAlreadyExistsException(
                    "ACCOUNT_ALREADY_EXISTS",
                    String.format("The given account already exists with PersonId[%s]", person.getId())
            );
        }
    }

    private void validateLegalPerson(Person person) {
        boolean isLegalPerson = person.getType().name().equals(PersonType.LEGAL_PERSON.name());
        boolean hasExceededLimit = person.getAccounts().size() > accountProperties.getLegalPersonLimitAccount();
        if (isLegalPerson && hasExceededLimit) {
            throw new AccountLimitExceededException(
                    "ACCOUNT_LIMIT_EXCEEDED",
                    String.format(
                            "Client exceeded the limit of 5 accounts. PersonType[%s]",
                            PersonType.LEGAL_PERSON.name()
                    )
            );
        }
    }

    private void validateCheckingAccountCreation(List<Account> accounts, Person person) {
        boolean hasMoreThanOneCheckingAccount = accounts.stream()
                .filter(acc -> acc.getType().equals(AccountType.CORRENTE))
                .count() > 1;
        if (hasMoreThanOneCheckingAccount) {
            throw new AccountLimitExceededException(
                    "ACCOUNT_LIMIT_EXCEEDED",
                    String.format("Limit of checking accounts exceeded for PersonId[%s]", person.getId())
            );
        }
    }
}
