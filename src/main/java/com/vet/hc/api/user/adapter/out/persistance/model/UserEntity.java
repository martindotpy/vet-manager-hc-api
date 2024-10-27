package com.vet.hc.api.user.adapter.out.persistance.model;

import java.util.Set;

import com.vet.hc.api.user.domain.enums.UserRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User entity.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String firstName;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String lastName;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String email;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<UserRole> roles;

    /**
     * Validates that the user has at least one role.
     *
     * @throws IllegalArgumentException if the user has no roles.
     */
    @PrePersist
    @PreUpdate
    private void validateRoles() {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role.");
        }
    }
}
