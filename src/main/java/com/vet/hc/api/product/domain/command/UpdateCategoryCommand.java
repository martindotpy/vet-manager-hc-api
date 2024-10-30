package com.vet.hc.api.product.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to update a category.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryCommand {
    private Long id;
    private String name;
}
