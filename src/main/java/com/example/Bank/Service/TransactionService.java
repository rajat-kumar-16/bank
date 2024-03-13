package com.example.Bank.Service;

import com.example.Bank.dto.TransactionDTO;
import com.example.Bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionService {
    public List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);
}
