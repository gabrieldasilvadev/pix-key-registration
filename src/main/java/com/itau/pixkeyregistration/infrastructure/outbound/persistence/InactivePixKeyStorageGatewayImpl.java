package com.itau.pixkeyregistration.infrastructure.outbound.persistence;

import com.itau.pixkeyregistration.commons.mappers.PixMapper;
import com.itau.pixkeyregistration.domain.gateways.InactivePixKeyStorageGateway;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.inactive.PixKeyInactive;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InactivePixKeyStorageGatewayImpl implements InactivePixKeyStorageGateway {
    private final AccountRepository accountRepository;
    private final PixMapper mapper;

    @Override
    public PixKeyInactive inactive(PixKey pixKey) {
        AccountTable account = accountRepository.findById(pixKey.getAccountId()).orElseThrow();
        var updatedPixKey = mapper.toInactiveTable(pixKey, account);
        var keyToRemove = account.getPixKeys()
                .stream()
                .filter(keyTable -> keyTable.getId().equals(updatedPixKey.getId()))
                .findFirst()
                .orElseThrow();
        account.getPixKeys().remove(keyToRemove);
        account.getPixKeys().add(updatedPixKey);
        accountRepository.save(account);
        return mapper.toInactive(updatedPixKey);
    }
}
