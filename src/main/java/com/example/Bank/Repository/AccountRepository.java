package com.example.Bank.Repository;

import com.example.Bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByAccountNumber(String account_no);

}
