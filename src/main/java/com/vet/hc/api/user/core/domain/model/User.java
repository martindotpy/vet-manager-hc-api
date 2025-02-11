package com.vet.hc.api.user.core.domain.model;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vet.hc.api.shared.adapter.in.util.RegexConstants;
import com.vet.hc.api.user.core.adapter.out.persistence.converter.ListUserRoleAttributeConverter;
import com.vet.hc.api.user.core.domain.model.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User implementation. Used to create instances of {@link User}.
 */
@Entity
@Audited
@Table(name = "`user`")
@SQLDelete(sql = "UPDATE `user` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User implements UserDetails {
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
    @Column(columnDefinition = "VARCHAR(254)", unique = true)
    private String email;
    @NotBlank
    @Column(columnDefinition = "VARCHAR(255)")
    private String password;
    @Convert(converter = ListUserRoleAttributeConverter.class)
    private List<UserRole> roles;
    @Pattern(regexp = RegexConstants.URL)
    @Column(columnDefinition = "VARCHAR(255)")
    private String profileImageUrl;

    @Builder.Default
    private boolean deleted = false;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}
