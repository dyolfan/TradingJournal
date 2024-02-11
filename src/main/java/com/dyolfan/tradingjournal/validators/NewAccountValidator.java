package com.dyolfan.tradingjournal.validators;

import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class NewAccountValidator {
    private final AccountRepository accountsRepository;

    public NewAccountValidator(AccountRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public boolean validateNewAccount(Account account) {
        return accountsRepository.findByName(account.getName()).isEmpty();
    }
}
