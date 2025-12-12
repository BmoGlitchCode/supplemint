package com.BmoGlitchCode.supplemint.adapter.output.persistence.repository.user;

import com.BmoGlitchCode.supplemint.domain.model.user.Email;
import com.BmoGlitchCode.supplemint.domain.model.user.User;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.output.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of UserRepository.
 * Stores users in a ConcurrentHashMap for thread-safe operations.
 * Suitable for development and testing.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<UserId, User> usersById = new ConcurrentHashMap<>();
    private final Map<String, UserId> userIdsByEmail = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        usersById.put(user.getId(), user);
        userIdsByEmail.put(user.getEmail().getValue(), user.getId());
        return user;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return Optional.ofNullable(usersById.get(id));
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        UserId userId = userIdsByEmail.get(email.getValue());
        if (userId == null) {
            return Optional.empty();
        }
        return findById(userId);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userIdsByEmail.containsKey(email.getValue());
    }

    @Override
    public void deleteById(UserId id) {
        User user = usersById.remove(id);
        if (user != null) {
            userIdsByEmail.remove(user.getEmail().getValue());
        }
    }

    /**
     * Clears all users from the repository.
     * Useful for testing.
     */
    public void clear() {
        usersById.clear();
        userIdsByEmail.clear();
    }

    /**
     * Returns the number of users in the repository.
     */
    public int count() {
        return usersById.size();
    }
}
