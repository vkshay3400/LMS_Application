package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.DAOUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);
}
