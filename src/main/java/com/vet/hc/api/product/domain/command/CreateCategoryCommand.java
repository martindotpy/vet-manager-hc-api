package com.vet.hc.api.product.domain.command;

import com.vet.hc.api.shared.domain.command.Command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to create a category.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryCommand implements Command {
    private String name;
}
