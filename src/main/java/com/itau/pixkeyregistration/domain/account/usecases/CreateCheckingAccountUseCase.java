package com.itau.pixkeyregistration.domain.account.usecases;

import com.itau.pixkeyregistration.application.config.properties.PersonProperties;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.PersonalDocumentTypeEnum;
import com.itau.pixkeyregistration.domain.exceptions.AccountAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.AccountLimitExceededException;
import com.itau.pixkeyregistration.domain.gateways.PersonStorageGateway;
import com.itau.pixkeyregistration.domain.person.Person;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCheckingAccountUseCase {
    private final PersonStorageGateway personStorage;
    private final PersonProperties personProperties;

    public Account create(Account account) {
        Person person = personStorage.findById(account.getPersonId());
        validateCustomerCanCreateAccount(person);
        person.addAccount(account);
        personStorage.save(person);
        return account;
    }

    private void validateCustomerCanCreateAccount(Person person) {
        validateNaturalPersonAccountLimit(person);
        validateLegalPersonAccountLimit(person);
    }

    private void validateNaturalPersonAccountLimit(Person person) {
        PersonalDocumentTypeEnum personalDocumentType = person.getPersonalDocument().getType();
        boolean isNaturalPerson = person.getPersonalDocument().getType().equals(personalDocumentType)
                && person.getType().equals(PersonType.NATURAL_PERSON);
        boolean hasMoreThanOneAccount = person.getAccounts().size() > personProperties.getNaturalPersonLimitAccount();
        if (hasMoreThanOneAccount && isNaturalPerson) {
            throw new AccountAlreadyExistsException(
                    "ACCOUNT_ALREADY_EXISTS",
                    String.format("The given account already exists with PersonId[%s]", person.getId())
            );
        }
    }

    private void validateLegalPersonAccountLimit(Person person) {
        PersonalDocumentTypeEnum personalDocumentType = person.getPersonalDocument().getType();
        boolean isLegalPerson = personalDocumentType.name().equals(PersonType.LEGAL_PERSON.name());
        boolean hasExceededLimit = person.getAccounts().size() > personProperties.getLegalPersonLimitAccount();

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
}
