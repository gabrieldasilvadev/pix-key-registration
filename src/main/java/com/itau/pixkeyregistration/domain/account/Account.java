package com.itau.pixkeyregistration.domain.account;

import com.itau.pixkeyregistration.domain.account.enums.AccountStatus;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountStatusException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountTypeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgencyException;
import com.itau.pixkeyregistration.domain.valueobjects.Address;
import com.itau.pixkeyregistration.domain.valueobjects.Password;
import com.itau.pixkeyregistration.domain.valueobjects.Email;
import de.huxhorn.sulky.ulid.ULID;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public class Account {
    private final String id = new ULID().nextULID();
    private String agencyNumber;
    private Integer agencyLastDigit;
    private Email email;
    private Password password;
    private String personId;
    private AccountType type;
    private AccountStatus status = AccountStatus.IN_ACTIVATION;
    private String personalDocument;
    private Address address;

    public Account() {
        validateAccount();
    }

    private String getAgency() {
        return String.format("%s-%s", this.agencyNumber, this.agencyLastDigit);
    }

    public void addAddress(Address address) {
        this.address = address;
    }

    private void validateAccount() {
        isValidType();
        isValidStatus();
        isValidAgencyNumber();
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


    public static final class AccountBuilder {
        private String agencyNumber;
        private Integer agencyLastDigit;
        private Email email;
        private Password password;
        private String personId;
        private AccountType type;
        private AccountStatus status;
        private String personalDocument;

        private AccountBuilder() {
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder aAgencyNumber(String agencyNumber) {
            this.agencyNumber = agencyNumber;
            return this;
        }

        public AccountBuilder aAgencyLastDigit(Integer agencyLastDigit) {
            this.agencyLastDigit = agencyLastDigit;
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

        public AccountBuilder aPersonalDocument(String personalDocument) {
            this.personalDocument = personalDocument;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.agencyLastDigit = this.agencyLastDigit;
            account.personalDocument = this.personalDocument;
            account.email = this.email;
            account.personId = this.personId;
            account.password = this.password;
            account.type = this.type;
            account.agencyNumber = this.agencyNumber;
            account.status = this.status;
            return account;
        }
    }
}
