package org.hernan.cussi.lyrics.userservice.repository;

import org.hernan.cussi.lyrics.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByEmail(String email);
}
