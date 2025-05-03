package org.softwaredev.springsecurity.user.repository;

import org.softwaredev.springsecurity.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
