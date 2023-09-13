package com.quipildor.backendusersapp.repositories;

import com.quipildor.backendusersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
