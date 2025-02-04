package com.vet.hc.api.user.core.adapter.out.persistence.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vet.hc.api.image.core.adapter.out.persistence.entity.ImageEntity;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User entity.
 *
 * @see User User
 */
@Entity
@Audited
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = "email")
})
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserEntity implements User, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(columnDefinition = "VARCHAR(50)")
    private String firstName;
    @NotBlank
    @Size(max = 50)
    @Column(columnDefinition = "VARCHAR(50)")
    private String lastName;
    @Email
    @NotBlank
    @Size(max = 254)
    @Column(columnDefinition = "VARCHAR(254)")
    private String email;
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)")
    private String password;
    @NotEmpty
    private List<UserRole> roles;
    @OneToOne
    @NotAudited
    private ImageEntity profileImage;

    @Builder.Default
    private boolean deleted = false;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return Set.of(
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toArray(GrantedAuthority[]::new));
    }
}
