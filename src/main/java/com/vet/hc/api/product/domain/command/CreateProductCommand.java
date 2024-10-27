package com.vet.hc.api.product.domain.command;

import com.vet.hc.api.shared.domain.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to create a product.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand implements Command {
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
}