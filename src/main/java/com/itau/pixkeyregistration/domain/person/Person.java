package com.itau.pixkeyregistration.domain.person;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonNameException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.valueobjects.Cellphone;
import com.itau.pixkeyregistration.domain.valueobjects.PersonalDocument;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@Getter
public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private PersonType type;
    private Cellphone cellphone;
    private PersonalDocument personalDocument;
    private HashSet<Account> accounts;

    public Person() {
        accounts = new HashSet<>();
    }

    public int getAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    private void validatePerson() {
        isLegalAge();
        isMinimumCharacters();
        isValidType();
        isAgeExcessive();
    }

    private void isLegalAge() {
        boolean underEighteen = this.getAge() < 18;
        if (underEighteen) {
            throw new InvalidAgeException(
                    "PERSON_INVALID_PARAMS",
                    String.format("You are not allowed to create an account if you are under 18. Current age: %d", this.getAge()),
                    Map.of(
                            "CURRENT_AGE", this.getAge(),
                            "BIRTH_DATE", this.birthDate
                    )
            );
        }
    }

    private void isMinimumCharacters() {
        if (this.firstName.length() < 3) {
            throw new InvalidPersonNameException(
                    "PERSON_INVALID_PARAMS",
                    String.format("The customer's first name does not have the minimum number of characters. Name[%s]", this.firstName)
            );
        }

        if (this.lastName.length() < 5) {
            throw new InvalidPersonNameException(
                    "PERSON_INVALID_PARAMS",
                    String.format("The customer's last name does not have the minimum number of characters. Name[%s]", this.lastName)
            );
        }
    }

    private void isValidType() {
        boolean hasPersonType = Arrays.stream(PersonType.values()).toList().contains(this.type);
        if (!hasPersonType) {
            throw new InvalidPersonTypeException(
                    "PERSON_INVALID_PARAMS",
                    String.format("The given person type is not valid. PersonType[%s]", this.type.name()),
                    Map.of("PERSON_TYPE", this.type.name())
            );
        }
    }

    private void isAgeExcessive() {
        boolean isExcessive = this.getAge() > 150;
        if (isExcessive) {
            throw new InvalidAgeException(
                    "PERSON_INVALID_PARAMS",
                    String.format("Overage customer. Informed age: %d", this.getAge()),
                    Map.of(
                            "CURRENT_AGE", this.getAge(),
                            "BIRTH_DATE", birthDate
                    )
            );
        }
    }


    public static final class PersonBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private PersonType type;
        private Cellphone cellphone;
        private String personalDocument;
        private HashSet<Account> accounts;

        private PersonBuilder() {
        }

        public static PersonBuilder aPerson() {
            return new PersonBuilder();
        }

        public PersonBuilder aId(String id) {
            this.id = id;
            return this;
        }


        public PersonBuilder aFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder aLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonBuilder aBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder aType(PersonType type) {
            this.type = type;
            return this;
        }

        public PersonBuilder aCellphone(Cellphone cellphone) {
            this.cellphone = new Cellphone(cellphone.value());
            return this;
        }

        public PersonBuilder aPersonalDocument(String personalDocument) {
            this.personalDocument = personalDocument;
            return this;
        }

        public PersonBuilder aAccounts(HashSet<Account> accounts) {
            this.accounts = accounts;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.id = this.id;
            person.personalDocument = new PersonalDocument(this.personalDocument);
            person.firstName = this.firstName;
            person.birthDate = this.birthDate;
            person.type = this.type;
            person.cellphone = this.cellphone;
            person.lastName = this.lastName;
            person.accounts = this.accounts;

            person.validatePerson();
            return person;
        }
    }
}
