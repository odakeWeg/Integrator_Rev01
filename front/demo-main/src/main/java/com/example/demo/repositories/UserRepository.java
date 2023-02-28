package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {
    List<UserModel> findByCadastro(int cadastro);
    List<UserModel> findByLine(int line);
    List<UserModel> deleteByCadastro(int cadastro);
    List<UserModel> findAllByOrderByIdAsc();
    List<UserModel> findAllByOrderByLineAsc();
    List<UserModel> deleteByLine(int line);
}
