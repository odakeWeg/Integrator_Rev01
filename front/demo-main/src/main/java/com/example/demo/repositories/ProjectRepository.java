package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.ProjectModel;

public interface ProjectRepository extends MongoRepository<ProjectModel, String> {
    List<ProjectModel> findByLine(int line);
    List<ProjectModel> findAllByOrderByLineAsc();
    List<ProjectModel> deleteByLine(int line);
}
