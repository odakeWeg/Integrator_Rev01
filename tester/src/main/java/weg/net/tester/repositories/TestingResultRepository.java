package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.TestingResultModel;

public interface TestingResultRepository extends MongoRepository<TestingResultModel, String> {
    
}
