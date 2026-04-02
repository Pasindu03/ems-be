package com.example.emsbe.controller;

import com.example.emsbe.entity.Expense;
import com.example.emsbe.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateExpense() throws Exception {
        Expense expense = new Expense();
        Mockito.when(expenseService.saveExpense(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllExpenses() throws Exception {
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        
        Mockito.when(expenseService.getAllExpenses()).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetExpenseById() throws Exception {
        Expense expense = new Expense();
        Mockito.when(expenseService.getExpenseById(1L)).thenReturn(expense);

        mockMvc.perform(get("/api/expenses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateExpense() throws Exception {
        Expense expense = new Expense();
        Mockito.when(expenseService.updateExpense(eq(1L), any(Expense.class))).thenReturn(expense);

        mockMvc.perform(put("/api/expenses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteExpense() throws Exception {
        mockMvc.perform(delete("/api/expenses/1"))
                .andExpect(status().isOk());
        Mockito.verify(expenseService, Mockito.times(1)).deleteExpense(1L);
    }

    @Test
    void testGetExpenseById_NotFoundException() throws Exception {
        // This tests your GlobalExceptionHandler's handling of RuntimeException
        Mockito.when(expenseService.getExpenseById(99L))
                .thenThrow(new RuntimeException("Expense not found"));

        mockMvc.perform(get("/api/expenses/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Expense not found"));
    }
}