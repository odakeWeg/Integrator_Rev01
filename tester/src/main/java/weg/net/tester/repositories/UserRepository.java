package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {
    
}
