package com.vet.hc.api.product.domain.command;

import com.vet.hc.api.shared.domain.payload.Payload;

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
public class CreateCategoryCommand implements Payload {
    private String name;
}
