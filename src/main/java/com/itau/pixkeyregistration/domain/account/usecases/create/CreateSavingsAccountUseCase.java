package com.itau.pixkeyregistration.domain.account.usecases.create;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.account.enums.PersonalDocumentTypeEnum;
import com.itau.pixkeyregistration.domain.exceptions.AccountAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSavingsAccountUseCase {
    private final PersonStorageGateway personStorage;

    public Account create(Account account) {
        Person person = personStorage.findById(account.getPersonId());
        validateCustomerCanCreateAccount(person);
        person.addAccount(account);
        personStorage.save(person);
        return account;
    }

    private void validateCustomerCanCreateAccount(Person person) {
        validatePersonType(person);
        validateSavingsAccountCreation(person);
    }

    private void validatePersonType(Person person) {
        PersonalDocumentTypeEnum personalDocumentType = person.getPersonalDocument().getType();
        boolean isLegalPerson = personalDocumentType.equals(PersonalDocumentTypeEnum.CNPJ);
        if (isLegalPerson) {
            throw new InvalidPersonTypeException(
                    "INVALID_PERSON_TYPE",
                    String.format(
                            "A legal person is not allowed to create a savings account. PersonId[%s]",
                            person.getId()
                    )
            );
        }
    }

    private void validateSavingsAccountCreation(Person person) {
        boolean hasSavingsAccount = person.getAccounts().stream()
                .anyMatch(a -> a.getType().name().equals(AccountType.POUPANCA.name()));
        if (hasSavingsAccount) {
            throw new AccountAlreadyExistsException(
                    "SAVINGS_ACCOUNT_ALREADY_EXISTS",
                    String.format("A savings account already exists for PersonId[%s]", person.getId())
            );
        }
    }
}

