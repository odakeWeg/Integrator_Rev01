package weg.net.tester.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.database.TestingResultModel;

public interface TestingResultRepository extends MongoRepository<TestingResultModel, String> {
    List<TestingResultModel> findBySerial(String serial);
}
