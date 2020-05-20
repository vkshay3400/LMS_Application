package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DAOUser, Integer> {
    @Query("select u from user u where u.firstName = ?1")
    DAOUser findByFirstName(String firstName);
    DAOUser findByEmail(String email);
}
