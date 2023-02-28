package com.example.demo.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class ProjectModel {
    @Id
    private int id;
    private int line;
    private String nome;
    private String creator;
    private Date creationDate;
    private Date lastModificationDate;
    private List<MappingModel> mappings;
    private List<RoutineModel> routines;
    private List<ConfigurationModel> configurations;
    private int node;
    private List<Integer> connections;
    private List<String> keyWords;
    private String description;
    private boolean enabled;

    public ProjectModel(int id, int line, String nome, String creator, Date creationDate, Date lastModificationDate, List<MappingModel> mappings, List<RoutineModel> routines, List<ConfigurationModel> configurations, int node, List<Integer> connections, List<String> keyWords, String description, boolean enabled) {
        this.id = id;
        this.line = line;
        this.nome = nome;
        this.creator = creator;
        this.creationDate = creationDate;
        this.mappings = mappings;
        this.routines = routines;
        this.configurations = configurations;
        this.node = node;
        this.connections = connections;
        this.keyWords = keyWords;
        this.description = description;
        this.enabled = enabled;
    }
}
