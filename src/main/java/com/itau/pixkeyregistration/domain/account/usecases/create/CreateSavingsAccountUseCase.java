package com.itau.pixkeyregistration.domain.account.usecases.create;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.exceptions.AccountAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.exceptions.PersonNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.AccountStorageGateway;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSavingsAccountUseCase {

    private final AccountStorageGateway accountStorage;
    private final PersonStorageGateway personStorage;

    public Account create(Account account) {
        Person person = personStorage.getById(account.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(
                        "PERSON_NOT_FOUND",
                        String.format("The given person does not exist with PersonId[%s]", account.getPersonId())
                ));
        validatePersonType(person);
        List<Account> accounts = accountStorage.getByPersonalDocument(account.getPersonalDocument());
        validateSavingsAccountCreation(accounts, person);
        return accountStorage.save(account);
    }

    private static void validatePersonType(Person person) {
        if (person.getType().equals(PersonType.LEGAL_PERSON)) {
            throw new InvalidPersonTypeException(
                    "INVALID_PERSON_TYPE",
                    String.format(
                            "A legal person is not allowed to create a savings account. PersonId[%s]",
                            person.getId()
                    )
            );
        }
    }

    private void validateSavingsAccountCreation(List<Account> accounts, Person person) {
        boolean hasSavingsAccount = accounts.stream()
                .anyMatch(a -> a.getType().name().equals(AccountType.POUPANCA.name()));
        if (hasSavingsAccount) {
            throw new AccountAlreadyExistsException(
                    "SAVINGS_ACCOUNT_ALREADY_EXISTS",
                    String.format("A savings account already exists for PersonId[%s]", person.getId())
            );
        }
    }
}

