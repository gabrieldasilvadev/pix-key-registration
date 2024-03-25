package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.account.update.AccountUpdate;
import com.itau.pixkeyregistration.domain.valueobjects.AccountUpdateTransient;

public interface AccountUpdateStorageGateway {
    void update(AccountUpdateTransient accountUpdateTransient);
    AccountUpdate findByAccountNumber(String accountNumber);
}
