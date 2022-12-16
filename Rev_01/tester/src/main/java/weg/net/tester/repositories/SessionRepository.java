package weg.net.tester.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.database.SessionModel;

public interface SessionRepository extends MongoRepository<SessionModel, String> {
    List<SessionModel> findByCadastro(String cadastro);
}
