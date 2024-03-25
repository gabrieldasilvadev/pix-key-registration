package com.itau.pixkeyregistration.domain.pix.usecases.registerpix;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyAlreadyRegisteredException;
import com.itau.pixkeyregistration.domain.gateways.AccountStorageGateway;
import com.itau.pixkeyregistration.domain.gateways.PixStorageGateway;
import com.itau.pixkeyregistration.domain.person.enums.PersonType;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.pix.enums.PixKeyStatus;
import com.itau.pixkeyregistration.domain.valueobjects.RegisterPixKeyTransient;
import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCellphonePixUseCase {
    private final PixStorageGateway pixStorage;
    private final AccountStorageGateway accountStorage;

    public void register(RegisterPixKeyTransient registerPixKeyTransient) {
        Account account = accountStorage.findByAccountNumber(registerPixKeyTransient.accountDetails().accountNumber());
        PersonType personType = PersonType.getByPersonalDocument(account.getPersonalDocument().value());
        PixKey pixKey = buildPixKey(registerPixKeyTransient, account);
        validatePixKeyUniqueness(pixKey);

        account.addPixKey(pixKey, personType);
        pixStorage.save(pixKey, account.getId());
    }

    private PixKey buildPixKey(RegisterPixKeyTransient registerPixKeyTransient, Account account) {
        return PixKey.PixKeyBuilder.aPixKey()
                .aId(new ULID().nextULID())
                .aKeyType(registerPixKeyTransient.type())
                .aKeyValue(registerPixKeyTransient.value())
                .aStatus(PixKeyStatus.ACTIVE)
                .aAccountId(account.getId())
                .build();
    }

    private void validatePixKeyUniqueness(PixKey pixKey) {
        boolean isUnique = pixStorage.areUniqueKey(pixKey.getKeyValue());
        if(!isUnique) {
            throw new PixKeyAlreadyRegisteredException(
                    "PIX_KEY_ALREDY_REGISTERED",
                    "A key with this value is already registered"
            );
        }
    }
}
