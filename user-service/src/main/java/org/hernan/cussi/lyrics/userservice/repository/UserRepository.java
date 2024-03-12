package org.hernan.cussi.lyrics.userservice.repository;

import org.hernan.cussi.lyrics.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
