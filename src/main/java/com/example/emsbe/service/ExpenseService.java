package com.example.emsbe.service;

import com.example.emsbe.entity.Expense;
import com.example.emsbe.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense saveExpense(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public Expense updateExpense(Long id, Expense updated) {
        Expense existing = getExpenseById(id);

        existing.setTitle(updated.getTitle());
        existing.setDate(updated.getDate());
        existing.setCostGbp(updated.getCostGbp());
        existing.setDescription(updated.getDescription());
        existing.setExpenseType(updated.getExpenseType());

        return repository.save(existing);
    }

    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }
}