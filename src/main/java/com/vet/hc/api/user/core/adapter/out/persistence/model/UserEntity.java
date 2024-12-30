package com.vet.hc.api.user.core.adapter.out.persistence.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vet.hc.api.user.core.domain.enums.UserRole;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uk_user_email")
})
public class UserEntity implements UserDetails, User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String firstName;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String lastName;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Set<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(UserEntityRole::new).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    private static class UserEntityRole implements GrantedAuthority {
        private UserRole role;

        public UserEntityRole(UserRole role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return role.name();
        }
    }
}
