package com.finance.finance_api.controller;

import com.finance.finance_api.model.Transaction;
import com.finance.finance_api.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return repository.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }

        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @RequestBody Transaction updated) {

        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        transaction.setDescription(updated.getDescription());
        transaction.setAmount(updated.getAmount());
        transaction.setType(updated.getType());

        return repository.save(transaction);
    }
}