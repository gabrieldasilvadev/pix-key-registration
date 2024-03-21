package com.itau.pixkeyregistration.domain.person;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonNameException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.valueobjects.Cellphone;
import de.huxhorn.sulky.ulid.ULID;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Getter
public class Person {
    private final String id = new ULID().nextULID();
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private PersonType type;
    private Cellphone cellphone;
    private String personalDocument;
    private final List<Account> accounts;

    public Person() {
        validatePerson();
        accounts = new ArrayList<>();
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
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private PersonType type;
        private Cellphone cellphone;
        private String personalDocument;

        private PersonBuilder() {
        }

        public static PersonBuilder aPerson() {
            return new PersonBuilder();
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
            this.cellphone = cellphone;
            return this;
        }

        public PersonBuilder aPersonalDocument(String personalDocument) {
            this.personalDocument = personalDocument;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.firstName = this.firstName;
            person.lastName = this.lastName;
            person.type = this.type;
            person.birthDate = this.birthDate;
            person.personalDocument = this.personalDocument;
            person.cellphone = this.cellphone;
            return person;
        }
    }
}
