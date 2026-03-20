package com.example.emsbe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank(message = "Date is required")
    private String date;

    @NotNull
    @Positive(message = "Cost must be positive")
    private Double costGbp;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Expense type is required")
    private String expenseType;
}