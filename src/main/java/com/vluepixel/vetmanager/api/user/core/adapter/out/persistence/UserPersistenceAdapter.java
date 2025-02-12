package com.vluepixel.vetmanager.api.user.core.adapter.out.persistence;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vluepixel.vetmanager.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.application.annotation.PersistenceAdapter;
import com.vluepixel.vetmanager.api.shared.domain.exception.RepositoryException;
import com.vluepixel.vetmanager.api.user.core.adapter.out.persistence.repository.UserSpringRepository;
import com.vluepixel.vetmanager.api.user.core.application.dto.UserDto;
import com.vluepixel.vetmanager.api.user.core.application.mapper.UserMapper;
import com.vluepixel.vetmanager.api.user.core.domain.model.User;
import com.vluepixel.vetmanager.api.user.core.domain.repository.UserRepository;

/**
 * User persistence adapter.
 */
@PersistenceAdapter
public final class UserPersistenceAdapter
        extends CriteriaEntityPersistenceAdapter<User, Long, UserDto, UserSpringRepository>
        implements UserRepository, UserDetailsService {
    private final UserSpringRepository userSpringRepository;

    public UserPersistenceAdapter(
            UserSpringRepository userSpringRepository,
            UserMapper userMapper) {
        super(userSpringRepository, userMapper);
        this.userSpringRepository = userSpringRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userSpringRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailDeletedOrNot(String email) {
        return userSpringRepository.findByEmailDeletedOrNot(email);
    }

    @Override
    public void restoreUserByEmail(String email) {
        try {
            userSpringRepository.restoreUserByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException(e, User.class);
        }
    }
}
