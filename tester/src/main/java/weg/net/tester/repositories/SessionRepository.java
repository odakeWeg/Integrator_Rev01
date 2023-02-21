package weg.net.tester.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.SessionModel;

public interface SessionRepository extends MongoRepository<SessionModel, String> {
    
}
