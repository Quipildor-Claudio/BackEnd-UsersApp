package com.quipildor.backendusersapp.controllers;

import com.quipildor.backendusersapp.models.entities.User;
import com.quipildor.backendusersapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping
    public List<User> list(){
        return  service.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<User>  userOptional = service.findById(id);
        if (userOptional.isPresent()){
            return  ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result ,@PathVariable Long id) {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<User> o = service.update(user, id);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<User> o = service.findById(id);
        if (o.isPresent()) {
           service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(),"El campo"+" "+err.getField()+ " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
