package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.ProjectModel;
import com.example.demo.repositories.ProjectRepository;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/system/")
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping()
    public List<ProjectModel> findAllProject() {
        return projectRepository.findAllByOrderByLineAsc();
    }

    @GetMapping("/{line}")
    public List<ProjectModel> findUserByLine(@PathVariable(value = "line") int line) {
        return projectRepository.findByLine(line);
    }

    @PostMapping()
    public ResponseEntity<ProjectModel> addProject(@RequestBody ProjectModel projectModel) {
        try {
            projectModel.setId((int) (new Date().getTime()/1000));
            projectModel.setLine(projectRepository.findAll().size());
            projectModel.setCreationDate(new Date());
            projectModel.setLastModificationDate(new Date());
            projectModel.setEnabled(true);
            ProjectModel _ProjectModel = projectRepository.save(projectModel);
            return new ResponseEntity<>(_ProjectModel, HttpStatus.CREATED);
          } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

    @DeleteMapping("/{line}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("line") int line) {
        try {
            projectRepository.deleteByLine(line);
            projectRepository.findByLine(line);
            List<ProjectModel> list = projectRepository.findAllByOrderByLineAsc();
            for(int i = 0; i < list.size(); i++) {
                list.get(i).setLine(i);
                projectRepository.save(list.get(i));
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    
  
  //@TODO: Acertar os exceptions
  @PutMapping("/{line}")
  public ResponseEntity<ProjectModel> updateTutorial(@PathVariable("line") int line, @RequestBody ProjectModel projectModel) {
      try {
          List<ProjectModel> projectData = projectRepository.findByLine(line);
  
          if (projectData.size()==1) {
            ProjectModel _ProjectModel = projectData.get(0);
            _ProjectModel.setNome(projectModel.getNome());
            _ProjectModel.setCreator(projectModel.getCreator());
            _ProjectModel.setLastModificationDate(new Date());
            _ProjectModel.setMappings(projectModel.getMappings());
            _ProjectModel.setRoutines(projectModel.getRoutines());
            _ProjectModel.setConfigurations(projectModel.getConfigurations());
            _ProjectModel.setNode(projectModel.getNode());
            _ProjectModel.setConnections(projectModel.getConnections());
            _ProjectModel.setKeyWords(projectModel.getKeyWords());
            _ProjectModel.setDescription(projectModel.getDescription());
            _ProjectModel.setEnabled(projectModel.isEnabled());
            return new ResponseEntity<>(projectRepository.save(_ProjectModel), HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
      } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.CONFLICT);
      }
  }
}
