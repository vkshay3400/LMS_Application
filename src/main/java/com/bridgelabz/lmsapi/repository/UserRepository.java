package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DAOUser, Integer> {
    @Query("select u from user u where u.first_name = ?1")
    DAOUser findByFirst_name(String first_name);
    DAOUser findByEmail(String email);
}
