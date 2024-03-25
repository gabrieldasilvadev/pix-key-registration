package com.itau.pixkeyregistration.domain.account;

import com.itau.pixkeyregistration.domain.account.enums.AccountStatus;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.exceptions.AccountNumberExceededLimit;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountStatusException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountTypeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgencyException;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.valueobjects.Email;
import com.itau.pixkeyregistration.domain.valueobjects.Password;
import com.itau.pixkeyregistration.domain.valueobjects.PersonalDocument;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class Account {
    private String id;
    private String agencyNumber;
    private Email email;
    private Password password;
    private String personId;
    private AccountType type;
    private AccountStatus status = AccountStatus.ACTIVE;
    private PersonalDocument personalDocument;
    private String number;
    private Set<PixKey> pixKeys;

    public Account() {
        this.pixKeys = new HashSet<>();
    }

    public void addPixKey(PixKey pixKey, PersonType personType) {
        int maxKeys = personType == PersonType.LEGAL_PERSON ? 20 : 5;

        if (!pixKeys.add(pixKey)) {
            throw new RuntimeException("PIX key already registered.");
        }

        if (pixKeys.size() >= maxKeys) {
            throw new RuntimeException("Pix key limit exceeded");
        }

        pixKeys.add(pixKey);
    }

    public PixKey getPixKeyByType(String pixKeyType) {
        return this.pixKeys.stream().filter(px -> px.getByType(pixKeyType) != null)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void validateAccount() {
        isValidType();
        isValidStatus();
        isValidAgencyNumber();
        validateAccountNumber();
    }

    private void isValidType() {
        boolean hasAccountType = Arrays.stream(AccountType.values()).toList().contains(this.type);
        if (!hasAccountType) {
            throw new InvalidAccountTypeException(
                    "ACCOUNT_INVALID_PARAMS",
                    String.format("The given person type is not valid. AccountType[%s]", this.type.name()),
                    Map.of("ACCOUNT_TYPE", this.type.name())
            );
        }
    }

    private void isValidStatus() {
        boolean hasAccountStatus = Arrays.stream(AccountStatus.values()).toList().contains(this.status);
        if (!hasAccountStatus) {
            throw new InvalidAccountStatusException(
                    "ACCOUNT_INVALID_PARAMS",
                    String.format("The given account status is not valid. AccountStatus[%s]", this.status.name()),
                    Map.of("ACCOUNT_STATUS", this.status.name())
            );
        }
    }

    private void isValidAgencyNumber() {
        if (this.agencyNumber.length() < 3) {
            throw new InvalidAgencyException(
                    "INVALID_AGENCY",
                    "The agency number must have 3 digits"
            );
        }
    }

    private void validateAccountNumber() {
        if (this.number.length() > 8) {
            throw new AccountNumberExceededLimit(
                    "ACCOUNT_NUMBER_EXCEEDED_LIMIT",
                    "Account number exceeded the 8 character limit"
            );
        }
    }


    public static final class AccountBuilder {
        private String id;
        private String agencyNumber;
        private Email email;
        private Password password;
        private String personId;
        private AccountType type;
        private AccountStatus status;
        private PersonalDocument personalDocument;
        private String number;
        private HashSet<PixKey> pixKeys;

        private AccountBuilder() {
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder aId(String id) {
            this.id = id;
            return this;
        }

        public AccountBuilder aAgencyNumber(String agencyNumber) {
            this.agencyNumber = agencyNumber;
            return this;
        }

        public AccountBuilder aEmail(Email email) {
            this.email = email;
            return this;
        }

        public AccountBuilder aPassword(Password password) {
            this.password = password;
            return this;
        }

        public AccountBuilder aPersonId(String personId) {
            this.personId = personId;
            return this;
        }

        public AccountBuilder aType(AccountType type) {
            this.type = type;
            return this;
        }

        public AccountBuilder aStatus(AccountStatus status) {
            this.status = status;
            return this;
        }

        public AccountBuilder aPersonalDocument(PersonalDocument personalDocument) {
            this.personalDocument = personalDocument;
            return this;
        }

        public AccountBuilder aNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountBuilder aPixKeys(HashSet<PixKey> pixKeys) {
            this.pixKeys = pixKeys;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.agencyNumber = this.agencyNumber;
            account.number = this.number;
            account.personalDocument = this.personalDocument;
            account.pixKeys = this.pixKeys;
            account.password = this.password;
            account.type = this.type;
            account.id = this.id;
            account.personId = this.personId;
            account.email = this.email;
            account.status = this.status;
            account.validateAccount();
            return account;
        }
    }
}
