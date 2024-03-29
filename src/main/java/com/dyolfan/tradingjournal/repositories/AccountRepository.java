package com.dyolfan.tradingjournal.repositories;

import com.dyolfan.tradingjournal.data.user.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByName(String name);
}
