package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.database.StackTraceLogModel;

public interface StackTraceLogRepository extends MongoRepository<StackTraceLogModel, String> {
    
}
