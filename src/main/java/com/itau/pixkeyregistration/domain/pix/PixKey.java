package com.itau.pixkeyregistration.domain.pix;

import com.itau.pixkeyregistration.domain.exceptions.InvalidCellphonePixKeyException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidRandomPixKeyException;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyAlreadyInactivatedException;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyStatus;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;
import com.itau.pixkeyregistration.domain.valueobjects.Email;
import com.itau.pixkeyregistration.domain.valueobjects.PersonalDocument;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PixKey {
    String id;
    String keyValue;
    PixKeyType keyType;
    PixKeyStatus status;
    String accountId;

    public PixKey(String keyValue, PixKeyType keyType) {
        this.keyValue = keyValue;
        this.keyType = keyType;

        isValidKey(keyValue, keyType);
    }

    public PixKey getByType(String type) {
        PixKeyType.valueOf(type);
        return this;
    }

    public void inactive() {
        if (this.status.equals(PixKeyStatus.INACTIVE)) {
            throw new PixKeyAlreadyInactivatedException(
                    "PIX_KEY_ALREADY_INACTIVATED",
                    "This pix key is already inactive"
            );
        }
        this.status = PixKeyStatus.INACTIVE;
    }

    public void updateKeyValue(String keyValue) {
        isValidKey(keyValue, this.keyType);
        this.keyValue = keyValue;
    }

    private void isValidKey(String keyValue, PixKeyType keyType) {
        switch (keyType) {
            case CELULAR -> isValidCellphone(keyValue);
            case EMAIL -> isValidEmail(keyValue);
            case CPF -> isValidCPF(keyValue);
            case CNPJ -> isValidCNPJ(keyValue);
            case ALEATORIO -> isValidRandom(keyValue);
        }
    }

    private void isValidCellphone(String keyValue) {
        if (!keyValue.startsWith("+")) {
            throw new InvalidCellphonePixKeyException(
                    "INVALID_CELLPHONE_PIX_KEY",
                    "Cellphone number must start with the '+' symbol"
            );
        }

        String countryCode = keyValue.substring(1, 3);
        if (!countryCode.matches("\\d{2}")) {
            throw new InvalidCellphonePixKeyException(
                    "INVALID_CELLPHONE_PIX_KEY",
                    "Country code must contain exactly two digits"
            );
        }

        String ddd = keyValue.substring(3, 6);
        if (!ddd.matches("\\d{3}")) {
            throw new InvalidCellphonePixKeyException(
                    "INVALID_CELLPHONE_PIX_KEY",
                    "DDD must contain exactly three digits"
            );
        }

        String phoneNumber = keyValue.substring(6);
        if (!phoneNumber.matches("\\d{9}")) {
            throw new InvalidCellphonePixKeyException(
                    "INVALID_CELLPHONE_PIX_KEY",
                    "Cellphone number must contain exactly nine digits"
            );
        }

    }

    private void isValidEmail(String keyValue) {
        new Email(keyValue);
    }

    private void isValidCPF(String keyValue) {
        new PersonalDocument(keyValue);
    }

    private void isValidCNPJ(String keyValue) {
        new PersonalDocument(keyValue);
    }

    private void isValidRandom(String keyValue) {
        boolean isValid = isValidUUID(keyValue);
        if (keyValue.length() != 36 || !isValid) {
            throw new InvalidRandomPixKeyException(
                    "INVALID_RANDOM_PIX_KEY",
                    "The random key must contain exactly 36 characters"
            );
        }
    }

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public static final class PixKeyBuilder {
        private String id;
        private String keyValue;
        private PixKeyType keyType;
        private PixKeyStatus status;
        private String accountId;

        private PixKeyBuilder() {
        }

        public static PixKeyBuilder aPixKey() {
            return new PixKeyBuilder();
        }

        public PixKeyBuilder aId(String id) {
            this.id = id;
            return this;
        }

        public PixKeyBuilder aKeyValue(String keyValue) {
            this.keyValue = keyValue;
            return this;
        }

        public PixKeyBuilder aKeyType(PixKeyType keyType) {
            this.keyType = keyType;
            return this;
        }

        public PixKeyBuilder aStatus(PixKeyStatus status) {
            this.status = status;
            return this;
        }

        public PixKeyBuilder aAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public PixKey build() {
            PixKey pixKey = new PixKey(keyValue, keyType);
            pixKey.accountId = this.accountId;
            pixKey.status = this.status;
            pixKey.id = this.id;
            return pixKey;
        }
    }
}
