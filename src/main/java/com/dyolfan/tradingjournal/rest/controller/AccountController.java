package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.services.db.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
@Validated
public class AccountController {
    private final AccountService accountService;

    public AccountController(@Autowired AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add")
    public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.addAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Account> updateAccount(@PathVariable String id, @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccountById(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable String id) {
        return ResponseEntity.ok(accountService.deleteAccountById(id));
    }
}
