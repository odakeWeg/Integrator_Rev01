package weg.net.tester.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import weg.net.tester.models.database.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {
    List<UserModel> findByCadastro(int cadastro);
}
