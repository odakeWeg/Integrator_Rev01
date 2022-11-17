package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.TestingRoutineModel;

public interface TestingRoutineRepository extends MongoRepository<TestingRoutineModel, String> {
    TestingRoutineModel findByFileName(String fileName);
}
