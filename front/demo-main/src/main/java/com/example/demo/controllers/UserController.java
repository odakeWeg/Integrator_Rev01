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

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;


@CrossOrigin(origins = "http://localhost:4202/")
@RestController
@RequestMapping("/config/user/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public List<UserModel> findAllUsers() {
        return userRepository.findAllByOrderByLineAsc();
    }

    @GetMapping("/{cadastro}")
    public List<UserModel> findUserByCadastro(@PathVariable(value = "cadastro") int cadastro) {
        return userRepository.findByCadastro(cadastro);
    }

    @PostMapping()
    public ResponseEntity<UserModel> postMethodName(@RequestBody UserModel userModel) {
        try {
            userModel.setId((int) (new Date().getTime()/1000));
            userModel.setLine(userRepository.findAll().size());
            UserModel _userModel = userRepository.save(userModel);
            return new ResponseEntity<>(_userModel, HttpStatus.CREATED);
          } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }

    @DeleteMapping("/{cadastro}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("cadastro") int cadastro) {
        try {
            userRepository.deleteByLine(cadastro);
            userRepository.findByLine(cadastro);
            List<UserModel> list = userRepository.findAllByOrderByLineAsc();
            for(int i = 0; i < list.size(); i++) {
                list.get(i).setLine(i);
                userRepository.save(list.get(i));
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
    
  
  //@TODO: Acertar os exceptions
  @PutMapping("/{cadastro}")
  public ResponseEntity<UserModel> updateTutorial(@PathVariable("cadastro") int cadastro, @RequestBody UserModel userModel) {
      try {
          List<UserModel> userData = userRepository.findByLine(cadastro);
  
          if (userData.size()==1) {
            UserModel _userModel = userData.get(0);
            _userModel.setCadastro(userModel.getCadastro());
            _userModel.setNome(userModel.getNome());
            _userModel.setPerfil(userModel.getPerfil());
            _userModel.setSenha(userModel.getSenha());
            return new ResponseEntity<>(userRepository.save(_userModel), HttpStatus.OK);
          } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
      } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.CONFLICT);
      }
  }
}
