package com.vet.hc.api.user.core.adapter.out.persistence;

import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vet.hc.api.shared.adapter.out.persistence.CriteriaEntityPersistenceAdapter;
import com.vet.hc.api.shared.application.annotations.PersistenceAdapter;
import com.vet.hc.api.shared.domain.result.Result;
import com.vet.hc.api.user.core.adapter.out.persistence.entity.UserEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.repository.UserSpringRepository;
import com.vet.hc.api.user.core.application.dto.UserDto;
import com.vet.hc.api.user.core.application.mapper.UserMapper;
import com.vet.hc.api.user.core.domain.failure.UserExceptionFailureHandler;
import com.vet.hc.api.user.core.domain.failure.UserFailure;
import com.vet.hc.api.user.core.domain.model.User;
import com.vet.hc.api.user.core.domain.model.UserImpl;
import com.vet.hc.api.user.core.domain.repository.UserRepository;

/**
 * User persistence adapter.
 */
@PersistenceAdapter
public final class UserPersistenceAdapter
        extends
        CriteriaEntityPersistenceAdapter<User, Long, UserImpl, UserEntity, UserDto, UserFailure, UserSpringRepository>
        implements
        UserRepository, UserDetailsService {
    private final UserSpringRepository userSpringRepository;
    private final UserMapper userMapper;
    private final UserExceptionFailureHandler userExceptionFailureHandler;

    public UserPersistenceAdapter(
            UserSpringRepository userSpringRepository,
            UserExceptionFailureHandler userExceptionFailureHandler,
            UserMapper userMapper) {
        super(userSpringRepository, userExceptionFailureHandler, userMapper);
        this.userSpringRepository = userSpringRepository;
        this.userMapper = userMapper;
        this.userExceptionFailureHandler = userExceptionFailureHandler;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByEmail(username)
                .map(userMapper::toEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userEntity;
    }

    @Override
    public Optional<? extends User> findByEmail(String email) {
        return userSpringRepository.findByEmail(email);
    }

    @Override
    public Optional<? extends User> findByEmailDeletedOrNot(String email) {
        return userSpringRepository.findByEmailDeletedOrNot(email);
    }

    @Override
    public Result<Void, UserFailure> restoreUserByEmail(String email) {
        try {
            userSpringRepository.restoreUserByEmail(email);

            return ok();
        } catch (Exception e) {
            return userExceptionFailureHandler.handle(e);
        }
    }
}
