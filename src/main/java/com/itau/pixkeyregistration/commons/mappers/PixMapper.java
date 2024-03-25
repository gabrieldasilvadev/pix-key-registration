package com.itau.pixkeyregistration.commons.mappers;

import com.itau.pixkeyregistration.application.web.dto.AccountDetailsResponseDto;
import com.itau.pixkeyregistration.application.web.dto.AccountTypeDto;
import com.itau.pixkeyregistration.application.web.dto.InactivePixKeyResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyRegistrationResponseDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyStatusDto;
import com.itau.pixkeyregistration.application.web.dto.PixKeyTypeDto;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.PixKeyUpdate;
import com.itau.pixkeyregistration.domain.pix.inactive.PixKeyInactive;
import com.itau.pixkeyregistration.domain.pix.inactive.PixKeyInactiveAccountDetails;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PixKeyTable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class PixMapper {
    public PixKeyRegistrationResponseDto toUpdateResponse(PixKeyUpdate pixKeyUpdated) {
        return new PixKeyRegistrationResponseDto(
                PixKeyTypeDto.valueOf(pixKeyUpdated.getKeyType().name()),
                pixKeyUpdated.getKeyValue(),
                new AccountDetailsResponseDto(
                        pixKeyUpdated.getAccountId(),
                        pixKeyUpdated.getFirstName(),
                        pixKeyUpdated.getLastName(),
                        pixKeyUpdated.getAccountNumber(),
                        AccountTypeDto.valueOf(pixKeyUpdated.getAccountType().name()),
                        pixKeyUpdated.getAgency()
                ),
                PixKeyStatusDto.fromValue(pixKeyUpdated.getKeyValue())
        );
    }

    public InactivePixKeyResponseDto toInactiveResponse(PixKeyInactive pixKeyInactivated) {
        return new InactivePixKeyResponseDto(
                pixKeyInactivated.getKeyId(),
                PixKeyTypeDto.valueOf(pixKeyInactivated.getKeyType().name()),
                new AccountDetailsResponseDto(
                        pixKeyInactivated.getAccountDetails().getAccountId(),
                        pixKeyInactivated.getAccountDetails().getFirstName(),
                        pixKeyInactivated.getAccountDetails().getLastName(),
                        pixKeyInactivated.getAccountDetails().getAccountNumber(),
                        AccountTypeDto.valueOf(pixKeyInactivated.getAccountDetails().getAccountType().name()),
                        pixKeyInactivated.getAccountDetails().getAgency()

                ),
                pixKeyInactivated.getKeyAddedAt().atOffset(ZoneOffset.UTC),
                pixKeyInactivated.getKeyInactivatedAt().atOffset(ZoneOffset.UTC)
        );
    }

    public PixKeyTable toTable(PixKey pixKey, AccountTable accountTable) {
        LocalDateTime pixKeyTableCreatedAt;
        try {
            pixKeyTableCreatedAt = accountTable.getPixKeys()
                    .stream()
                    .filter(pk -> pk.getId().equals(pixKey.getId()))
                    .findFirst()
                    .orElseThrow()
                    .getCreatedAt();

        } catch (NoSuchElementException ex) {
            pixKeyTableCreatedAt = LocalDateTime.now();
        }

        return PixKeyTable.builder()
                .id(pixKey.getId())
                .keyValue(pixKey.getKeyValue())
                .keyType(pixKey.getKeyType())
                .status(pixKey.getStatus())
                .account(accountTable)
                .createdAt(pixKeyTableCreatedAt)
                .build();
    }

    public PixKeyTable toInactiveTable(PixKey pixKey, AccountTable accountTable) {
       PixKeyTable pixKeyFromAccount = accountTable.getPixKeys()
                .stream()
                .filter(pk -> pk.getId().equals(pixKey.getId()))
                .findFirst()
                .orElseThrow();

        return PixKeyTable.builder()
                .id(pixKey.getId())
                .keyValue(pixKey.getKeyValue())
                .keyType(pixKey.getKeyType())
                .status(pixKey.getStatus())
                .account(accountTable)
                .createdAt(pixKeyFromAccount.getCreatedAt())
                .inactivatedAt(LocalDateTime.now())
                .build();
    }

    public PixKeyInactive toInactive(PixKeyTable updatedPixKey) {
        return PixKeyInactive.builder()
                .keyId(updatedPixKey.getId())
                .keyValue(updatedPixKey.getKeyValue())
                .keyType(updatedPixKey.getKeyType())
                .keyStatus(updatedPixKey.getStatus())
                .accountDetails(
                        PixKeyInactiveAccountDetails.builder()
                                .accountType(updatedPixKey.getAccount().getType())
                                .agency(updatedPixKey.getAccount().getAgencyNumber())
                                .accountNumber(updatedPixKey.getAccount().getAgencyNumber())
                                .firstName(updatedPixKey.getAccount().getPerson().getFirstName())
                                .lastName(updatedPixKey.getAccount().getPerson().getLastName())
                                .accountId(updatedPixKey.getAccount().getId())
                                .build()
                )
                .keyAddedAt(updatedPixKey.getCreatedAt())
                .keyInactivatedAt(LocalDateTime.now())
                .keyAddedAt(updatedPixKey.getCreatedAt())
                .build();
    }

    public PixKey toDomain(PixKeyTable pixKeyTable) {
        return PixKey.PixKeyBuilder.aPixKey()
                .aId(pixKeyTable.getId())
                .aKeyValue(pixKeyTable.getKeyValue())
                .aKeyType(pixKeyTable.getKeyType())
                .aStatus(pixKeyTable.getStatus())
                .aAccountId(pixKeyTable.getAccount().getId())
                .build();
    }

    public HashSet<PixKey> toDomain(HashSet<PixKeyTable> hashPixKey) {
        return hashPixKey.stream()
                .map(this::toDomain)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
