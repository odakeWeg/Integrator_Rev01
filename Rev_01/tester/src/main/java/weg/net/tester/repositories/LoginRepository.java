package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.database.LoginModel;

public interface LoginRepository extends MongoRepository<LoginModel, String> {
    
}
